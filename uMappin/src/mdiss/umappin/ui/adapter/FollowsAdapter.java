package mdiss.umappin.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import mdiss.umappin.R;
import mdiss.umappin.entities.User;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FollowsAdapter extends BaseAdapter {
 
    private Activity activity;
    private static LayoutInflater inflater=null;
    ArrayList<User> users;
 
    public FollowsAdapter(Activity a, ArrayList<User> pUsers){
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
        
        name.setText(user.getName());
 
 

        return vi;
    }
    
   

}
