package mdiss.umappin.fragments;


import com.google.android.maps.MapView;

import mdiss.umappin.utils.GeoMethods;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {

	private MapView mapView;
	
	@SuppressLint("NewApi")
	public MapFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//mapView = new MapView(getActivity(), new MapnikTileDownloader());
		mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
       // mapView.setCenter(GeoMethods.getCurrentLocation(getActivity())); 
        mapView.getController().setZoom(16);
		return mapView;
	}
}
