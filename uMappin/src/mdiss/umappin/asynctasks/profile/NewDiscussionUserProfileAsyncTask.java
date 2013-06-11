package mdiss.umappin.asynctasks.profile;

import mdiss.umappin.asynctasks.general.GetFollowedAsyncTask;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.DiscussionHeadersFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

public class NewDiscussionUserProfileAsyncTask extends AsyncTask<Void, Void, JSONObject> {

	private Activity activity;
	
	
	
	public NewDiscussionUserProfileAsyncTask(Activity activity) {
		this.activity=activity;
	}
	
	
	
	@Override
	protected JSONObject doInBackground(Void... arg0) {
		String response = HttpConnections.makeGetRequest(Constants.uMappinUrl + "sessionuser", null, null, activity);

		JSONObject jsonO =null;
		try {
			jsonO = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonO;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		
		if(!HttpConnections.goToLoginIfneed()){
			User currentUser = new User(result);
			DiscussionHeadersFragment fragment = new DiscussionHeadersFragment();
			fragment.setProfileData(currentUser);
			
			//new GetFollowedAsyncTask(currentUser, activity).execute();
			new GetFollowedAsyncTask(currentUser, activity).execute();
		
		}
	}

}
