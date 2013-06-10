package mdiss.umappin.asynctasks;

import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.fragments.TimelineFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

import mdiss.umappin.entities.Publication;

public class GetNewsAsyncTask extends AsyncTask<Void,Void,JSONArray> {

	private Activity activity;
	
	public GetNewsAsyncTask(Activity activity) {
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
		JSONArray array = new JSONArray();
		try {
			array = new JSONArray(HttpConnections.makeGetRequest(Constants.uMappinUrl + "news", null, null, activity));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	protected void onPostExecute(JSONArray result) {
		super.onPostExecute(result);
		TimelineFragment fragment = new TimelineFragment();
		List<Publication> pubs = Publication.getPublications(result);
		fragment.setPublications(pubs);
		activity.getFragmentManager().beginTransaction().addToBackStack("timeline").replace(R.id.content_frame, fragment).commit();
		activity.findViewById(R.id.loading).setVisibility(View.GONE);
		activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
	}
}
