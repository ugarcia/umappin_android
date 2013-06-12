package mdiss.umappin.fragments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.Projection;
import org.mapsforge.android.maps.mapgenerator.tiledownloader.MapnikTileDownloader;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.android.maps.overlay.ArrayWayOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.android.maps.overlay.OverlayWay;
import org.mapsforge.core.BoundingBox;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.core.MapPosition;

import mdiss.umappin.R;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.GeoMethods;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class MapFragment extends Fragment {

	private MapView mapView;
	private List<GeoPoint> route;
	private String routeName="";

	public MapFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		initializeMap();
		if (route!=null) {
			showRoute();
		} else {
			getActivity().setTitle("OpenStreetMap");
		}
		return mapView;
	}

	private void initializeMap() {
		mapView = new MapView(getActivity(), new MapnikTileDownloader());
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setCenter(GeoMethods.getCurrentLocation(getActivity()));
		// mapView.setCenter(new GeoPoint(41.40, 2.15));
		mapView.getController().setZoom(16);
		mapView.setBuiltInZoomControls(true);
	}

	public void showRoute() {
		if (route == null) {
			route = new ArrayList<GeoPoint>();
		}
		double distance = GeoMethods.getDistance(route);
		Log.i(Constants.logGeoMethods, distance + " km");
		if (!route.isEmpty()) {
			getActivity().setTitle(routeName);
			mapView.setCenter(route.get(0));
			Paint wayDefaultPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
			wayDefaultPaintFill.setStyle(Paint.Style.STROKE);
			wayDefaultPaintFill.setColor(Color.BLUE);
			wayDefaultPaintFill.setAlpha(160);
			wayDefaultPaintFill.setStrokeWidth(7);
			wayDefaultPaintFill.setStrokeJoin(Paint.Join.ROUND);
			wayDefaultPaintFill.setPathEffect(new DashPathEffect(new float[] { 20, 20 }, 0));

			Paint wayDefaultPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
			wayDefaultPaintOutline.setStyle(Paint.Style.STROKE);
			wayDefaultPaintOutline.setColor(Color.BLUE);
			wayDefaultPaintOutline.setAlpha(128);
			wayDefaultPaintOutline.setStrokeWidth(7);
			wayDefaultPaintOutline.setStrokeJoin(Paint.Join.ROUND);

			Paint wayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			wayPaint.setStyle(Paint.Style.FILL);
			wayPaint.setColor(Color.YELLOW);
			wayPaint.setAlpha(192);

			ArrayWayOverlay wayOverlay = new ArrayWayOverlay(wayDefaultPaintFill, wayDefaultPaintOutline);
			GeoPoint[] points = route.toArray(new GeoPoint[0]);
			OverlayWay way1 = new OverlayWay(new GeoPoint[][] { points });
			wayOverlay.addWay(way1);
			mapView.getOverlays().add(wayOverlay);

			int maxLat, minLat, maxLon, minLon;
			maxLat = Integer.MIN_VALUE;
			minLat = Integer.MAX_VALUE;
			maxLon = Integer.MIN_VALUE;
			minLon = Integer.MAX_VALUE;
			Iterator<GeoPoint> iter = route.iterator();
			while (iter.hasNext()) {
				GeoPoint gp = iter.next();
				if ((gp.getLatitude() * 1E6) > maxLat)
					maxLat = (int) (gp.getLatitude() * 1E6);
				if ((gp.getLatitude() * 1E6) < minLat)
					minLat = (int) (gp.getLatitude() * 1E6);
				if ((gp.getLongitude() * 1E6) > maxLon)
					maxLon = (int) (gp.getLongitude() * 1E6);
				if ((gp.getLongitude() * 1E6) < minLon)
					minLon = (int) (gp.getLongitude() * 1E6);
			}

			BoundingBox bb = new BoundingBox(minLat, minLon, maxLat, maxLon);
			fitToBoundingBox(bb, 18);
			
			Drawable defaultMarker = getResources().getDrawable(R.drawable.marker1);
			Drawable defaultMarker2 = getResources().getDrawable(R.drawable.marker2);
			ArrayItemizedOverlay itemizedOverlay1 = new ArrayItemizedOverlay(defaultMarker);
			ArrayItemizedOverlay itemizedOverlay2 = new ArrayItemizedOverlay(defaultMarker2);
			OverlayItem item1 = new OverlayItem(route.get(0), "Begin", "Begining of the route");
			OverlayItem item2 = new OverlayItem(route.get(route.size()-1), "End", "End of route");
			itemizedOverlay1.addItem(item1);
			itemizedOverlay2.addItem(item2);
			mapView.getOverlays().add(itemizedOverlay1);
			mapView.getOverlays().add(itemizedOverlay2);
		}
	}

	public List<GeoPoint> getRoute() {
		return route;
	}
	
	public String getRouteName() {
		return routeName;
	}
	
	public void setRouteName(String routeName) {
		this.routeName=routeName;
	}

	public void setRoute(List<GeoPoint> route) {
		this.route = route;
	}

	public synchronized void fitToBoundingBox(final BoundingBox pBoundingBox, final int pMaximumZoom) {
		int width = mapView.getWidth();
		int height = mapView.getHeight();
		if (width <= 0 || height <= 0) {
			mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onGlobalLayout() {
					mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					fitToBoundingBox(pBoundingBox, pMaximumZoom);
				}
			});
		} else {
			Projection projection1 = mapView.getProjection();
			GeoPoint pointSouthWest = new GeoPoint(pBoundingBox.getMinLatitude(), pBoundingBox.getMinLongitude());
			GeoPoint pointNorthEast = new GeoPoint(pBoundingBox.getMaxLatitude(), pBoundingBox.getMaxLongitude());
			Point pointSW = new Point();
			Point pointNE = new Point();
			byte maxLvl = (byte) Math.min(pMaximumZoom, mapView.getMapZoomControls().getZoomLevelMax());
			byte zoomLevel = 0;
			while (zoomLevel < maxLvl) {
				byte tmpZoomLevel = (byte) (zoomLevel + 1);
				projection1.toPoint(pointSouthWest, pointSW, tmpZoomLevel);
				projection1.toPoint(pointNorthEast, pointNE, tmpZoomLevel);
				if (pointNE.x - pointSW.x > width) {
					break;
				}
				if (pointSW.y - pointNE.y > height) {
					break;
				}
				zoomLevel = tmpZoomLevel;
			}
			mapView.getMapPosition()
					.setMapCenterAndZoomLevel(new MapPosition(pBoundingBox.getCenterPoint(), zoomLevel));
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.getOverlays().clear();
		Log.i("LifeCycle", "map destroy");
	}

}
