package mdiss.umappin.utils;


import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class GeoMethods {
	
	/**
	 * @param context Receives the Context needed to use Location Service
	 * @return GeoPoint Mapsforge object with the exact point of the last known location. If error return GeoPoin(1,1)
	 */
	public static GeoPoint getCurrentLocation(Context context) {
		LocationManager locationmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		//We use network as default method to locate, but if we can't use it use others
		Location destination = new Location("network");
		String methodForLocate = "network";
		try {
			destination = locationmanager.getLastKnownLocation("network");
			if (destination == null) {
				destination = locationmanager.getLastKnownLocation("gps");
				methodForLocate = "gps";
			}
			if (destination == null) {
				destination = locationmanager.getLastKnownLocation("passive");
				methodForLocate = "passive";
			}
			Log.i(Constants.logGeoMethods, "It's been used " + methodForLocate + " for locating");
	
			//Google uses latitude and longitude in microdegrees, so it's necessary to do *1E6
			return new GeoPoint((int) (destination.getLatitude() * 1E6), (int) (destination.getLongitude() * 1E6));
		} catch (Exception e) {
			e.printStackTrace();
			//We return a point to show if we can't use location
			//TODO show something if can't get location
			return new GeoPoint(1, 1);
		}
	}
}
