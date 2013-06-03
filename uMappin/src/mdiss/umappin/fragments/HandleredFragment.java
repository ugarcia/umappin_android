package mdiss.umappin.fragments;


import android.app.Fragment;
import android.os.Handler;
import android.view.View.OnClickListener;

public abstract class HandleredFragment extends Fragment implements OnClickListener{
	//with profile fragment we have the handler used by some of external tasks
	
	/**
	 * Android needs empty constructor to recreate the fragment if something 
	 * goes wrong. Then use setDiscussionHeaders. 
	 */





	public HandleredFragment() {
	}
	
	
	public abstract Handler getMyhandler();
	
	
	
}
