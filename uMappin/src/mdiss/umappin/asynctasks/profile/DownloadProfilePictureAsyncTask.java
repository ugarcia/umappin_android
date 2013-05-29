package mdiss.umappin.asynctasks.profile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import mdiss.umappin.R;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;
import mdiss.umappin.utils.Login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;

import android.util.Log;
import android.widget.ImageView;

public class DownloadProfilePictureAsyncTask extends AsyncTask<String, Void, Bitmap> {


	User user;
	Activity activity;
	ProfileFragment fragment;
	public DownloadProfilePictureAsyncTask(User pUser,Activity pActivity, ProfileFragment pFragment) {
		this.user = pUser;
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
		user.setProfilePicture(result);
		
		new Thread(){
			public void run(){
				Looper.prepare();
				fragment.getMyhandler().sendEmptyMessage(Constants.profilePicture);

			}
		}.start();
	}



}
