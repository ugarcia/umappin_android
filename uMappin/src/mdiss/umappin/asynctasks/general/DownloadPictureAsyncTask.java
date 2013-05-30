package mdiss.umappin.asynctasks.general;

import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.HttpConnections;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadPictureAsyncTask extends AsyncTask<String, Void, Bitmap> {


	public User user;
	public Activity activity;
	public ProfileFragment fragment;
	public DownloadPictureAsyncTask(Activity pActivity, ProfileFragment pFragment) {
		this.activity = pActivity;
		this.fragment = pFragment;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		
		mIcon11 = HttpConnections.makeBitmapGetRequest(urldisplay, null, null, activity);
		return mIcon11;
 
        
	}
	
	

	protected void onPostExecute(Bitmap result) {
		Log.i("some", "There");
	}



}
