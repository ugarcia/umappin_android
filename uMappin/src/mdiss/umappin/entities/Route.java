package mdiss.umappin.entities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mdiss.umappin.utils.GeoMethods;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.GeoPoint;

public class Route {

	private String id;
	private String name;
	private int difficulty;
	private List<GeoPoint> routePoints;
	private List<BasicNameValuePair> tags;

	public Route(String id, String name, int difficulty, List<GeoPoint> points, List<BasicNameValuePair> tags) {
		this.id=id;
		this.name=name;
		this.difficulty=difficulty;
		this.routePoints=points;
		this.tags=tags;
	}
	
	public Route(JSONObject json) {
		try {
			this.id = json.getString("id");
			this.name = json.getString("name");
			this.difficulty = json.getInt("difficulty");

			routePoints = new ArrayList<GeoPoint>();
			tags = new ArrayList<BasicNameValuePair>();

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
					String key = (String) keys.next();
					tags.add(new BasicNameValuePair(key,properties.getString(key)));
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

	public List<BasicNameValuePair> getTags() {
		return tags;
	}

	public void addPoint(GeoPoint point) {
		this.routePoints.add(point);
	}
	
	public String getTagsToString() {
		String ret = "";
		Iterator<BasicNameValuePair> iterator = this.tags.iterator();
		if (iterator.hasNext()) {
			BasicNameValuePair vp = iterator.next();
			ret = vp.getName() + ": " + vp.getValue();
		}
		while (iterator.hasNext()) {
			BasicNameValuePair vp = iterator.next();
			ret = ret + ", " + vp.getName() + ": " + vp.getValue();
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
		return (double)Math.round(d * 10) / 10;
	}

	public JSONObject toGeoJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("id",this.id);
			json.put("name",this.name);
			json.put("difficulty",this.difficulty);
			JSONArray coords = new JSONArray();
			Iterator<GeoPoint> iter = this.routePoints.iterator();
			while (iter.hasNext()) {
				coords.put(iter.next().toJSONArray());
			}
			JSONObject geometry = new JSONObject();
			geometry.put("type","LineString");
			geometry.put("coordinates",coords);
			json.put("geometry",geometry);
			
			JSONObject tagJSON = new JSONObject();
			Iterator<BasicNameValuePair> tagIter = tags.iterator();
			while (tagIter.hasNext()) {
				BasicNameValuePair vp = tagIter.next();
				tagJSON.put(vp.getName(), vp.getValue());
			}
			json.put("properties",tagJSON);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		return json;
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
