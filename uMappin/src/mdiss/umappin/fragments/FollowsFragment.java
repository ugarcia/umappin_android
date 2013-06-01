package mdiss.umappin.fragments;



import mdiss.umappin.R;

import mdiss.umappin.entities.User;
import mdiss.umappin.ui.adapter.FollowingAdapter;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class FollowsFragment extends ListFragment implements OnClickListener{
	
	
	/**
	 * Android needs empty constructor to recreate the fragment if something 
	 * goes wrong. Then use setDiscussionHeaders. 
	 */
	private User profileUser;
	private Boolean followsFollowed; //false follows True followed

	private ListView list;
	private FollowingAdapter adapter;



	public FollowsFragment() {
	}
	
	public void setProfileData(User pUser,Boolean followsFollowed){
		profileUser = pUser;
		this.followsFollowed = followsFollowed;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.follow_list,container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list=(ListView) getListView();
		
		if(followsFollowed){
			//Following Adapter have to change to followeds
			//TODO
			adapter=new FollowingAdapter(getActivity(), profileUser.getFollows());
		}else{
			adapter=new FollowingAdapter(getActivity(), profileUser.getFollowed());

		}
		
        list.setAdapter(adapter);
		
	}


	@Override
	public void onClick(View v) {
		
	}
	
	
	
}
