package mdiss.umappin.fragments;



import mdiss.umappin.R;

import mdiss.umappin.entities.User;
import mdiss.umappin.utils.Constants;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class OthersProfileFragment extends HandleredFragment implements OnClickListener{
	//with HandleredFragment we have the handler used by some of external tasks
	
	/**
	 * Android needs empty constructor to recreate the fragment if something 
	 * goes wrong. Then use setDiscussionHeaders. 
	 */
	private User profileUser;
	private ImageView profilePicture;
	private TextView followsNumber;
	private TextView followedNumber;
	private TextView name;


	private TextView firstName;
	private TextView lastName;
	private TextView email;

	
	




	private final Handler myHandler = new Handler(){
		public void handleMessage(final Message msg){
			switch (msg.what){
			case Constants.profileFollows:
				followsNumber.setText(profileUser.getFollowing().size()+"");
				
				break;
			case Constants.profileFollowed:
				followedNumber.setText(profileUser.getFollowers().size()+"");
				
				break;
				
			}
		}
	};
	public OthersProfileFragment() {
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.others_profile,container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		//get elements from XML
		name = (TextView) getView().findViewById(R.id.firstName);		
		profilePicture = (ImageView)getView().findViewById(R.id.profileImage);
		followsNumber = (TextView)getView().findViewById(R.id.profileFollowsNumber);
		followedNumber = (TextView)getView().findViewById(R.id.profileFollowedNumber);
		


		firstName = (TextView)getView().findViewById(R.id.profileFirstName);
		lastName = (TextView)getView().findViewById(R.id.profileLastName);
		email =(TextView)getView().findViewById(R.id.profileEmail);
		
		
		
		//Set Elements default values
		name.setText(profileUser.getName());
		firstName.setText(profileUser.getFirstName());
		lastName.setText(profileUser.getLastName());
		email.setText(profileUser.getEmail());
		profilePicture.setImageBitmap(profileUser.getProfilePicture());

		
		
		



		
	}
	public  Handler getMyhandler() {
		return this.myHandler;
	}

	@Override
	public void onClick(View v) {
		
	}



	public void setProfileData(User profUser) {
		this.profileUser = profUser;
	}
	
	
	
}
