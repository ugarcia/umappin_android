package mdiss.umappin.asynctasks;

import mdiss.umappin.R;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.DiscussionHeadersFragment;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.ui.LoginActivity;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class ProfileAsyncTask extends AsyncTask<Void, Void, JSONObject> {

	private Activity activity;
	
	
	
	public ProfileAsyncTask(Activity activity) {
		this.activity=activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.findViewById(R.id.content_frame).setVisibility(View.GONE);
		activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(Void... arg0) {
		String response = HttpConnections.makeGetRequest(Constants.uMappinUrl + "sessionuser", null, null, activity);
		JSONArray array=new JSONArray();
		try {
			array = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonO =null;
		try {
			jsonO = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonO;
		//return array;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		
		if(!HttpConnections.goToLoginIfneed()){
			User currentUser = new User(result);
			ProfileFragment fragment = new ProfileFragment();
			fragment.setProfileData(currentUser);
			//while(currentUser.getProfilePicture()==null){
				//Whait to profile Picture to be Loaded
				//It could be beter to use a Handler, but not time to implement it,...
			//}
			//DiscussionHeadersFragment fragment = new DiscussionHeadersFragment();
			//fragment.setDiscussionHeaders(Discussion.getListFromJSONArray(result));
			activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
			activity.findViewById(R.id.loading).setVisibility(View.GONE);
			activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
			
			new DownloadProfilePictureAsyncTask(currentUser,activity,fragment).execute(currentUser.getPhotoUri());

		
		}

	}




}
