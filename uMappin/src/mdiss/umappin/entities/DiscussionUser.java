package mdiss.umappin.entities;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscussionUser {

	private String id;
	private String name;
	private URL photo;
	
	public DiscussionUser(JSONObject json) {
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			this.setPhoto(new URL(json.getString("photo")));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public URL getPhoto() {
		return photo;
	}

	public void setPhoto(URL photo) {
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
