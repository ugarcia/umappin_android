package mdiss.umappin.entities;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.asynctasks.profile.DownloadProfilePictureAsyncTask;
import mdiss.umappin.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class User {

	private String id;
	private String name;
	private String photoUri;
	private String firstName;
	private String lastName;
	private ArrayList<User> followed;
	private ArrayList<User> follows;
	private Bitmap profilePicture =null;
	
	public User(JSONObject json) {
		try {
			this.photoUri = Constants.pictureUri+json.getString("profilePicture")+"/content";	
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			this.setFirstName(json.getString("firstName"));
			this.setLastName(json.getString("lastName"));
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	public void setFollows(JSONArray jFollows){
		
		this.follows = getArrayListFromJsonArray(jFollows);
	}
	public ArrayList<User> getFollows(){
		return this.follows;
	}
	
	public void setFollowed(JSONArray jFollowed){
		
		this.followed = getArrayListFromJsonArray(jFollowed);
	}
	public ArrayList<User> getFollowed(){
		return this.followed;
	}
	
	
	private ArrayList<User> getArrayListFromJsonArray(JSONArray array){
		ArrayList<User> list = new ArrayList<User>();
		for (int i=0, length = array.length();i<length;i++) {
			try {
				list.add(new User(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
		
	}
}
