package mdiss.umappin.asynctasks;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.exceptions.UserNotFoundException;
import mdiss.umappin.ui.LoginActivity;
import mdiss.umappin.ui.MainActivity;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

	LoginActivity activity;

	public LoginAsyncTask(LoginActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Boolean doInBackground(String... params) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("email", params[0]));
		nameValuePairs.add(new BasicNameValuePair("password", params[1]));
		try {
			String responseBody = HttpConnections.makePostRequest(
					Constants.uMappinUrl + "login", nameValuePairs);
			JSONObject json = null;
			if (responseBody.contains("User not found")) {
				throw new UserNotFoundException(params[0] + "-" + params[1]
						+ " : incorrect");
			} else {
				savePreferences(params[0], params[1]);
			}
			json = new JSONObject(responseBody);
			Log.i("JSON", json.toString());
			new TestLoginAsyncTask().execute(json.getString("token"));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {// Positive login
			Intent intent = new Intent(activity, MainActivity.class);
			activity.showProgress(false);
			activity.startActivity(intent);
		} else {// Error while login
			activity.showProgress(false);
			activity.mEmailView.setError("Incorrect user or password");
		}
	}

	/**
	 * @param username
	 * @param password
	 */
	public void savePreferences(String username, String password) {
		SharedPreferences prefs = activity.getSharedPreferences(Constants.prefsName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("user", username);
		editor.putString("password", password);
		editor.commit();
		Log.i(Constants.logPrefs,"user-password: " + username + "-" + password + ". Saved");
	}
	
}
