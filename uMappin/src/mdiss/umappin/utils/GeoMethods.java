package mdiss.umappin.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.GeoPoint;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class GeoMethods {
	
	public static final int EQUATORIALRADIUS=6378137;
	
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
			return new GeoPoint(42.6, -2.92);
		}
	}
	
	public static Double getDistance(GeoPoint p1, GeoPoint p2) {
		double dLat = p2.getLatitude()-p1.getLatitude();
		double dLon = p2.getLongitude()-p1.getLongitude();
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(p1.getLatitude()))
		        * Math.cos(Math.toRadians(p2.getLatitude())) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		    double distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    return distance * EQUATORIALRADIUS;
	}
	
	public static JSONObject getGeoJSON(GeoPoint point) {
		JSONObject json = new JSONObject();
		try {
			json.put("type", "Point");
			json.put("coordinates",new JSONArray().put(point.getLatitude()).put(point.getLongitude()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
