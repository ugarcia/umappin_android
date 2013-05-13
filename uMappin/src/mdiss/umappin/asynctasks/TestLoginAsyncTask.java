package mdiss.umappin.asynctasks;

import mdiss.umappin.utils.HttpConnections;
import android.os.AsyncTask;

public class TestLoginAsyncTask extends AsyncTask<String, Void, Boolean> {

	@Override
	protected Boolean doInBackground(String... params) {
		String responseString = HttpConnections.makeGetRequest("http://130.206.138.182:8001/discussions",params[0]);
		return (responseString!=null);
	}

}
