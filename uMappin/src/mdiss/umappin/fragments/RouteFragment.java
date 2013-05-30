package mdiss.umappin.fragments;

import java.util.List;

import org.mapsforge.core.GeoPoint;

import mdiss.umappin.R;
import mdiss.umappin.adapters.RouteAdapter;
import mdiss.umappin.asynctasks.RoutesAsyncTask;
import mdiss.umappin.entities.Route;
import mdiss.umappin.utils.GeoMethods;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RouteFragment extends ListFragment {

	private List<Route> routes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		View header = getActivity().getLayoutInflater().inflate(R.layout.route_list_header, null);
		header.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
				ListView.LayoutParams.WRAP_CONTENT));
		getListView().addHeaderView(header);
		RouteAdapter adapter = new RouteAdapter(getActivity(), routes);
		setListAdapter(adapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MapFragment fragment = new MapFragment();
				fragment.showRoute(routes.get(position).getRoutePoints());
				getActivity().getFragmentManager().beginTransaction().addToBackStack("map").replace(R.id.content_frame, fragment).commit();
			}
		});
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.route, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_near_routes:
			GeoPoint currentPoint = GeoMethods.getCurrentLocation(getActivity());
			new RoutesAsyncTask(getActivity(), GeoMethods.getGeoJSON(currentPoint)).execute("/near/20");
			getActivity().getActionBar().setTitle("Routes around");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
