package mdiss.umappin.adapters;

import java.util.List;

import mdiss.umappin.R;
import mdiss.umappin.entities.Route;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteAdapter extends BaseAdapter {

	protected Activity activity;
	protected List<Route> items;

	public RouteAdapter(Activity activity, List<Route> routes) {
		this.activity=activity;
		this.items=routes;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_route, null);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.distance = (TextView) view.findViewById(R.id.distance);
			holder.difficulty = (TextView) view.findViewById(R.id.difficulty);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Route item = items.get(position);
		holder.name.setText(item.getName());
		holder.distance.setText(item.getDistance() + " km");
		holder.difficulty.setText(String.valueOf(item.getDifficulty()));

		return view;
	}

	public static class ViewHolder {
		public TextView name, distance, difficulty;
	}

}
