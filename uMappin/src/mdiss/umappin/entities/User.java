package mdiss.umappin.entities;

import java.util.ArrayList;

import mdiss.umappin.R;
import mdiss.umappin.asynctasks.DownloadProfilePictureAsyncTask;
import mdiss.umappin.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class User {

	private String id;
	private String name;
	private String photoUri;
	private ArrayList<User> followed;
	private ArrayList<User> follows;
	private Bitmap profilePicture =null;
	
	public User(JSONObject json) {
		try {
			this.photoUri = Constants.pictureUri+json.getString("profilePicture")+"/content";	
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}


	public String getPhotoUri() {
		return photoUri;
	}


	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}


	public void setPhoto(String photo) {
		this.photoUri = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setProfilePicture(Bitmap picture){
		this.profilePicture = picture;
	}


	public Bitmap getProfilePicture() {
		return this.profilePicture;
	}
}
