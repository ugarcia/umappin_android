package mdiss.umappin.adapters;

import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.asynctasks.ImageDownloadAsyncTask;
import mdiss.umappin.entities.Publication;
import mdiss.umappin.utils.Constants;
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
			holder.like = (ImageButton) view.findViewById(R.id.buttonLike);
			holder.image = (ImageView)view.findViewById(R.id.userImage);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Publication item = items.get(position);
		holder.username.setText(item.getUsername());
		String urlPhoto = Constants.uMappinUrl.substring(0, Constants.uMappinUrl.length()-1) + item.getImage();
		new ImageDownloadAsyncTask(holder.image).execute(urlPhoto);
		
		holder.subject.setText(item.getSubject());
		holder.content.setText(item.getContent());
		
		if (item.getLikes() ==1) {
			holder.numberOfLikes.setText("1 like");
		} else if (item.getLikes()  >1){
			holder.numberOfLikes.setText(item.getLikes()  + " likes");
		}else{
			holder.numberOfLikes.setText("No likes");
		}
		holder.like.setOnClickListener(new OnClickListener() {
		boolean liked=false;
			@Override
			public void onClick(View v) {
				
				ViewGroup vg = (ViewGroup) v.getParent();
				TextView likes = (TextView) vg.findViewById(R.id.numberOfLikes);
				int numberLikes;
				if (likes.getText().toString().compareToIgnoreCase("No likes")==0){
					numberLikes = 0;
				}else{
					numberLikes = Integer.valueOf(likes.getText().toString().split(" ")[0]);
				}
				if (!liked) {
					liked=true;
					
					numberLikes++;
					
					((ImageButton)v).setImageResource(android.R.drawable.ic_menu_delete);
				}else{
					liked=false;
					((ImageButton)v).setImageResource(android.R.drawable.ic_menu_add);
					numberLikes--;
					
				}
				if (numberLikes==1) {
					likes.setText("1 like");
				} else if (numberLikes >1){
					likes.setText(numberLikes + " likes");
				}else{
					likes.setText("No likes");
				}
			}
		});
		return view;
	}

	public static class ViewHolder {
		public TextView username, subject, content, numberOfLikes;
		public ImageButton like;
		public ImageView image;
	}
	
}
