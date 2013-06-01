package mdiss.umappin.asynctasks.profile;

import mdiss.umappin.R;
import mdiss.umappin.entities.User;
import mdiss.umappin.fragments.OthersProfileFragment;
import mdiss.umappin.fragments.ProfileFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.HttpConnections;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class OthersProfileAsyncTask extends AsyncTask<User, Void, JSONObject> {

	private Activity activity;
	private User profUser;
	
	
	public OthersProfileAsyncTask(Activity activity) {
		this.activity=activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.findViewById(R.id.content_frame).setVisibility(View.GONE);
		activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected JSONObject doInBackground(User... arg0) {
		profUser = arg0[0];
		String response = HttpConnections.makeGetRequest(Constants.othersProfileUri+profUser.getId()
				, null, null, activity);

		JSONObject jsonO =null;
		try {
			jsonO = new JSONObject(response);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonO;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(JSONObject jsonO) {
		super.onPostExecute(null);
		profUser.setInitData(jsonO);
		Log.i("Task","Open Others Profile,...");
		if(!HttpConnections.goToLoginIfneed()){
			activity.setTitle(profUser.getName()+"' Profile");
			OthersProfileFragment fragment = new OthersProfileFragment();
			fragment.setProfileData(profUser);
			
			activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
			activity.findViewById(R.id.loading).setVisibility(View.GONE);
			activity.findViewById(R.id.content_frame).setVisibility(View.VISIBLE);
			
			new GetFollowsAsyncTaskProfile(profUser, activity, fragment).execute();
			new GetFollowedAsyncTaskProfile(profUser, activity, fragment).execute();
		
		}

	}




}
