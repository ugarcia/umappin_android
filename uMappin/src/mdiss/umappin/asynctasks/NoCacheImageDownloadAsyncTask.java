package mdiss.umappin.asynctasks;

import mdiss.umappin.utils.HttpConnections;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NoCacheImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView mImageView;
	
	public NoCacheImageDownloadAsyncTask(ImageView iv) {
		mImageView = iv;
	}
	
	@Override
	protected Bitmap doInBackground(String... arg0) {
		Bitmap bm = null;
		bm = HttpConnections.makeBitmapGetRequest(arg0[0], null, null);
		return bm;
	}
	
	@Override
	protected void onPostExecute(Bitmap bm) {
		super.onPostExecute(bm);
		mImageView.setImageBitmap(bm);
	}

}
