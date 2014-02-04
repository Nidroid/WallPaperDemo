package com.android.wallpaper;

import android.app.Application;

import com.android.wallpaper.Util.Session;

public class WallPaperApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Session.initialiseApplication(getApplicationContext());

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
