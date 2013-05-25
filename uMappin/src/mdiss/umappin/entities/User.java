package mdiss.umappin.entities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String id;
	private String name;
	private String photo;
	private ArrayList<User> followed;
	private ArrayList<User> follows;
	
	public User(JSONObject json) {
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			//this.setPhoto(json.getString("profilePicture"));
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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
}
