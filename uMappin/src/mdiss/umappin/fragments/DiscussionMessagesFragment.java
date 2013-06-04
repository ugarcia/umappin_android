package mdiss.umappin.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import mdiss.umappin.R;
import mdiss.umappin.adapters.DiscussionMessagesAdapter;
import mdiss.umappin.asynctasks.DiscussionMessagesAsyncTask;
import mdiss.umappin.asynctasks.ReplyMessagAsyncTask;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.ui.MainActivity;

import android.app.ListFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class DiscussionMessagesFragment extends ListFragment{

	private Discussion discussion;
	private ListView mList;
	private EditText mReplyText;
	private Button mReplyButton;
	
	public DiscussionMessagesFragment() {
		super();
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mList = (ListView) getListView();
		DiscussionMessagesAdapter adapter = new DiscussionMessagesAdapter(getActivity(),discussion);
		mList.setAdapter(adapter);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1);
		mList.setLayoutParams(params);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.list,container,false);
		LinearLayout ll = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.reply_message,container,false);
		view.addView(ll, view.getChildCount());
		
		//Get current window's height and width
		Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,(int)(point.y/6)));
        
        mReplyButton = (Button) view.findViewById(R.id.reply_button);
        mReplyText = (EditText) view.findViewById(R.id.reply_text);
        mReplyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = mReplyText.getText().toString();
				
				JSONObject json = new JSONObject();
				try {
				json.put("discussion_id", discussion.getId());
				json.put("message",text);
				new ReplyMessagAsyncTask().execute(json);
				getFragmentManager().popBackStack();
				new DiscussionMessagesAsyncTask((MainActivity) getActivity()).execute(discussion.getId());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//TODO core---- instead of requesting all info again create inside the app the new message
				getFragmentManager().popBackStack();
				new DiscussionMessagesAsyncTask((MainActivity) getActivity()).execute(discussion.getId());
			}
		});
		return view;
	}
	
	
}
