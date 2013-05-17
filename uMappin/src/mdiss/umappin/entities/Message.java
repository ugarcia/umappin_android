package mdiss.umappin.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
	private String id;
	private DiscussionUser user;
	private long timeStamp;
	private String text;
	
	public Message(JSONObject json) {
		try {
			this.id=json.getString("id");
			this.user=new DiscussionUser(json.getJSONObject("user"));
			this.text=json.getString("message");
			this.timeStamp=json.getLong("timeStamp");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DiscussionUser getUser() {
		return user;
	}

	public void setUser(DiscussionUser user) {
		this.user = user;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
