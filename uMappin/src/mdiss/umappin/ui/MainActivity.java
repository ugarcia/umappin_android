package mdiss.umappin.ui;





import org.mapsforge.android.maps.MapActivity;

import mdiss.umappin.R;
import mdiss.umappin.asynctasks.DiscussionHeadersAsyncTask;
import mdiss.umappin.fragments.MapFragment;
import mdiss.umappin.utils.Constants;
import mdiss.umappin.utils.Login;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


@SuppressLint("NewApi")
public class MainActivity extends MapActivity {
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] menuOptions = {"Timeline","Messages","Map","Games","Take a photo"};
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.row_drawer_menu,menuOptions));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.open_drawer,  /* "open drawer" description for accessibility */
                R.string.close_drawer  /* "close drawer" description for accessibility */
                ) {
            @SuppressLint("NewApi")
			public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @SuppressLint("NewApi")
			public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        
		
        mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_logout) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.logout_title));
			alertDialog.setMessage(getString(R.string.logout_message));
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
					getString(R.string.button_positive),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							clearPrefs();
							dialog.dismiss();
							finish();
						}
					});
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
					getString(R.string.button_negative),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
						}
					});
			alertDialog.show();
			return true;
		} else if (mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	/* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	//TODO on menu item click
            switch(position) {
            case 0://Timeline
            	getActionBar().setTitle("Timeline");
            	break;
            case 1://Messages
            	getActionBar().setTitle("Messages");
            	new DiscussionHeadersAsyncTask(MainActivity.this).execute();
            	break;
            case 2://Map
            	getActionBar().setTitle("OSMap");
            	MapFragment fragment = new MapFragment();
            	getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            	break;
            case 3://Games
            	getActionBar().setTitle("Play!");
            	break;
            default://Take a photo
            	getActionBar().setTitle("Take a photo");
            }
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

	private void clearPrefs() {
		SharedPreferences prefs = getSharedPreferences(Constants.prefsName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		Log.i(Constants.logPrefs,"preferences cleared");
	}
	

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
