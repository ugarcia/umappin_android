package mdiss.umappin.adapters;

import mdiss.umappin.R;
import mdiss.umappin.entities.Discussion;
import mdiss.umappin.entities.Message;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DiscussionMessagesAdapter extends BaseAdapter {

	protected Activity activity;
    protected Discussion discussion;
    private TextView mUser, mText;
    private ImageView mUserImage;
	
    public DiscussionMessagesAdapter(Activity activity, Discussion discussion) {
    	this.activity=activity;
    	this.discussion=discussion;
    }
    
	@Override
	public int getCount() {
		return discussion.getMessages().size();
	}

	@Override
	public Object getItem(int position) {
		return discussion.getMessages().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Message item = discussion.getMessages().get(position);
		if (view==null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_discussion_message, null);
		}
		
		mUser = (TextView) view.findViewById(R.id.user);
		mUser.setText(item.getUser().getName());
		
		mUserImage = (ImageView) view.findViewById(R.id.userImage);
		mUserImage.setImageURI(Uri.parse(item.getUser().getPhoto()));
		
		mText = (TextView) view.findViewById(R.id.text);
		mText.setText(item.getText());
		
		return view;
	}

}
