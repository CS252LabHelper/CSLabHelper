package com.cs252.cslabhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	  private static final String DATABASE_NAME = "game.db";
	  private static final int DATABASE_VERSION = 1;

	  public static final String TABLE_B146 = "b146";
	  public static final String TABLE_B148 = "b148";
	  public static final String TABLE_B156 = "b156";
	  public static final String TABLE_B158 = "b158";
	  
	  //Question Data Fields
	  public static final String CLASS_ID = "ID";
	  public static final String CLASS_NAME = "NAME";
	  public static final String CLASS_DAY = "DAY";
	  public static final String CLASS_START_TIME = "START_TIME";
	  public static final String CLASS_END_TIME = "END_TIME";
	  
	  public static final String B146_CREATE = "create table "
			  + TABLE_B146 + "(" + CLASS_ID
			  + " integer primary key autoincrement, " + CLASS_NAME
			  + " text not null, " + CLASS_DAY 
			  + " text not null, " + CLASS_START_TIME
			  + " integer, " + CLASS_END_TIME
			  + " integer);";
	  
	  public static final String B148_CREATE = "create table "
			  + TABLE_B148 + "(" + CLASS_ID
			  + " integer primary key autoincrement, " + CLASS_NAME
			  + " text not null, " + CLASS_DAY 
			  + " text not null, " + CLASS_START_TIME
			  + " integer, " + CLASS_END_TIME
			  + " integer);";
	  
	  public static final String B156_CREATE = "create table "
			  + TABLE_B156 + "(" + CLASS_ID
			  + " integer primary key autoincrement, " + CLASS_NAME
			  + " text not null, " + CLASS_DAY 
			  + " text not null, " + CLASS_START_TIME
			  + " integer, " + CLASS_END_TIME
			  + " integer);";
	  
	  public static final String B158_CREATE = "create table "
			  + TABLE_B158 + "(" + CLASS_ID
			  + " integer primary key autoincrement, " + CLASS_NAME
			  + " text not null, " + CLASS_DAY 
			  + " text not null, " + CLASS_START_TIME
			  + " integer, " + CLASS_END_TIME
			  + " integer);";
	 

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  database.execSQL(B146_CREATE);
		  database.execSQL(B148_CREATE);
		  database.execSQL(B156_CREATE);
		  database.execSQL(B158_CREATE);
		  Log.d("onCreate", "Database created");
	  }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_B146);
	    onCreate(db);
	  }
}