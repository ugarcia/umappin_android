package mdiss.umappin.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mdiss.umappin.R;
import mdiss.umappin.adapters.DiscussionHeaderAdapter;
import mdiss.umappin.asynctasks.DiscussionMessagesAsyncTask;
import mdiss.umappin.asynctasks.SendMessage;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.entities.User;
import mdiss.umappin.utils.Constants;
import android.app.Dialog;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class DiscussionHeadersFragment extends ListFragment {

	private List<Discussion> discussionHeaders;
	ListView mList;
	public  User profileUser;
	public DiscussionHeadersFragment discussionHFragment;

	/**
	 * Android needs empty constructor to recreate the fragment if something
	 * goes wrong. Then use setDiscussionHeaders.
	 */
	public DiscussionHeadersFragment() {
		this.setDiscussionHeaders(new ArrayList<Discussion>());
		this.discussionHFragment = this;
	}

	public List<Discussion> getDiscussionHeaders() {
		return discussionHeaders;
	}

	public void setDiscussionHeaders(List<Discussion> discussionHeaders) {
		this.discussionHeaders = discussionHeaders;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().setTitle("Messages");
		return inflater.inflate(R.layout.list, container, false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		super.onViewCreated(view, savedInstanceState);
		mList = (ListView) getListView();
		DiscussionHeaderAdapter adapter = new DiscussionHeaderAdapter(getActivity(), discussionHeaders);
		mList.setAdapter(adapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				new DiscussionMessagesAsyncTask(getActivity()).execute(discussionHeaders.get(position).getId());
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.discussions, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_message:
			final Dialog dialog = new Dialog(getActivity());
			dialog.setContentView(R.layout.new_discussion);
			dialog.setTitle("New message");
			final List<String> receivers = new ArrayList<String>();
			Button sendMessage = (Button) dialog.findViewById(R.id.buttonSendMessage);
			sendMessage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					JSONObject json = new JSONObject();
					try {
					EditText et = (EditText) dialog.findViewById(R.id.subject);
					json.put("subject", et.getText().toString());
					
					et = (EditText) dialog.findViewById(R.id.message);
					JSONObject message = new JSONObject();
					message.put("message", et.getText().toString());
					json.put("messages", message);
					
					JSONArray arrayReceivers = new JSONArray(receivers);
					json.put("users", arrayReceivers);
					
					String url = Constants.uMappinUrl + "discussions";
					new SendMessage(getActivity()).execute(url,json.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			
			Button selectReceivers = (Button) dialog.findViewById(R.id.buttonSelectReceivers);
			selectReceivers.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//TODO open receivers list and on close store receivers in List
					//id marta = 516b2d1fe4b0fe5e23ee9a6e
					
				}
			});
			dialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setProfileData(User currentUser) {
		this.profileUser = currentUser;
		Log.i("profileUser","set profile user");
		
	}
}
