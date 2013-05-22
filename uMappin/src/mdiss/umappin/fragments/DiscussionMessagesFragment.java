package mdiss.umappin.fragments;

import mdiss.umappin.R;
import mdiss.umappin.adapters.DiscussionMessagesAdapter;
import mdiss.umappin.entities.Discussion;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DiscussionMessagesFragment extends ListFragment{

	private Discussion discussion;
	private ListView mList;
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list,container,false);
		mList = (ListView) getListView();
		DiscussionMessagesAdapter adapter = new DiscussionMessagesAdapter(getActivity(),discussion);
		mList.setAdapter(adapter);
		return view;
	}
	
	
}
