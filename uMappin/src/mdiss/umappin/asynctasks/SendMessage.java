package mdiss.umappin.asynctasks;

import android.app.Activity;
import android.widget.Toast;
import mdiss.umappin.asynctasks.general.SendJSONHttp;

public class SendMessage extends SendJSONHttp {

	public SendMessage(Activity activity) {
		super(activity);
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Toast.makeText(super.getActivity(), "Message sended correctly", Toast.LENGTH_LONG).show();
	}

}
