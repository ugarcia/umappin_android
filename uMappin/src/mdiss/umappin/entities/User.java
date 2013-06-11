package mdiss.umappin.entities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import mdiss.umappin.asynctasks.general.DownloadPictureAsyncTask;
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
	


	private ArrayList<User> followed;
	private ArrayList<User> follows;
	private Bitmap profilePicture =null;
	
	public User(JSONObject json) {
		setInitData(json);
	}
	
	public void setInitData(JSONObject json){
		try {
			this.photoId = json.getString("profilePicture");
			//Trash but,... no want to fight with server side,...
			if (json.getString("profilePicture").contains("content")){ //follows
				this.photoUri = Constants.uMappinUrl.substring(0, Constants.uMappinUrl.length() -1)+
						json.getString("profilePicture");
			}else{//sessionUser
				this.photoUri = Constants.pictureUri+json.getString("profilePicture")+"/content";	

			}
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			if (!json.isNull("firstName")) this.setFirstName(json.getString("firstName"));
			if (!json.isNull("lastName")) this.setLastName(json.getString("lastName"));
			this.setEmail(json.getString("email"));
			
			this.followed = new ArrayList<User>();
			this.follows = new ArrayList<User>();
			
			
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
	public ArrayList<User> getFollowing(){
		return this.follows;
	}
	
	public void setFollowed(JSONArray jFollowed){
		
		this.followed = getArrayListFromJsonArray(jFollowed);
	}
	public ArrayList<User> getFollowers(){
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
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void getFollowsImages(Activity pActivity) {
		getUsersListImages(pActivity, this.getFollowing());
		
	}
	
	public void getFollowedImages(Activity pActivity){
		getUsersListImages(pActivity, this.getFollowers());

	}
	
	public Boolean isFollower(String id){//following you
		
		for (User u: this.getFollowers()){
			if (u.getId().compareTo(id)==0){
				return true;
			}
		}
		
		return false;
	}
	
	public Boolean isFollowing(String id){// you are following 
		for (User u: this.getFollowing()){
			if (u.getId().compareTo(id)==0){
				return true;
			}
		}
		
		return false;
	}
	public void getUsersListImages(Activity pActivity, ArrayList<User> users){
		for (User follow : users){
			String uri = follow.getPhotoUri();
			DownloadPictureAsyncTask downPick = new DownloadPictureAsyncTask(pActivity);
			Bitmap image =null;
			try {
				image = downPick.execute(uri).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			follow.setProfilePicture(image);
		}
	}
}
