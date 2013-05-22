package mdiss.umappin.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscussionUser {

	private String id;
	private String name;
	private String photo;
	
	public DiscussionUser(JSONObject json) {
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			this.setPhoto(json.getString("profilePicture"));
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
