package mdiss.umappin.asynctasks.general;

import org.json.JSONException;
import org.json.JSONObject;

import mdiss.umappin.R;
import mdiss.umappin.utils.HttpConnections;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

public class SendJSONHttp extends AsyncTask<String, Void, Boolean> {

	private Activity activity;
	
	public SendJSONHttp(Activity activity) {
		
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.findViewById(R.id.content_frame).setVisibility(View.GONE);
		activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected Boolean doInBackground(String... arg0) {//
		String url = arg0[0];
		JSONObject jsonBody=new JSONObject();
		try {
			jsonBody = new JSONObject(arg0[1]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HttpConnections.makeJsonPostRequest(url, jsonBody, null, null);
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		activity.findViewById(R.id.loading).setVisibility(View.GONE);
		activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
	}
	
	protected Activity getActivity() {
		return activity;
	}
	
}
