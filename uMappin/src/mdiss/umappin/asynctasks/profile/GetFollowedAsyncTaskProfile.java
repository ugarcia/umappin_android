package mdiss.umappin.asynctasks.profile;


import org.json.JSONArray;
import org.json.JSONException;

import mdiss.umappin.asynctasks.general.GetFollowedAsyncTask;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Looper;


public class GetFollowedAsyncTaskProfile extends GetFollowedAsyncTask {


	
	public GetFollowedAsyncTaskProfile(User pUser,Activity pActivity, ProfileFragment pFragment) {
		super(pUser, pActivity, pFragment);
	}

	
	

	protected void onPostExecute(JSONArray result) {
		
		super.onPostExecute(result);
		
		new Thread(){
			public void run(){
				Looper.prepare();
				fragment.getMyhandler().sendEmptyMessage(Constants.profileFollowed);

			}
		}.start();
	}



}
