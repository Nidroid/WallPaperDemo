package com.android.wallpaper.Util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
	
	private static final String DATABASE_NAME = "WallPaperDemo.sqlite";
	private static final int DATABASE_VERSION = 1;
	private static final String IMAGEURLS_TABLE = "tbl_imageurls";
	private Context context;
	private SQLiteDatabase db;

	
	public DBAdapter(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
	}
	
	public void insertToDb(ArrayList<String> urls) {
		try {
			
			this.db.delete(IMAGEURLS_TABLE, null, null);
			
			ContentValues dataToInsert = new ContentValues();
			for(String url:urls)
			{
				dataToInsert.put("url", url);
				long id=db.insert(IMAGEURLS_TABLE, null, dataToInsert);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getUrl(int id)
	{
		String url="";
		
		Cursor cursor=this.db.rawQuery(" SELECT url FROM "+IMAGEURLS_TABLE+"  where _id='"+ id+" '", null);
						
		if(cursor!=null)
		{
			cursor.moveToFirst();
			url=cursor.getString(0);
		}
		return url;
	}
		
	public void close() {
		this.db.close();
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE IF NOT EXISTS " + IMAGEURLS_TABLE
					+ "(_id INTEGER PRIMARY KEY, url TEXT DEFAULT '')");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS todo");
			onCreate(db);
		}
	}
}
