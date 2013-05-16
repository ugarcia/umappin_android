package mdiss.umappin.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpConnections {

	
	/**
	 * @param url the url where making the connection
	 * @param token the token of the user to make API calls
	 * @return the response body in a String
	 */
	public static String makeGetRequest(String url, String token) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("token", token);
		Log.i("HTTP", "header = token: "+token);
		Log.i("HTTP", "GET URL: " + url);
		String responseBody = null;
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			responseBody = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("HTTP", "GET response: " + responseBody);
		return responseBody;
	}

	/**
	 * @param url the url where making the connection
	 * @param nameValuePairs params key:value
	 * @return the response body in a String
	 */
	public static String makePostRequest(String url,
			List<NameValuePair> nameValuePairs) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		Log.i("HTTP", "POST URL: " + url);
		String responseBody = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			responseBody = EntityUtils.toString(entity);
			Log.i("HTTP", "POST: Received JSON: " + responseBody);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return responseBody;
	}	
}
