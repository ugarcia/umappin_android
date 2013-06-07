package mdiss.umappin.adapters;

import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.entities.Publication;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PublicationAdapter extends BaseAdapter {

	protected Activity activity;
	protected List<Publication> items;
	
	public PublicationAdapter(Activity activity, List<Publication> items) {
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
		return items.indexOf(items.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_publication, null);
			holder = new ViewHolder();
			holder.username = (TextView) view.findViewById(R.id.username);
			holder.subject = (TextView) view.findViewById(R.id.subject);
			holder.content = (TextView) view.findViewById(R.id.content);
			holder.numberOfLikes = (TextView) view.findViewById(R.id.numberOfLikes);
			holder.like = (Button) view.findViewById(R.id.buttonLike);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Publication item = items.get(position);
		holder.username.setText(item.getUsername());
		holder.subject.setText(item.getSubject());
		holder.content.setText(item.getContent());
		holder.like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO send like to server
			}
		});
		//holder.name.setText(item.getName());
		return view;
	}

	public static class ViewHolder {
		public TextView username, subject, content, numberOfLikes;
		public Button like;
	}
	
}
