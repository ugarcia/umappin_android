package mdiss.umappin.adapters;

import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.entities.Publication;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.distance = (TextView) view.findViewById(R.id.distance);
			holder.difficulty = (TextView) view.findViewById(R.id.difficulty);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Publication item = items.get(position);
		//holder.name.setText(item.getName());
		return view;
	}

	public static class ViewHolder {
		public TextView name, distance, difficulty;
	}
	
}
