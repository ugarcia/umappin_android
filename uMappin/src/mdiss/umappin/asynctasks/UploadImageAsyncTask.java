package mdiss.umappin.asynctasks;

import org.json.JSONException;
import org.json.JSONObject;

import mdiss.umappin.R;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class UploadImageAsyncTask extends AsyncTask<JSONObject, Void, String> {

	private Activity activity;

	public UploadImageAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.findViewById(R.id.content_frame).setVisibility(View.GONE);
		activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
	}

	@Override
	protected String doInBackground(JSONObject... arg0) {
		JSONObject json = arg0[0];
		try {
			Log.i(Constants.logUploadPhotos,json.getString("title"));
			Log.i(Constants.logUploadPhotos,json.getString("description"));
			Log.i(Constants.logUploadPhotos,String.valueOf(json.getDouble("latitude")));
			Log.i(Constants.logUploadPhotos,String.valueOf(json.getDouble("longitude")));
			Log.i(Constants.logUploadPhotos,String.valueOf(json.getInt("date_created")));
			Log.i(Constants.logUploadPhotos,json.getString("content"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return HttpConnections.makeJsonPostRequest(Constants.uMappinUrl + "photos", arg0[0], null, activity);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Toast.makeText(activity, activity.getString(R.string.sended_photo_toast), Toast.LENGTH_LONG).show();
		activity.findViewById(R.id.loading).setVisibility(View.GONE);
		activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
	}

}
