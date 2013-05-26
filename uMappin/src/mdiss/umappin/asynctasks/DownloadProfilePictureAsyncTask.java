package mdiss.umappin.asynctasks;

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
		//String response = HttpConnections.makeGetRequest(urldisplay, null, null, activity);
		/*try {
			HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);
 
            mIcon11= BitmapFactory.decodeByteArray(bytes, 0,
                    bytes.length);
			//InputStream in = new ByteArrayInputStream(response.getBytes("UTF-8"));
			//mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;*/
		
		//TODO
		//This is for test, I have to think how to move to HTTPConnections
		//Now we get HTTPConnections requestBody no the HttpUriRequest
		HttpUriRequest request = new HttpGet(urldisplay.toString());
		request.addHeader("token",Login.getToken());
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response;
		try {
			response = httpClient.execute(request);
			StatusLine statusLine = response.getStatusLine();
	        int statusCode = statusLine.getStatusCode();
	        if (statusCode == 200) {
	            HttpEntity entity = response.getEntity();
	            byte[] bytes = EntityUtils.toByteArray(entity);
	 
	            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
	                    bytes.length);
	            return bitmap;
	        } else {
	            throw new IOException("Download failed, HTTP response code "
	                    + statusCode + " - " + statusLine.getReasonPhrase());
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
 
        
	}
	
	

	protected void onPostExecute(Bitmap result) {
		Log.i("some", "There");
		user.setProfilePicture(result);
		
		new Thread(){
			public void run(){
				Looper.prepare();
				fragment.getMyhandler().sendEmptyMessage(1);

			}
		}.start();
	}



}
