package mdiss.umappin.asynctasks;

import mdiss.umappin.utils.ImageUtils;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView mImageView;
	
	public ImageDownloadAsyncTask(ImageView iv) {
		mImageView = iv;
	}
	
	@Override
	protected Bitmap doInBackground(String... arg0) {
		Bitmap bm = null;
		bm = ImageUtils.getImage(arg0[0], arg0[0]);
		bm = ImageUtils.scaleCenterCrop(bm, 200, 200);
		return bm;
	}
	
	@Override
	protected void onPostExecute(Bitmap bm) {
		super.onPostExecute(bm);
		mImageView.setImageBitmap(bm);
	}

}
