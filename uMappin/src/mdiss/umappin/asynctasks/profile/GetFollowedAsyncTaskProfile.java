package mdiss.umappin.asynctasks.profile;


import org.json.JSONArray;
import mdiss.umappin.asynctasks.general.GetFollowedAsyncTask;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.HandleredFragment;
import mdiss.umappin.utils.Constants;
import android.app.Activity;
import android.os.Looper;


public class GetFollowedAsyncTaskProfile extends GetFollowedAsyncTask {

	HandleredFragment fragment;
	
	public GetFollowedAsyncTaskProfile(User pUser,Activity pActivity, HandleredFragment pFragment) {
		super(pUser, pActivity);
		this.fragment = pFragment;
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
