package mdiss.umappin.asynctasks;

import mdiss.umappin.R;
import mdiss.umappin.entities.Route;
import mdiss.umappin.fragments.RouteFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

public class RoutesAsyncTask extends AsyncTask<String, Void, JSONArray> {

	Activity activity;
	JSONObject currentPoint;

	public RoutesAsyncTask(Activity activity) {
		this.activity = activity;
	}

	public RoutesAsyncTask(Activity activity, JSONObject geoJSON) {
		this.activity = activity;
		this.currentPoint=geoJSON;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.findViewById(R.id.content_frame).setVisibility(View.GONE);
		activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONArray doInBackground(String... arg0) {
		String response;
		if (currentPoint!=null) {
			 response = HttpConnections.makeJsonPostRequest(Constants.uMappinUrl + "routes" + arg0[0],currentPoint ,null, this.activity);
		} else {
			response = HttpConnections.makeGetRequest(Constants.uMappinUrl + "routes" + arg0[0],null ,null, this.activity);
		}
		JSONArray json=new JSONArray();
		try {
			json = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	protected void onPostExecute(JSONArray result) {
		super.onPostExecute(result);
		RouteFragment fragment = new RouteFragment();
		fragment.setRoutes(Route.routesFromJSON(result));
		activity.getFragmentManager().beginTransaction().addToBackStack("routes").replace(R.id.content_frame, fragment).commit();
		activity.findViewById(R.id.loading).setVisibility(View.GONE);
		activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
	}
}
