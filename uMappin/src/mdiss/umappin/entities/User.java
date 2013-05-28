package mdiss.umappin.entities;

import java.util.ArrayList;

import mdiss.umappin.asynctasks.profile.ProfileSaveAsyncTask;
import mdiss.umappin.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;

public class User {

	private String id;
	private String name;
	private String photoUri;
	private String photoId;
	private String firstName;
	private String lastName;
	private String email;
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	private ArrayList<User> followed;
	private ArrayList<User> follows;
	private Bitmap profilePicture =null;
	
	public User(JSONObject json) {
		try {
			this.photoId = json.getString("profilePicture");
			this.photoUri = Constants.pictureUri+json.getString("profilePicture")+"/content";	
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			if (!json.isNull("firstName")) this.setFirstName(json.getString("firstName"));
			if (!json.isNull("lastName")) this.setLastName(json.getString("lastName"));
			this.setEmail(json.getString("email"));
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}


	public String getPhotoId() {
		return photoId;
	}


	public void setPhotoId(String photoId) {
		this.photoId = photoId;
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
	
	public void save(Activity activity){
		new ProfileSaveAsyncTask(activity).execute(this);		
	}
}
