package mdiss.umappin.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import mdiss.umappin.R;
import mdiss.umappin.entities.LateralMenuItem;

public class LateralMenuAdapter  extends ArrayAdapter<LateralMenuItem> {

	private LateralMenuItem items[] = null;
	private Context context;
	 int layoutResourceId; 
	public LateralMenuAdapter(Context context, int resource,
			LateralMenuItem[] objects) {
		super(context, resource, objects);
		
		this.context = context;
		items = objects;
		this.layoutResourceId = resource;
	}
	
	
	   @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        ItemHolder holder = null;

	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new ItemHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.lateral_menu_item_pick);
	            holder.txtTitle = (TextView)row.findViewById(R.id.lateral_menu_item_text);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (ItemHolder)row.getTag();
	        }
	        
	        LateralMenuItem item = items[position];
	        holder.txtTitle.setText(item.getText());
	        holder.imgIcon.setImageResource(item.getIcon());
	        
	        return row;
	    }
	   
	   static class ItemHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	    }

}
