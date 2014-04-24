package com.cs252.cslabhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	  private static final String DATABASE_NAME = "game.db";
	  private static final int DATABASE_VERSION = 1;
	  
	  public static final String TABLE_CLASSES = "classes";
	  
	  //Question Data Fields
	  public static final String CLASS_ID = "ID";
	  public static final String CLASS_NAME = "NAME";
	  public static final String CLASS_DAY = "DAY";
	  public static final String CLASS_START_TIME = "START_TIME";
	  public static final String CLASS_END_TIME = "END_TIME";
	  public static final String CLASS_LAB = "LAB";
	  
	  public static final String CLASSES_CREATE = "create table "
			  + TABLE_CLASSES + "(" + CLASS_ID
			  + " integer primary key autoincrement, " + CLASS_NAME
			  + " text not null, " + CLASS_DAY 
			  + " text not null, " + CLASS_START_TIME
			  + " integer, " + CLASS_END_TIME
			  + " integer, " + CLASS_LAB
			  + " text not null);";
	 

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  database.execSQL(CLASSES_CREATE);
		  Log.d("onCreate", "Database created");
	  }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
	    onCreate(db);
	  }
}