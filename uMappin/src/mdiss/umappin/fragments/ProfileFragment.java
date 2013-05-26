package mdiss.umappin.fragments;



import mdiss.umappin.R;

import mdiss.umappin.entities.User;
import mdiss.umappin.utils.Constants;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileFragment extends ListFragment implements OnClickListener{
	
	
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
	private TextView firstName;
	private TextView lastName;
	
	private ImageButton editFirstNameButton;
	private EditText editFirstNameText;
	
	private ImageButton editFirstNameConfirmButton;
	private ImageButton editFirstNameCancelButton;
	
	private Button saveProfileButton;




	private final Handler myHandler = new Handler(){
		public void handleMessage(final Message msg){
			switch (msg.what){
			case Constants.profilePicture:
				profilePicture.setImageBitmap(profileUser.getProfilePicture());
				Log.i("some", msg+"");
				break;
			case Constants.profileFollows:
				followsNumber.setText(profileUser.getFollows().size()+"");
				break;
			case Constants.profileFollowed:
				followedNumber.setText(profileUser.getFollowed().size()+"");
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
		
		//get elements from XML
		name = (TextView) getView().findViewById(R.id.firstName);		
		profilePicture = (ImageView)getView().findViewById(R.id.profileImage);
		followsNumber = (TextView)getView().findViewById(R.id.profileFollowsNumber);
		followedNumber = (TextView)getView().findViewById(R.id.profileFollowedNumber);
		firstNameLabel = (TextView)getView().findViewById(R.id.profile_first_name_label);

		firstName = (TextView)getView().findViewById(R.id.profileFirstName);
		lastName = (TextView)getView().findViewById(R.id.profileLastName);
		
		editFirstNameButton =(ImageButton)getView().findViewById(R.id.edit_first_name_button);
		editFirstNameText = (EditText)getView().findViewById(R.id.edit_first_name_text);
		editFirstNameCancelButton = (ImageButton)getView().findViewById(R.id.edit_first_name_cancel_button);
		editFirstNameConfirmButton = (ImageButton)getView().findViewById(R.id.edit_first_name_confirm_button);
		saveProfileButton = (Button)getView().findViewById(R.id.profile_save_button);
		
		//Set Elements default values
		name.setText(profileUser.getName());
		firstName.setText(profileUser.getFirstName());
		lastName.setText(profileUser.getLastName());
		
		//Set buttons listeners		
		editFirstNameButton.setOnClickListener(this );
		editFirstNameCancelButton.setOnClickListener(this);
		editFirstNameConfirmButton.setOnClickListener(this);


		
	}
	public  Handler getMyhandler() {
		return myHandler;
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
		}else if (v.getId() == this.editFirstNameConfirmButton.getId()){//confirm edit First name
			firstName.setText(editFirstNameText.getText());
			
			firstName.setVisibility(View.VISIBLE);
			editFirstNameButton.setVisibility(View.VISIBLE);
			firstNameLabel.setVisibility(View.VISIBLE);
			editFirstNameText.setVisibility(View.INVISIBLE);
			editFirstNameCancelButton.setVisibility(View.INVISIBLE);
			editFirstNameConfirmButton.setVisibility(View.INVISIBLE);
			saveProfileButton.setVisibility(View.VISIBLE);
		}else if (v.getId() == this.editFirstNameCancelButton.getId()){//Cancel edit First name
			firstName.setVisibility(View.VISIBLE);
			editFirstNameButton.setVisibility(View.VISIBLE);
			firstNameLabel.setVisibility(View.VISIBLE);
			editFirstNameText.setVisibility(View.INVISIBLE);
			editFirstNameCancelButton.setVisibility(View.INVISIBLE);
			editFirstNameConfirmButton.setVisibility(View.INVISIBLE);
		}
	}
	
	
	
}
