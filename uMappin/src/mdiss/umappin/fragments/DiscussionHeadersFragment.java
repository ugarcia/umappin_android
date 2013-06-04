package mdiss.umappin.fragments;

import java.util.ArrayList;
import java.util.List;

import org.mapsforge.core.GeoPoint;

import mdiss.umappin.R;
import mdiss.umappin.adapters.DiscussionHeaderAdapter;
import mdiss.umappin.asynctasks.DiscussionMessagesAsyncTask;
import mdiss.umappin.asynctasks.RoutesAsyncTask;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.ui.MainActivity;
import mdiss.umappin.utils.GeoMethods;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DiscussionHeadersFragment extends ListFragment{
	
	private List<Discussion> discussionHeaders;
	ListView mList;
	
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
		return inflater.inflate(R.layout.list,container,false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onViewCreated(view, savedInstanceState);
		mList = (ListView) getListView();
		DiscussionHeaderAdapter adapter = new DiscussionHeaderAdapter(getActivity(),discussionHeaders);
		mList.setAdapter(adapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				new DiscussionMessagesAsyncTask((MainActivity) getActivity()).execute(discussionHeaders.get(position).getId());
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
			((MainActivity) getActivity()).setTitle("New Message");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
