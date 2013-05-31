package mdiss.umappin.ui.adapter;

import java.util.ArrayList;

import mdiss.umappin.R;
import mdiss.umappin.entities.User;
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FollowingAdapter extends BaseAdapter implements OnClickListener{
 
    private Activity activity;
    private static LayoutInflater inflater=null;
    ArrayList<User> users;
    ImageButton unfollowButton;
 
    public FollowingAdapter(Activity a, ArrayList<User> pUsers){
        activity = a;
        users = pUsers;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return users.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.follows_row, null);
 
        User user = users.get(position);
        TextView name = (TextView)vi.findViewById(R.id.follows_name); // name
        
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.follows_image); // thumb image
        
        thumb_image.setImageBitmap(user.getProfilePicture());
        unfollowButton = (ImageButton)vi.findViewById(R.id.unfollow_button);
        unfollowButton.setTag(user); //Binding the user to the button
        unfollowButton.setOnClickListener(this);
        name.setText(user.getName());
 
 

        return vi;
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == unfollowButton.getId()){
			User currentUser = (User)v.getTag();
			users.remove(currentUser);
			notifyDataSetChanged();
			
			//TODO
			
		}
		
	}
    
   

}
