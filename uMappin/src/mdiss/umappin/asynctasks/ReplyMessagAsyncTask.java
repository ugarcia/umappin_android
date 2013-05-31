package mdiss.umappin.asynctasks;

import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONObject;

import android.os.AsyncTask;

public class ReplyMessagAsyncTask extends AsyncTask<JSONObject,Void,Boolean> {

	@Override
	protected Boolean doInBackground(JSONObject... params) {
		HttpConnections.makeJsonPostRequest(Constants.uMappinUrl + "messages", params[0], null, null);
		return true;
	}
	
}
