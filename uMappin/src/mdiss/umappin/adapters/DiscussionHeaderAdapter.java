package mdiss.umappin.adapters;

import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.entities.DiscussionUser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DiscussionHeaderAdapter extends BaseAdapter {

	protected Activity activity;
    protected List<Discussion> items;
    private TextView mSubject, mUsers;
	
    public DiscussionHeaderAdapter(Activity activity, List<Discussion> items) {
    	this.activity=activity;
    	this.items=items;
    }
    
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.valueOf(items.get(position).getId());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Discussion item = items.get(position);
		
		//for performance try to reuse convertView
		if (view==null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_discussion_header, null);
		}
		
		mSubject = (TextView) view.findViewById(R.id.subject);
		mSubject.setText(item.getSubject());
		
		List<DiscussionUser> usersList = item.getUsers(); 
		mUsers = (TextView) view.findViewById(R.id.users);
		
		//Max characters in users string=200, not to loop thousands users
		String users = "";
		int i=0;
		while(users.length()<200 && i<usersList.size()){ 
			if (i==0) {
				users = usersList.get(i).getName();
			} else {
				users=users + ", " + (usersList.get(i).getName());
			}
			i++;
		}
		mUsers.setText(users);
		
		return view;
	}

}
