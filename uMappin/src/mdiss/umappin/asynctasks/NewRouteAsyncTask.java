package mdiss.umappin.asynctasks;

import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONObject;

import android.os.AsyncTask;

public class NewRouteAsyncTask extends AsyncTask<JSONObject, Void, Boolean> {

	@Override
	protected Boolean doInBackground(JSONObject... arg0) {
		HttpConnections.makeJsonPostRequest(Constants.uMappinUrl + "routes", arg0[0], null, null);
		return null;
	}

}
