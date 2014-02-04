package com.android.wallpaper.Util;

import java.io.IOException;
import java.io.InputStream;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.wallpaper.loadgif.GifDecoderView;
import com.android.wallpaperdemo.R;

public class Session {

	public static DBAdapter db;

	public static void initialiseApplication(Context context) {

		if (Session.db == null)
			Session.db = new DBAdapter(context);
	}
	
	public static class TransparentProgressDialog extends Dialog {

		public TransparentProgressDialog(Context context) {
			super(context, R.style.TransparentProgressDialog);
			WindowManager.LayoutParams wlmp = getWindow().getAttributes();
			wlmp.gravity = Gravity.CENTER_HORIZONTAL;
			getWindow().setAttributes(wlmp);
			setTitle(null);
			setCancelable(false);
			setOnCancelListener(null);
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);

			InputStream stream = null;
			try {
				stream = context.getAssets().open("spinner.gif");
			} catch (IOException e) {
				e.printStackTrace();
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			// GifMovieView view= new GifMovieView(context, stream);
			GifDecoderView view = new GifDecoderView(context, stream);

			layout.addView(view, params);
			addContentView(layout, params);
		}
	}


}
