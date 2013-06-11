package mdiss.umappin.asynctasks.profile;


import org.json.JSONArray;

import mdiss.umappin.asynctasks.general.GetFollowsAsyncTask;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.HandleredFragment;
import mdiss.umappin.utils.Constants;

import android.app.Activity;
import android.os.Looper;


public class GetFollowsAsyncTaskProfile extends GetFollowsAsyncTask {

	HandleredFragment fragment;


	public GetFollowsAsyncTaskProfile(User pUser,Activity pActivity, HandleredFragment pFragment) {
		super(pUser, pActivity);
		this.fragment = pFragment;
	}


	protected void onPostExecute(JSONArray result) {
		
		super.onPostExecute(result);
		
		new Thread(){
			public void run(){
				Looper.prepare();
				fragment.getMyhandler().sendEmptyMessage(Constants.profileFollows);

			}
		}.start();
	}



}
