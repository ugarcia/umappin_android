package mdiss.umappin.asynctasks;

import mdiss.umappin.R;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.fragments.DiscussionMessagesFragment;
import mdiss.umappin.ui.MainActivity;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;

public class DiscussionMessagesAsyncTask extends AsyncTask<String, Void, JSONObject> {

	MainActivity activity;
	
	public DiscussionMessagesAsyncTask(MainActivity activity) {
		this.activity=activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.findViewById(R.id.content_frame).setVisibility(View.GONE);
		activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(String... id) {
		String response = HttpConnections.makeGetRequest(Constants.uMappinUrl + "discussions/" + id[0], null,null, this.activity);
		JSONObject json=new JSONObject();
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		DiscussionMessagesFragment fragment = new DiscussionMessagesFragment();
		fragment.setDiscussion(new Discussion(result));
		activity.getFragmentManager().beginTransaction().addToBackStack("message").replace(R.id.content_frame, fragment).commit();
		activity.setTitle(fragment.getDiscussion().getSubject());
		activity.findViewById(R.id.loading).setVisibility(View.GONE);
		activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
	}
}
