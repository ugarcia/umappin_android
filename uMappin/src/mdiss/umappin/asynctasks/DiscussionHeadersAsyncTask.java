package mdiss.umappin.asynctasks;

import mdiss.umappin.R;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.fragments.DiscussionHeadersFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

public class DiscussionHeadersAsyncTask extends AsyncTask<Void, Void, JSONArray> {

	private Activity activity;
	
	public DiscussionHeadersAsyncTask(Activity activity) {
		this.activity=activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.findViewById(R.id.content_frame).setVisibility(View.GONE);
		activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONArray doInBackground(Void... arg0) {
		String response = HttpConnections.makeGetRequest(Constants.uMappinUrl + "discussions", null, null, activity);
		JSONArray array=new JSONArray();
		try {
			array = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	protected void onPostExecute(JSONArray result) {
		super.onPostExecute(result);
		DiscussionHeadersFragment fragment = new DiscussionHeadersFragment();
		fragment.setDiscussionHeaders(Discussion.getListFromJSONArray(result));
		activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		activity.findViewById(R.id.loading).setVisibility(View.GONE);
		activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
	}
}
