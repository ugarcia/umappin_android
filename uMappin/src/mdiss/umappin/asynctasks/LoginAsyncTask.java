package mdiss.umappin.asynctasks;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.exceptions.UserNotFoundException;
import mdiss.umappin.ui.MainActivity;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;
import mdiss.umappin.utils.Login;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

	Activity activity;

	public LoginAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected Boolean doInBackground(String... params) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("email", params[0]));
		nameValuePairs.add(new BasicNameValuePair("password", params[1]));
		try {
			String responseBody = HttpConnections.makeFormPostRequest(
					Constants.uMappinUrl + "login", nameValuePairs, null,
					activity);
			JSONObject json = null;
			if (responseBody.contains("User not found")) {
				throw new UserNotFoundException(params[0] + "-" + params[1]
						+ " : incorrect");
			} 
			
			json = new JSONObject(responseBody);
			
			//save login info
			Login.saveLoginPreferences(params[0], params[1],json.getString("token"));
			
			Log.i("JSON", json.toString());
			//new TestLoginAsyncTask().execute(json.getString("token"));
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
		super.onPostExecute(result);
		//TODO fix progress to be compatible with any activity
		if (result) {// Positive login
			if (!(activity instanceof MainActivity)){
	            Intent intent = new Intent(activity, MainActivity.class);
				//activity.showProgress(false);
				activity.startActivity(intent);
				activity.finish();
			}
		} else {// Error while login
			//activity.showProgress(false);
			//activity.mEmailView.setError("Incorrect user or password");
			//TODO
		}
		

	}

	
	
}
