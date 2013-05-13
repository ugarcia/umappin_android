package mdiss.umappin.asynctasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class LoginAsyncTask extends AsyncTask<Void, Void, Boolean> {

	@Override
	protected Boolean doInBackground(Void... arg0) {
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://130.206.138.182:8001/login");
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
	    nameValuePairs.add(new BasicNameValuePair("email", "jarriaga86@gmail.com"));  
	    nameValuePairs.add(new BasicNameValuePair("password", "gruagrua"));  
	    try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			JSONObject json = new JSONObject(EntityUtils.toString(entity));
			Log.i("JSON",json.toString());
			new TestLoginAsyncTask().execute(json.getString("token"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
