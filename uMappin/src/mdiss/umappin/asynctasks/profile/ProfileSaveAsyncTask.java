package mdiss.umappin.asynctasks.profile;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.entities.User;
import mdiss.umappin.exceptions.UserNotFoundException;
import mdiss.umappin.ui.LoginActivity;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ProfileSaveAsyncTask extends AsyncTask<User, Void, Boolean> {

	Activity activity;
	

	

	public ProfileSaveAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected Boolean doInBackground(User... params) {
		
		User user = params[0];
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
		nameValuePairs.add(new BasicNameValuePair("id", user.getId()));
		nameValuePairs.add(new BasicNameValuePair("firstName", user.getFirstName()));
		nameValuePairs.add(new BasicNameValuePair("lastName", user.getLastName()));



		try {
			String responseBody = HttpConnections.makePutRequest(
					
					Constants.userUri+"/"+user.getId(), nameValuePairs, null,
					activity);
			JSONObject json = null;
			if (responseBody.contains("User not found")) {
				throw new UserNotFoundException(params[0] + "-" + params[1]
						+ " : incorrect");
			} 
			
			json = new JSONObject(responseBody);
			
			
			
			Log.i("JSON", json.toString());
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
		Log.i("HTTP", result.toString());


	}

	
	
}
