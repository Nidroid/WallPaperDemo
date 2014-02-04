package com.android.wallpaper;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.android.wallpaper.Util.Session;
import com.android.wallpaper.Util.Session.TransparentProgressDialog;
import com.android.wallpaper.Util.StoreUsetData;
import com.android.wallpaper.server.WallPaperHttpGet;
import com.android.wallpaper.server.WallPaperRequest;
import com.android.wallpaper.server.WallPaperResponse;
import com.android.wallpaperdemo.R;

public class MainActivity extends Activity {

	private String tag = "wallpaper-MainActivity";
	private static int reqid = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent(this, WallPaperService.class));
		setReminderInSystem(reqid, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class WallPaperAsynTask extends AsyncTask<Integer, Void, String> {
		TransparentProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new TransparentProgressDialog(MainActivity.this);
			dialog.show();
		}

		@Override
		protected String doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			String result;
			int reqcode = params[0];
			WallPaperHttpGet httpget = new WallPaperHttpGet();
			WallPaperResponse response = new WallPaperResponse();
			WallPaperRequest request = new WallPaperRequest();
			request.requrl = StoreUsetData.GETURL;

			try {
				response = httpget.handleRequestData(reqcode, request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (response.isSuccess) {
				result = "SUCCESS";

				// DataBase Entry
				Session.db.insertToDb(response.urls);
			} else
				result = "Failure...error code " + response.errorcode;
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing())
				dialog.dismiss();
			Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void onClick(View v) {
		new WallPaperAsynTask().execute(StoreUsetData.IMAGEURLS_REQ);
	}

	public class ReminderReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			new WallPaperAsynTask().execute(StoreUsetData.IMAGEURLS_REQ);
		}

	}

	public void setReminderInSystem(int id, Context context) {

		Calendar calendar = Calendar.getInstance();
		// 10 AM everyday
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Intent alarmintent = new Intent(context, ReminderReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, id,
				alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
	}

}
