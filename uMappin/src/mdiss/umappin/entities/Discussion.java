package mdiss.umappin.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Discussion {

	private String id;
	private int unread;
	private String subject;
	private long timeStamp;
	private String lastWrote;
	private List<DiscussionUser> users;
	private List<Message> messages;
	
	public Discussion(JSONObject json) {
		try {
			this.id=json.getString("id");
			String unread=json.getString("unread");
			if (unread.equals("")) {
				this.unread=0;
			} else {
				this.unread=Integer.parseInt(unread);
			}
			this.subject=json.getString("subject");
			this.timeStamp=json.getLong("timeStamp");
			this.lastWrote=json.getString("lastWrote");
			
			//Get users from array and put them in the ArrayList
			ArrayList<DiscussionUser> users = new ArrayList<DiscussionUser>();
			JSONArray arrayUsers = json.getJSONArray("users");
			for (int i=0; i<arrayUsers.length(); i++) {
				users.add(new DiscussionUser(arrayUsers.getJSONObject(i)));
			}
			this.users=users;
			
			//Get messages (if exists) and put them in the messages attribute
			ArrayList<Message> messages = new ArrayList<Message>();
			if (!json.isNull("messages")) {
				JSONArray array = json.getJSONArray("messages");
				for (int i=0; i<array.length();i++) {
					messages.add(new Message(array.getJSONObject(i)));
				}
			}
			this.messages=messages;
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

	public int getUnread() {
		return unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getLastWrote() {
		return lastWrote;
	}

	public void setLastWrote(String lastWrote) {
		this.lastWrote = lastWrote;
	}

	public List<DiscussionUser> getUsers() {
		return users;
	}

	public void setUsers(List<DiscussionUser> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public static List<Discussion> getListFromJSONArray(JSONArray array) {
		List<Discussion> list = new ArrayList<Discussion>();
		for (int i=0;i<array.length();i++) {
			try {
				list.add(new Discussion(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
