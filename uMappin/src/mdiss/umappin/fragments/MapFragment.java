package mdiss.umappin.fragments;

import java.util.ArrayList;
import java.util.List;

import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.mapgenerator.tiledownloader.MapnikTileDownloader;
import org.mapsforge.android.maps.overlay.ArrayWayOverlay;
import org.mapsforge.android.maps.overlay.OverlayWay;
import org.mapsforge.core.GeoPoint;

import mdiss.umappin.R.color;
import mdiss.umappin.utils.GeoMethods;
import android.app.Fragment;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {

	private MapView mapView;
	private List<GeoPoint> route;

	public MapFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		initializeMap();
		// Drawable defaultMarker =
		// getResources().getDrawable(R.drawable.marker);
		if (!getActivity().getActionBar().getTitle().equals("OpenStreetMap")) {
			showRoute();
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
		route = new ArrayList<GeoPoint>();
		route.add(new GeoPoint(43.25696,-2.923434));
		route.add(new GeoPoint(43.25696,-2.923440));
		route.add(new GeoPoint(43.25696,-2.923444));
		if (!route.isEmpty()) {
			mapView.setCenter(route.get(0));
			Paint wayDefaultPaintFill = new Paint();
			wayDefaultPaintFill.setColor(color.Chocolate);
			wayDefaultPaintFill.setStyle(Paint.Style.FILL);
			wayDefaultPaintFill.setAlpha(32);
			wayDefaultPaintFill.setStrokeWidth(5);
			wayDefaultPaintFill.setStrokeCap(Cap.ROUND);
			wayDefaultPaintFill.setStrokeJoin(Paint.Join.BEVEL);

			Paint wayDefaultPaintOutline = new Paint();
			wayDefaultPaintOutline.setColor(color.Red);
			wayDefaultPaintOutline.setStyle(Paint.Style.FILL);
			wayDefaultPaintOutline.setAlpha(64);
			wayDefaultPaintOutline.setStrokeWidth(5);
			wayDefaultPaintOutline.setStrokeCap(Cap.ROUND);
			wayDefaultPaintOutline.setStrokeJoin(Paint.Join.BEVEL);
			
			ArrayWayOverlay wayOverlay = new ArrayWayOverlay(wayDefaultPaintFill, wayDefaultPaintOutline);
			GeoPoint[] points = route.toArray(new GeoPoint[0]);
			
			OverlayWay way = new OverlayWay(new GeoPoint[][] { points }, wayDefaultPaintOutline, null);
			wayOverlay.addWay(way);
			mapView.getOverlays().add(wayOverlay);
		}
	}

	public List<GeoPoint> getRoute() {
		return route;
	}

	public void setRoute(List<GeoPoint> route) {
		this.route = route;
	}
	
}
