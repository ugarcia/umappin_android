package mdiss.umappin.asynctasks.profile;

import mdiss.umappin.asynctasks.general.DownloadPictureAsyncTask;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.Constants;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Looper;


public class DownloadProfilePictureAsyncTaskProfile extends DownloadPictureAsyncTask {


	User user;

	public DownloadProfilePictureAsyncTaskProfile(User pUser,Activity pActivity, ProfileFragment pFragment) {
		super(pActivity, pFragment);
		user = pUser;
	}

	
	

	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		user.setProfilePicture(result);

		new Thread(){
			public void run(){
				Looper.prepare();
				fragment.getMyhandler().sendEmptyMessage(Constants.profilePicture);

			}
		}.start();
	}



}
