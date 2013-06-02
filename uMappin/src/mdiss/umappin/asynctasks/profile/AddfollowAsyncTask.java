package mdiss.umappin.asynctasks.profile;


import mdiss.umappin.entities.User;
import mdiss.umappin.exceptions.UserNotFoundException;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;
import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class AddfollowAsyncTask extends AsyncTask<User, Void, Boolean> {

	Activity activity;
	

	

	public AddfollowAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected Boolean doInBackground(User... params) {
		
		User user = params[0];
        JSONObject jsonSend = new JSONObject();


		try {
			
			
			String responseBody = HttpConnections.makeJsonPutRequest(
					
					Constants.addfollowUri+user.getId(), jsonSend, null,
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
