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
import org.json.JSONArray;
import org.json.JSONException;

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

public class GetFollowedAsyncTask extends AsyncTask<String, Void, JSONArray> {


	User user;
	Activity activity;
	ProfileFragment fragment;
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
		
		new Thread(){
			public void run(){
				Looper.prepare();
				fragment.getMyhandler().sendEmptyMessage(Constants.profileFollowed);

			}
		}.start();
	}



}
