package mdiss.umappin.fragments;



import mdiss.umappin.R;

import mdiss.umappin.entities.User;
import mdiss.umappin.ui.adapter.FollowsAdapter;
import mdiss.umappin.utils.Constants;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	

	private ListView list;
	private FollowsAdapter adapter;


	private final Handler myHandler = new Handler(){
		public void handleMessage(final Message msg){
			switch (msg.what){
			case Constants.profilePicture:
				
				break;
			
				
			}
		}
	};
	public FollowsFragment() {
	}
	
	public void setProfileData(User pUser){
		profileUser = pUser;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.follow_list,container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list=(ListView) getListView();
		adapter=new FollowsAdapter(getActivity(), profileUser.getFollows());
        list.setAdapter(adapter);
        
        


		
	}
	public  Handler getMyhandler() {
		return myHandler;
	}

	@Override
	public void onClick(View v) {
		
	}
	
	
	
}
