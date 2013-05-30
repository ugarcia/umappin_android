package mdiss.umappin.entities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mdiss.umappin.utils.GeoMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.GeoPoint;

public class Route {

	private String id;
	private String name;
	private int difficulty;
	private List<GeoPoint> routePoints;
	private Set<String> tags;

	public Route(JSONObject json) {
		try {
			this.id = json.getString("id");
			this.name = json.getString("name");
			this.difficulty = json.getInt("difficulty");

			routePoints = new ArrayList<GeoPoint>();
			tags = new HashSet<String>();

			if (!json.isNull("geometry")) {
				JSONObject geometry = json.getJSONObject("geometry");
				JSONArray points = geometry.getJSONArray("coordinates");
				for (int i = 0; i < points.length(); i++) {
					JSONArray coord = points.getJSONArray(i);
					routePoints.add(new GeoPoint(coord.getDouble(0), coord.getDouble(1)));
				}
			}
			if (!json.isNull("properties")) {
				JSONObject properties = json.getJSONObject("properties");
				Iterator<?> keys = properties.keys();
				while (keys.hasNext()) {
					tags.add((String) keys.next());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public List<GeoPoint> getRoutePoints() {
		return routePoints;
	}

	public Set<String> getTags() {
		return tags;
	}

	public String getTagsToString() {
		String ret = "";
		Iterator<String> iterator = this.tags.iterator();
		if (iterator.hasNext()) {
			ret = iterator.next();
		}
		while (iterator.hasNext()) {
			ret = ret + ", " + iterator.next();
		}
		return ret;
	}

	public double getDistance() {// in kilometers
		double distance = 0.0;

		for (int i = 0; i < routePoints.size() - 1; i++) {
			// we are already avoiding single points and not routes to enter
			// here
			distance += GeoMethods.getDistance(routePoints.get(i), routePoints.get(i + 1));
		}
		return roundOneDecimal(distance / 1000);
	}

	private double roundOneDecimal(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		return Double.valueOf(twoDForm.format(d));
	}

	public static List<Route> routesFromJSON(JSONArray array) {
		ArrayList<Route> list = new ArrayList<Route>();
		for (int i = 0; i < array.length(); i++) {
			try {
				list.add(new Route(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
