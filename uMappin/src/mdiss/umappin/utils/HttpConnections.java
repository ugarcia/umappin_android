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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import mdiss.umappin.ui.LoginActivity;
import mdiss.umappin.ui.MainActivity;
import mdiss.umappin.utils.Login;

public class HttpConnections {

	
	/**
	 * @param url the url where making the connection
	 * @param body the body of the request
	 * @param header the header of the request, we don't need token
	 * @return the response body in a String
	 */
	
	static Activity parentAct;
	static String lastUrl;
	static List<NameValuePair> lastBody;
	static List<NameValuePair> lastHeader;
	static String lastResponse;
	private static Boolean loginFinish =false;
	
	public static String makeGetRequest(String url, List<NameValuePair>  body, List<NameValuePair> header,
			Activity parentActivity) {
		parentAct = parentActivity;
		lastBody =body;
		lastHeader = header;
		lastUrl = url;
	
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("token",Login.getToken());
		Log.i("HTTP", "header = token: "+Login.getToken());
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
		lastResponse = responseBody;
		return responseBody;
	}

	/**
	 * @param url the url where making the connection
	 * @param body the body of the request
	 * @param header the header of the request, we don't need token
	 * @return the response body in a String
	 */
	public static String makePostRequest(String url,
			List<NameValuePair> body, List<NameValuePair> header,
			Activity parentActivity) {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		Log.i("HTTP", "POST URL: " + url);
		String responseBody = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(body));
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
		
		lastResponse = responseBody;
		return responseBody;
	}	
	
	public static String getToken(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(Constants.prefsName, Context.MODE_PRIVATE);
		return prefs.getString("token", "default");
	}
	
	/*
	 * We call always after ask from info from AsyncTasks to check if the token is valid
	 * If it isn't valid it calls to LoginActivity.
	 */
	public static void goToLoginIfneed(){
		if (lastResponse.compareToIgnoreCase(Constants.unauthorizedError)==0){
			 Intent intent = new Intent(parentAct, LoginActivity.class);
			 parentAct.startActivity(intent);
		}
	}
	
}
