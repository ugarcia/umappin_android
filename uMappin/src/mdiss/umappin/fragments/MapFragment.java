package mdiss.umappin.fragments;

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
	
	public MapFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mapView = new MapView(getActivity(), new MapnikTileDownloader());
		mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setCenter(GeoMethods.getCurrentLocation(getActivity()));
        //mapView.setCenter(new GeoPoint(41.40, 2.15));
        mapView.getController().setZoom(16);
        mapView.setBuiltInZoomControls(true);
        
//        Drawable defaultMarker = getResources().getDrawable(R.drawable.marker);
        
        Paint wayPaint = new Paint();
        wayPaint.setColor(color.Chocolate);
        wayPaint.setStyle(Paint.Style.FILL);
        wayPaint.setAlpha(64);
        wayPaint.setStrokeWidth(5);
        wayPaint.setStrokeCap(Cap.ROUND);
        wayPaint.setStrokeJoin(Paint.Join.ROUND);
        
        Paint wayPaint2 = new Paint();
        wayPaint.setColor(color.Red);
        wayPaint.setStyle(Paint.Style.FILL);
        wayPaint.setAlpha(64);
        wayPaint.setStrokeWidth(5);
        wayPaint.setStrokeCap(Cap.ROUND);
        wayPaint.setStrokeJoin(Paint.Join.ROUND);
        ArrayWayOverlay wayOverlay = new ArrayWayOverlay(wayPaint,wayPaint2);
        GeoPoint gp1 = new GeoPoint(41.38, 2.15);
        GeoPoint gp2 = new GeoPoint(41.39, 2.15);
        GeoPoint gp3 = new GeoPoint(41.40, 2.15);
        GeoPoint gp4 = new GeoPoint(41.41, 2.15);
        
        OverlayWay way = new OverlayWay(new GeoPoint[][] { { gp1, gp2, gp3, gp4 } }, wayPaint, wayPaint2);
        wayOverlay.addWay(way);
        mapView.getOverlays().add(wayOverlay);
		return mapView;
	}
}
