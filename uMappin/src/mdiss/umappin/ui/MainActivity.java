package mdiss.umappin.ui;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.mapgenerator.tiledownloader.MapnikTileDownloader;

import mdiss.umappin.R;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.GeoMethods;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends MapActivity {

	private MapView mapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		mapView = (MapView) findViewById(R.id.mapView);
		mapView = new MapView(this, new MapnikTileDownloader());
		mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setCenter(GeoMethods.getCurrentLocation(this)); 
        mapView.getController().setZoom(16);
        setContentView(mapView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_logout) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.logout_title));
			alertDialog.setMessage(getString(R.string.logout_message));
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
					getString(R.string.button_positive),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							clearPrefs();
							dialog.dismiss();
							finish();
						}
					});
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
					getString(R.string.button_negative),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void clearPrefs() {
		SharedPreferences prefs = getSharedPreferences(Constants.prefsName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		Log.i(Constants.logPrefs,"preferences cleared");
	}
}
