package com.android.wallpaper;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.android.wallpaper.Util.Session;
import com.android.wallpaper.Util.StoreUsetData;


public class WallPaperService extends Service 
{
	
	MyCount counter=new MyCount(24*60*60*1000, 1*60*60*1000);
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		counter.start();
	}
	

   
	class MyCount extends CountDownTimer implements Runnable 
    {
    	public MyCount(long millisInFuture, long countDownInterval) 
    	{
    		super(millisInFuture, countDownInterval);
        }

        public void onFinish()
        {
        	ChangeWallPaper(24);
        }

        public void onTick(long millisUntilFinished) 
        {
        	int id = Math.round(millisUntilFinished/(60*60*1000));
        	ChangeWallPaper(id);
        	
        }

        public void run() 
        {
        	this.start();  
        }
    } 
	
	public void ChangeWallPaper(final int id) {
			
		final WallpaperManager WM = WallpaperManager.getInstance(getApplicationContext());
		new Thread(new Runnable() {
		
			@Override
			public void run() {
				try {
					
					DownloadFile(Session.db.getUrl(id));
					File ff=new File(StoreUsetData.FILEPATH);
					if(ff.exists())
					{
					FileInputStream is = new FileInputStream(ff);
					WM.setStream(is);
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	
	
	private void DownloadFile(String urlstr)
	{
		try {
	        URL url = new URL(urlstr);
	        URLConnection connection = url.openConnection();
	        connection.connect();
	        	        
	        InputStream input = new BufferedInputStream(url.openStream());
	        OutputStream output = new FileOutputStream(StoreUsetData.FILEPATH);

	        byte data[] = new byte[1024];
	        
	        int count;
	        while ((count = input.read(data)) != -1) {
	            output.write(data, 0, count);
	        }

	        output.flush();
	        output.close();
	        input.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	}	
}
