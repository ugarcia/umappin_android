package mdiss.umappin.asynctasks.general;


import org.json.JSONArray;
import org.json.JSONException;

import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Looper;


public class GetFollowedAsyncTask extends AsyncTask<String, Void, JSONArray> {


	User user;
	public Activity activity;
	public ProfileFragment fragment;
	public GetFollowedAsyncTask(User pUser,Activity pActivity, ProfileFragment pFragment) {
		this.user = pUser;
		this.activity = pActivity;
		this.fragment = pFragment;
	}

	protected JSONArray doInBackground(String... urls) {
		String response = HttpConnections.makeGetRequest(Constants.followedUri, null, null, activity);
		JSONArray array=new JSONArray();
		try {
			array = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array;		
 
	}
	
	

	protected void onPostExecute(JSONArray result) {
		
		user.setFollowed(result);
		
	}



}
