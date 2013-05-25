package mdiss.umappin.utils;

import mdiss.umappin.R;
import mdiss.umappin.asynctasks.LoginAsyncTask;
import mdiss.umappin.ui.LoginActivity;
import mdiss.umappin.utils.Constants;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class Login  {
	
	/**
	 * @return true if logged, searching if String "user" exists as key in
	 *         preferences
	 */
	private static Activity parentActivity;
	private static String token;
	private static int fakeToken = 0;
	
	public static boolean savedData() {
		SharedPreferences prefs =  parentActivity.getSharedPreferences(Constants.prefsName, Context.MODE_PRIVATE);
		return "default" != prefs.getString("user", "default"); // if value of
																// key "user"
																// doesn't exist
																// take
																// "default" as
																// value
		
	}
	
	/**
	 * Used when login info saved
	 */	
	public static void login(){
		SharedPreferences prefs = parentActivity.getSharedPreferences(Constants.prefsName,
				Context.MODE_PRIVATE);
		String user =prefs.getString("user", "none");
		String password = prefs.getString("password", "none");
		firstLogin(user, password);
	}
	
	

	
	/**
	 * Used when no data saved
	 * @param email
	 * @param password
	 */
	public static void firstLogin(String email, String password){

		new LoginAsyncTask(parentActivity).execute(email, password);
		
	}
	
	/**
	 * @param username
	 * @param password
	 */
	public static void saveLoginPreferences(String username, String password, String pToken) {
		SharedPreferences prefs = parentActivity.getSharedPreferences(Constants.prefsName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("user", username);
		editor.putString("password", password);
		editor.putString("token", pToken);
		token = pToken;
		editor.commit();
		Log.i(Constants.logPrefs,"user-password: " + username + "-" + password + ". Saved");
	}
	
	public static void setParentActivity(Activity activity){
		parentActivity =  activity;
	}

	public static String getToken() {
		// TODO Auto-generated method stub
		if (fakeToken <2){ //Used to set as not valid token
			fakeToken++;
			return fakeToken+"";
		}
		return token;
	}

}
