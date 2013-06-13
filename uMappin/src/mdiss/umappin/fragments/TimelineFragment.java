package mdiss.umappin.fragments;

import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.adapters.PublicationAdapter;
import mdiss.umappin.entities.Publication;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimelineFragment extends ListFragment {
	
	List<Publication> publications;
	
	public TimelineFragment(){
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle("Activity");
		return inflater.inflate(R.layout.list,container,false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		PublicationAdapter adapter = new PublicationAdapter(getActivity(),publications);
		setListAdapter(adapter);
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}
}
