package mdiss.umappin.fragments;



import mdiss.umappin.R;

import mdiss.umappin.entities.User;
import mdiss.umappin.utils.Constants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends HandleredFragment implements OnClickListener{
	
	
	/**
	 * Android needs empty constructor to recreate the fragment if something 
	 * goes wrong. Then use setDiscussionHeaders. 
	 */
	private User profileUser;
	private ImageView profilePicture;
	private TextView followsNumber;
	private TextView followedNumber;
	private TextView name;
	private TextView firstNameLabel;
	private TextView lastNameLabel;
	private TextView emailLabel;

	private TextView firstName;
	private TextView lastName;
	private TextView email;

	
	private ImageButton editFirstNameButton;
	private EditText editFirstNameText;
	private ImageButton editFirstNameConfirmButton;
	private ImageButton editFirstNameCancelButton;
	
	private ImageButton editLastNameButton;
	private EditText editLastNameText;
	private ImageButton editLastNameConfirmButton;
	private ImageButton editLastNameCancelButton;
	
	private ImageButton editEmailButton;
	private EditText editEmailText;
	private ImageButton editEmailConfirmButton;
	private ImageButton editEmailCancelButton;
	
	private Button saveProfileButton;
	
	private ImageButton showFollows;
	private ImageButton showFollowed;
	
	private InputMethodManager keyboard;
	
	private Boolean pictureLoad = false;
	private Boolean followsLoad = false;
	private Boolean followedLoad = false;


	@SuppressLint("HandlerLeak")
	private final Handler myHandler = new Handler(){
		public void handleMessage(final Message msg){
			switch (msg.what){
			case Constants.profilePicture:
				profilePicture.setImageBitmap(profileUser.getProfilePicture());
				pictureLoad = true;
				break;
			case Constants.profileFollows:
				followsNumber.setText(profileUser.getFollowing().size()+"");
				showFollows.setEnabled(true);
				followsLoad = true;
				new Thread(){ //don't want to stop the UI
					public void run(){
						profileUser.getFollowsImages(getActivity());
					}
				}.start();
				
				break;
			case Constants.profileFollowed:
				followedNumber.setText(profileUser.getFollowers().size()+"");
				showFollowed.setEnabled(true);
				followedLoad = true;
				new Thread(){ //don't want to stop the UI
					public void run(){
						profileUser.getFollowedImages(getActivity());
					}
				}.start();
				break;
				
			}
		}
	};
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
		keyboard= (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		
		//get elements from XML
		name = (TextView) getView().findViewById(R.id.firstName);		
		profilePicture = (ImageView)getView().findViewById(R.id.profileImage);
		followsNumber = (TextView)getView().findViewById(R.id.profileFollowsNumber);
		followedNumber = (TextView)getView().findViewById(R.id.profileFollowedNumber);
		firstNameLabel = (TextView)getView().findViewById(R.id.profile_first_name_label);
		lastNameLabel = (TextView)getView().findViewById(R.id.profile_last_name_label);
		emailLabel = (TextView)getView().findViewById(R.id.profile_email_label);


		firstName = (TextView)getView().findViewById(R.id.profileFirstName);
		lastName = (TextView)getView().findViewById(R.id.profileLastName);
		email =(TextView)getView().findViewById(R.id.profileEmail);
		
		editFirstNameButton =(ImageButton)getView().findViewById(R.id.edit_first_name_button);
		editFirstNameText = (EditText)getView().findViewById(R.id.edit_first_name_text);
		editFirstNameCancelButton = (ImageButton)getView().findViewById(R.id.edit_first_name_cancel_button);
		editFirstNameConfirmButton = (ImageButton)getView().findViewById(R.id.edit_first_name_confirm_button);
		
		editLastNameButton =(ImageButton)getView().findViewById(R.id.edit_last_name_button);
		editLastNameText = (EditText)getView().findViewById(R.id.edit_last_name_text);
		editLastNameCancelButton = (ImageButton)getView().findViewById(R.id.edit_last_name_cancel_button);
		editLastNameConfirmButton = (ImageButton)getView().findViewById(R.id.edit_last_name_confirm_button);
		
		editEmailButton =(ImageButton)getView().findViewById(R.id.edit_email_button);
		editEmailText = (EditText)getView().findViewById(R.id.edit_email_text);
		editEmailCancelButton = (ImageButton)getView().findViewById(R.id.edit_email_cancel_button);
		editEmailConfirmButton = (ImageButton)getView().findViewById(R.id.edit_email_confirm_button);
		saveProfileButton = (Button)getView().findViewById(R.id.profile_save_button);
		
		showFollows = (ImageButton)getView().findViewById(R.id.show_follows);
		showFollowed = (ImageButton)getView().findViewById(R.id.show_followed);

		//Set Elements default values
		name.setText(profileUser.getName());
		firstName.setText(profileUser.getFirstName());
		lastName.setText(profileUser.getLastName());
		email.setText(profileUser.getEmail());
		
		if(followsLoad)
			followsNumber.setText(profileUser.getFollowing().size()+"");
		if(followedLoad)
			followedNumber.setText(profileUser.getFollowers().size()+"");
		
		if(pictureLoad){
			profilePicture.setImageBitmap(profileUser.getProfilePicture());
		}
		
		//Set buttons listeners		
		editFirstNameButton.setOnClickListener(this );
		editFirstNameCancelButton.setOnClickListener(this);
		editFirstNameConfirmButton.setOnClickListener(this);
		
		editLastNameButton.setOnClickListener(this );
		editLastNameCancelButton.setOnClickListener(this);
		editLastNameConfirmButton.setOnClickListener(this);
		
		editEmailButton.setOnClickListener(this );
		editEmailCancelButton.setOnClickListener(this);
		editEmailConfirmButton.setOnClickListener(this);
		
		saveProfileButton.setOnClickListener(this);
		
		showFollows.setOnClickListener(this);
		if(!followedLoad)
			showFollows.setEnabled(false); //not clickable before follows loaded
		
		showFollowed.setOnClickListener(this);
		if(!followedLoad)
			showFollowed.setEnabled(false); //not clickable before followed loaded



		
	}
	public  Handler getMyhandler() {
		return this.myHandler;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == this.editFirstNameButton.getId()) //Edit First Name
		{
			editFirstNameText.setText(firstName.getText());

			
			firstName.setVisibility(View.INVISIBLE);
			editFirstNameButton.setVisibility(View.INVISIBLE);
			firstNameLabel.setVisibility(View.INVISIBLE);
			editFirstNameText.setVisibility(View.VISIBLE);
			editFirstNameCancelButton.setVisibility(View.VISIBLE);
			editFirstNameConfirmButton.setVisibility(View.VISIBLE);
			saveProfileButton.setVisibility(View.GONE);

		}else if (v.getId() == this.editFirstNameConfirmButton.getId()){//confirm edit First name
			firstName.setText(editFirstNameText.getText());
			
			firstName.setVisibility(View.VISIBLE);
			editFirstNameButton.setVisibility(View.VISIBLE);
			firstNameLabel.setVisibility(View.VISIBLE);
			editFirstNameText.setVisibility(View.INVISIBLE);
			editFirstNameCancelButton.setVisibility(View.INVISIBLE);
			editFirstNameConfirmButton.setVisibility(View.INVISIBLE);
			saveProfileButton.setVisibility(View.VISIBLE);
			keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

		}else if (v.getId() == this.editFirstNameCancelButton.getId()){//Cancel edit First name
			firstName.setVisibility(View.VISIBLE);
			editFirstNameButton.setVisibility(View.VISIBLE);
			firstNameLabel.setVisibility(View.VISIBLE);
			editFirstNameText.setVisibility(View.INVISIBLE);
			editFirstNameCancelButton.setVisibility(View.INVISIBLE);
			editFirstNameConfirmButton.setVisibility(View.INVISIBLE);
			keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

		}else if(v.getId() == this.editLastNameButton.getId()) //Edit Last Name
		{
			editLastNameText.setText(lastName.getText());

			
			lastName.setVisibility(View.INVISIBLE);
			editLastNameButton.setVisibility(View.INVISIBLE);
			lastNameLabel.setVisibility(View.INVISIBLE);
			editLastNameText.setVisibility(View.VISIBLE);
			editLastNameCancelButton.setVisibility(View.VISIBLE);
			editLastNameConfirmButton.setVisibility(View.VISIBLE);
			saveProfileButton.setVisibility(View.GONE);

		}else if (v.getId() == this.editLastNameConfirmButton.getId()){//confirm edit Last name
			lastName.setText(editLastNameText.getText());
			
			lastName.setVisibility(View.VISIBLE);
			editLastNameButton.setVisibility(View.VISIBLE);
			lastNameLabel.setVisibility(View.VISIBLE);
			editLastNameText.setVisibility(View.INVISIBLE);
			editLastNameCancelButton.setVisibility(View.INVISIBLE);
			editLastNameConfirmButton.setVisibility(View.INVISIBLE);
			saveProfileButton.setVisibility(View.VISIBLE);
			keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

		}else if (v.getId() == this.editLastNameCancelButton.getId()){//Cancel edit Last name
			lastName.setVisibility(View.VISIBLE);
			editLastNameButton.setVisibility(View.VISIBLE);
			lastNameLabel.setVisibility(View.VISIBLE);
			editLastNameText.setVisibility(View.INVISIBLE);
			editLastNameCancelButton.setVisibility(View.INVISIBLE);
			editLastNameConfirmButton.setVisibility(View.INVISIBLE);
			keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

		}else if(v.getId() == this.editEmailButton.getId()) //Edit email
		{
			editEmailText.setText(email.getText());

			
			email.setVisibility(View.INVISIBLE);
			editEmailButton.setVisibility(View.INVISIBLE);
			emailLabel.setVisibility(View.INVISIBLE);
			editEmailText.setVisibility(View.VISIBLE);
			editEmailCancelButton.setVisibility(View.VISIBLE);
			editEmailConfirmButton.setVisibility(View.VISIBLE);
			saveProfileButton.setVisibility(View.GONE);

		}else if (v.getId() == this.editEmailConfirmButton.getId()){//confirm edit email
			email.setText(editEmailText.getText());
			
			email.setVisibility(View.VISIBLE);
			editEmailButton.setVisibility(View.VISIBLE);
			emailLabel.setVisibility(View.VISIBLE);
			editEmailText.setVisibility(View.INVISIBLE);
			editEmailCancelButton.setVisibility(View.INVISIBLE);
			editEmailConfirmButton.setVisibility(View.INVISIBLE);
			saveProfileButton.setVisibility(View.VISIBLE);
			keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

		}else if (v.getId() == this.editEmailCancelButton.getId()){//Cancel edit Email
			email.setVisibility(View.VISIBLE);
			editEmailButton.setVisibility(View.VISIBLE);
			emailLabel.setVisibility(View.VISIBLE);
			editEmailText.setVisibility(View.INVISIBLE);
			editEmailCancelButton.setVisibility(View.INVISIBLE);
			editEmailConfirmButton.setVisibility(View.INVISIBLE);
			keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		}else if (v.getId() == this.saveProfileButton.getId()){//Save profile button
			profileUser.setFirstName(firstName.getText().toString());
			profileUser.setLastName(lastName.getText().toString());
			profileUser.setEmail(email.getText().toString());
			
			profileUser.save(getActivity());
			saveProfileButton.setVisibility(View.GONE);
		}else if (v.getId() == this.showFollows.getId()){
			FollowsFragment fragment = new FollowsFragment();
			fragment.setProfileData(profileUser, true);
			getFragmentManager().beginTransaction().addToBackStack("follows").replace(R.id.content_frame, fragment).commit();
		}else if (v.getId() == this.showFollowed.getId()){
			FollowsFragment fragment = new FollowsFragment();
			fragment.setProfileData(profileUser, false);
			getFragmentManager().beginTransaction().addToBackStack("following").replace(R.id.content_frame, fragment).commit();
		}
	}
	
}
