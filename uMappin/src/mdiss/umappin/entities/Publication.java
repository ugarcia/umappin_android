package mdiss.umappin.entities;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Publication {

	private String id;
	private String writerId;
	private String subject;
	private String postPicture;
	private int likes;
	private int replies;
	private long timeStamp;
	private String lastWrote;
	private String content;
	private String username="";
	
	
	public Publication(JSONObject json) {
		try {
			this.id=json.getString("id");
			if (!json.isNull("writerId")) {
				this.writerId=json.getString("writerId");
			}
			if (!json.isNull("subject")) {
				this.subject=json.getString("subject");
			} else {
				this.subject="";
			}
			if (!json.isNull("content")) {
				this.content=json.getString("content");
			} else {
				this.content="";
			}
			if (!json.isNull("postPicture")) {
				this.postPicture=Constants.uMappinUrl.subSequence(0, Constants.uMappinUrl.length()-1) + json.getString("postPicture");
			} else {
				this.postPicture="";
			}
			if (!json.isNull("firstName")) {
				this.username=json.getString("firstName")+" ";
			}
			if (!json.isNull("lastName")) {
				this.username=this.username+json.getString("lastName");
			}
			this.lastWrote=json.getString("lastWrote");
			this.timeStamp=json.getLong("timeStamp");
			this.replies=json.getInt("replies");
			this.likes=json.getJSONArray("likes").length();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getId() {
		return id;
	}
	public String getSubject() {
		return subject;
	}
	public String getPostPicture() {
		return postPicture;
	}
	public int getLikes() {
		return likes;
	}
	public int getReplies() {
		return replies;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public String getLastWrote() {
		return lastWrote;
	}
	public String getContent() {
		return content;
	}
	public String getUsername() {
		return username;
	}
	public String getWriterId() {
		return writerId;
	}
	
	public static List<Publication> getPublications(JSONArray array) {
		ArrayList<Publication> list = new ArrayList<Publication>();
		for (int i = 0; i < array.length(); i++) {
			try {
				list.add(new Publication(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
