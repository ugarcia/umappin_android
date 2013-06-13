package mdiss.umappin.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.Projection;
import org.mapsforge.core.GeoPoint;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;

public class GeoMethods {

	public static final int EQUATORIALRADIUS = 6378137;

	/**
	 * @param context
	 *            Receives the Context needed to use Location Service
	 * @return GeoPoint Mapsforge object with the exact point of the last known
	 *         location. If error return GeoPoin(1,1)
	 */
	public static GeoPoint getCurrentLocation(Context context) {
		LocationManager locationmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// We use network as default method to locate, but if we can't use it
		// use others
		Location destination = new Location("network");
		destination = locationmanager.getLastKnownLocation("network");
		if (destination == null) {
			destination = locationmanager.getLastKnownLocation("gps");
		}
		if (destination == null) {
			destination = locationmanager.getLastKnownLocation("passive");
		}
		// Google uses latitude and longitude in microdegrees, so it's
		// necessary to do *1E6
		return new GeoPoint((int) (destination.getLatitude() * 1E6), (int) (destination.getLongitude() * 1E6));

	}

	public static Double getDistance(GeoPoint p1, GeoPoint p2) {
		double dLat = p2.getLatitude() / 1E6 - p1.getLatitude() / 1E6;
		double dLon = p2.getLongitude() / 1E6 - p1.getLongitude() / 1E6;
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(p1.getLatitude()))
				* Math.cos(Math.toRadians(p2.getLatitude())) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return distance * EQUATORIALRADIUS;
	}

	public static JSONObject getGeoJSON(GeoPoint point) {
		JSONObject json = new JSONObject();
		try {
			json.put("type", "Point");
			json.put("coordinates", new JSONArray().put(point.getLatitude()).put(point.getLongitude()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static MapView zoomAndPan(MapView mapView, int minLatE6, int maxLatE6, int minLngE6, int maxLngE6) {
		int width = mapView.getWidth();
		int heigth = mapView.getHeight();
		if (width <= 0 || heigth <= 0) {
			return mapView;
		}
		int cntLat = (maxLatE6 + minLatE6) / 2;
		int cntLng = (maxLngE6 + minLngE6) / 2;

		mapView.getController().setCenter(new GeoPoint(cntLat, cntLng));

		GeoPoint pointSouthWest = new GeoPoint(minLatE6, minLngE6);
		GeoPoint pointNorthEast = new GeoPoint(maxLatE6, maxLngE6);

		Projection projection = mapView.getProjection();
		Point pointSW = new Point();
		Point pointNE = new Point();
		byte maxLvl = (byte) mapView.getMapGenerator().getZoomLevelMax();
		byte zoomLevel = 0;
		for (; zoomLevel < maxLvl;) {
			byte tmpZoomLevel = (byte) (zoomLevel + 1);
			projection.toPoint(pointSouthWest, pointSW, tmpZoomLevel);
			projection.toPoint(pointNorthEast, pointNE, tmpZoomLevel);
			if (pointNE.x - pointSW.x > width) {
				break;
			}
			if (pointSW.y - pointNE.y > heigth) {
				break;
			}
			zoomLevel = tmpZoomLevel;
		}
		mapView.getController().setZoom(zoomLevel);
		return mapView;
	}

	public static double getDistance(List<GeoPoint> routePoints) {// in
																	// kilometers
		double distance = 0.0;
		for (int i = 0; i < routePoints.size() - 1; i++) {
			// we are already avoiding single points and not routes to enter
			// here
			distance += GeoMethods.getDistance(routePoints.get(i), routePoints.get(i + 1));
		}
		return roundOneDecimal(distance * 10);
	}

	private static double roundOneDecimal(double d) {
		return (double) Math.round(d * 10) / 10;
	}
}
