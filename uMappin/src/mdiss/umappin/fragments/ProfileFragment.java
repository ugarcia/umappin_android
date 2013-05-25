package mdiss.umappin.fragments;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.adapters.DiscussionHeaderAdapter;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.entities.User;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileFragment extends ListFragment{
	
	
	/**
	 * Android needs empty constructor to recreate the fragment if something 
	 * goes wrong. Then use setDiscussionHeaders. 
	 */
	private User profileUser;
	public ProfileFragment() {
	}
	
	public void setProfileData(User pUser){
		profileUser = pUser;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.profile,container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		TextView name = (TextView) getView().findViewById(R.id.firstName);
		name.setText(profileUser.getName());
		
	}
	
}
