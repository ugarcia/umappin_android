package mdiss.umappin.fragments;

import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.mapgenerator.tiledownloader.MapnikTileDownloader;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import mdiss.umappin.R;
import mdiss.umappin.ui.MainActivity;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.GeoMethods;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class PictureFragment extends Fragment {

	private MapView mapView;
	private FrameLayout mFrameLayout;
	private GestureDetector gestureDetector;
	private View.OnTouchListener gestureListener;
	private GeoPoint picturePoint;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.locate_picture, container, false);
		mFrameLayout = (FrameLayout) view.findViewById(R.id.map_container);

		mapView = new MapView(getActivity(), new MapnikTileDownloader());
		mapView.getController().setZoom(16);
		mapView.setBuiltInZoomControls(true);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		picturePoint = GeoMethods.getCurrentLocation(getActivity());
		mapView.setCenter(picturePoint);

		gestureDetector = new GestureDetector(getActivity(), new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		mapView.setOnTouchListener(gestureListener);
		mFrameLayout.addView(mapView);
		return view;
	}

	/**
	 * Using global variable picturePoint, draws a OverlayItem in map in that
	 * location
	 */
	private void putMarkerInMap() {
		mapView.getOverlays().clear();
		Drawable defaultMarker = getResources().getDrawable(R.drawable.marker);
		ArrayItemizedOverlay itemizedOverlay = new ArrayItemizedOverlay(defaultMarker);
		OverlayItem item = new OverlayItem(picturePoint, Constants.picturePointName, Constants.picturePointDesc);
		itemizedOverlay.addItem(item);
		mapView.getOverlays().add(itemizedOverlay);
		mapView.setCenter(picturePoint);
	}

	class MyGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.i(Constants.logMap, "On single tap");
			picturePoint = mapView.getProjection().fromPixels((int) e.getX(), (int) e.getY());
			putMarkerInMap();
			return super.onSingleTapConfirmed(e);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.picture, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MainActivity main = (MainActivity) getActivity();
		switch (item.getItemId()) {
		case R.id.action_new_photo:
			main.dispatchTakePictureIntent(MainActivity.ACTION_TAKE_PHOTO_B);
			return true;
		case R.id.action_upload_photo:
			GeoPoint currentPoint = GeoMethods.getCurrentLocation(getActivity());
			//TODO send photo
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		mapView.getOverlays().clear();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		putMarkerInMap();
	}
}
