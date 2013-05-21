package mdiss.umappin.fragments;

import java.util.ArrayList;
import java.util.List;

import mdiss.umappin.entities.Discussion;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiscussionMessagesFragment extends ListFragment{

	private List<Discussion> discussion;
	
	public DiscussionMessagesFragment() {
		this.setDiscussion(new ArrayList<Discussion>());
	}

	public List<Discussion> getDiscussion() {
		return discussion;
	}

	public void setDiscussion(List<Discussion> discussion) {
		this.discussion = discussion;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
}
