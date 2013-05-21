package mdiss.umappin.fragments;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.entities.Discussion;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiscussionHeadersFragment extends ListFragment{
	
	private List<Discussion> discussionHeaders;
	
	/**
	 * Android needs empty constructor to recreate the fragment if something 
	 * goes wrong. Then use setDiscussionHeaders. 
	 */
	public DiscussionHeadersFragment() {
		this.setDiscussionHeaders(new ArrayList<Discussion>());
	}
	
	public List<Discussion> getDiscussionHeaders() {
		return discussionHeaders;
	}

	public void setDiscussionHeaders(List<Discussion> discussionHeaders) {
		this.discussionHeaders = discussionHeaders;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
}
