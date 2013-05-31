package mdiss.umappin.asynctasks.general;

import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.HttpConnections;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadPictureAsyncTask extends AsyncTask<String, Void, Bitmap> {


	public Activity activity;
	public DownloadPictureAsyncTask(Activity pActivity) {
		this.activity = pActivity;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		
		mIcon11 = HttpConnections.makeBitmapGetRequest(urldisplay, null, null, activity);
		Log.i("DownloadPicture", urldisplay);

		return mIcon11;

 
        
	}
	
	

	protected void onPostExecute(Bitmap result) {
	}



}
