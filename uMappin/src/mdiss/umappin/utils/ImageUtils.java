package mdiss.umappin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.Display;

public class ImageUtils {

	public static LruCache<String, Bitmap> cache=new LruCache<String, Bitmap>(4*1024*1024);
	final static int MAX_OPENGL_WIDTH_HEIGHT=2048;
	
	public static Bitmap getImage(String key, String url) {
		Bitmap bm=cache.get(key);
		if (bm==null) {
			bm = HttpConnections.makeBitmapGetRequest(url, null, null);
			cache.put(key, bm);
			Log.i(Constants.logImageUtils,"Get bitmap from URL");
		} else {
			Log.i(Constants.logImageUtils,"Get bitmap from cache");
		}
		return bm;
	}
	
	static public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
		float sourceWidth = source.getWidth();
		float sourceHeight = source.getHeight();

		if (newHeight>newWidth && newHeight > MAX_OPENGL_WIDTH_HEIGHT)  {
			newWidth=(int)((double)newWidth*((double)MAX_OPENGL_WIDTH_HEIGHT/(double)newHeight));
			newHeight=MAX_OPENGL_WIDTH_HEIGHT;
		} else if (newWidth>newHeight && newWidth>MAX_OPENGL_WIDTH_HEIGHT) {
			newHeight=(int)((double)newHeight*((double)MAX_OPENGL_WIDTH_HEIGHT/(double)newWidth));
			newWidth=MAX_OPENGL_WIDTH_HEIGHT;
		}
		
		float xScale = (float) newWidth / sourceWidth;
		float yScale = (float) newHeight / sourceHeight;
		float scale = Math.max(xScale, yScale);

		float scaledWidth = scale * sourceWidth;
		float scaledHeight = scale * sourceHeight;

		float left = (newWidth - scaledWidth) / 2;
		float top = (newHeight - scaledHeight) / 2;

		RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
		Config config = source.getConfig();
		if (config==null) config=Bitmap.Config.ARGB_8888;
		
		Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, config);
		Canvas canvas = new Canvas(dest);
		canvas.drawBitmap(source, null, targetRect, null);

		return dest;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, boolean square) {
		int width = 0;
		int height = 0;
		if (square) {
			if (bitmap.getWidth() < bitmap.getHeight()) {
				width = bitmap.getWidth();
				height = bitmap.getWidth();
			} else {
				width = bitmap.getHeight();
				height = bitmap.getHeight();
			}
		} else {
			height = bitmap.getHeight();
			width = bitmap.getWidth();
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);
		final float roundPx = 90;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	@SuppressWarnings("deprecation")
	public static Bitmap getScreenSizedBitmap(Bitmap bm, Display display) {
		int windowHeight=display.getHeight();
		int windowWidth=display.getWidth();
		int bmHeight=bm.getHeight();
		int bmWidth=bm.getWidth();
		Bitmap newBm=bm;
		if ((bmHeight/windowHeight)>0.3) {
			newBm=Bitmap.createScaledBitmap(newBm, ((int)(windowWidth*0.4)), (bmHeight), false);
		}
		if ((bmWidth/windowWidth)>0.5) {
			newBm=Bitmap.createScaledBitmap(newBm, newBm.getWidth(), ((int)(windowHeight*0.3)), false);
		}
		return newBm;
	}
}
