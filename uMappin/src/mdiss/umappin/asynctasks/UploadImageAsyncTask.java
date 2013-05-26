package mdiss.umappin.asynctasks;

import mdiss.umappin.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

public class UploadImageAsyncTask extends AsyncTask<Void, Void, Boolean> {

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
	protected Boolean doInBackground(Void... arg0) {
		/*Make post request {
		 *rest/photos
	    "date_created":123456,
	    "title":"Abc avenue",
		"description":"my favourite road",
		"latitude":1224, 
		"longitude":12333
		}
		
		with returned id make the content post call
		*/
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		activity.findViewById(R.id.loading).setVisibility(View.GONE);
		activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
	}

}
