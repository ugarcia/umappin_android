package mdiss.umappin.asynctasks.general;


import org.json.JSONArray;
import org.json.JSONException;

import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import android.app.Activity;

import android.os.AsyncTask;


public class GetFollowsAsyncTask extends AsyncTask<String, Void, JSONArray> {


	public User user;
	public  Activity activity;
	public ProfileFragment fragment;
	public GetFollowsAsyncTask(User pUser,Activity pActivity) {
		this.user = pUser;
		this.activity = pActivity;
	}

	protected JSONArray doInBackground(String... urls) {
		String response = HttpConnections.makeGetRequest(Constants.followsUri, null, null, activity);
		JSONArray array=new JSONArray();
		try {
			array = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array;		
 
	}
	
	

	protected void onPostExecute(JSONArray result) {
		
		user.setFollows(result);
		
	}



}
