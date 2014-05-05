package com.cs252.cslabhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

class Class {
	int id;
	String name;
	String day;
	int start_time;
	int end_time;
	String lab;
}


public class ClassDataSource {


 
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private  String[] classColumns = { MySQLiteHelper.CLASS_ID,
      MySQLiteHelper.CLASS_NAME , MySQLiteHelper.CLASS_DAY, MySQLiteHelper.CLASS_START_TIME, 
      MySQLiteHelper.CLASS_END_TIME, MySQLiteHelper.CLASS_LAB}; 

  public ClassDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  
  public Class createClass(Class c, String table_name){
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.CLASS_NAME, c.name);
    values.put(MySQLiteHelper.CLASS_DAY, c.day);
    values.put(MySQLiteHelper.CLASS_START_TIME, c.start_time);
    values.put(MySQLiteHelper.CLASS_END_TIME, c.end_time);
    values.put(MySQLiteHelper.CLASS_LAB, c.lab);
    
    long insertId = database.insert(table_name, null,
        values);
    Log.d("insertId", String.valueOf(insertId));
    Cursor cursor = database.query(table_name,
        classColumns, MySQLiteHelper.CLASS_ID + " = " + insertId, null,
        null, null, null);
    //Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_QUESTIONS + " ", null);
    cursor.moveToFirst();
    Class newClass = cursorToClass(cursor);
    cursor.close();
    Log.d("createQuestion", newClass.name);
    return newClass;
  }
  
  public Class[] viewClasses(String day, int time){
	  int i = 0;
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASSES,
		        classColumns, "DAY = '" + day + "' AND '" + time + "' BETWEEN START_TIME AND END_TIME", null, null, null, null);
	  
	  int size = cursor.getCount();
	  Class[] classes = new Class[size];
	  cursor.moveToFirst();
	    
	  while (!cursor.isAfterLast()) {
	    classes[i] = new Class();
	    classes[i] = cursorToClass(cursor);
	    cursor.moveToNext();
	    i++;
	  }
	  
	  cursor.close();
	  
	  return classes;
  }
  public Class[] retrieveClasses(String class_name, String day, int time){
	  String daySelect = "";
	  String timeSelect = "";
	  String extradash = "";
	  int i = 0;
	  
	  if(!day.contains("Any")){
		  daySelect = "' AND DAY = '" + day.toString();
	  }
	  if(time > 0){
		  timeSelect = "' AND '" + String.valueOf(time) + "' BETWEEN START_TIME AND END_TIME";
  	  }else{
  		  extradash = "'";
  	  }
	  
	  
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASSES,
		        classColumns, "NAME = '" + class_name + daySelect + timeSelect + extradash , null, null, null, null);
	  
	  int size = cursor.getCount();
	  Class[] classes = new Class[size];
	  cursor.moveToFirst();
	    
	  while (!cursor.isAfterLast()) {
	    classes[i] = new Class();
	    classes[i] = cursorToClass(cursor);
	    cursor.moveToNext();
	    i++;
	  }
	  // make sure to close the cursor
	  cursor.close();
	  
	  return classes;
 }
			
  public Class[] getAllClasses() {
	  
    int i = 0;
    Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASSES,
        classColumns, null, null, null, null, null);
    
    int size = cursor.getCount();
    Class[] classes = new Class[size];
    cursor.moveToFirst();
    
    while (!cursor.isAfterLast()) {
      classes[i] = new Class();
      classes[i] = cursorToClass(cursor);
      cursor.moveToNext();
      i++;
    }
    // make sure to close the cursor
    cursor.close();
    return classes;
  }
  
  private Class cursorToClass(Cursor cursor) {
	  //cursor.moveToFirst();
	    Class c = new Class();
	    c.id = cursor.getInt(0);
	    c.name = cursor.getString(1);
	    c.day = cursor.getString(2);
	    c.start_time = cursor.getInt(3);
	    c.end_time = cursor.getInt(4);
	    c.lab = cursor.getString(5);
	    return c;
}
  

public void insertAllIntoTable(ClassDataSource ds){
	int numClasses = 46;
    Class classes[] = new Class[numClasses];
    
    for(int i=0;i<classes.length;i++){
    	classes[i] = new Class();
    }
    
   
    //B148
    classes[0].name = "CS352";
    classes[0].day = "Monday";
    classes[0].start_time = 930;
    classes[0].end_time = 1120;
    classes[0].lab = "B148";
    
    classes[1].name = "CS448";
    classes[1].day = "Monday";
    classes[1].start_time = 1130;
    classes[1].end_time = 1320;
    classes[1].lab = "B148";
    
    classes[2].name = "CS180 OFFICE HOURS";
    classes[2].day = "Monday";
    classes[2].start_time = 1730;
    classes[2].end_time = 1930;
    classes[2].lab = "B148";
    
    classes[3].name = "CS448";
    classes[3].day = "Tuesday";
    classes[3].start_time = 930;
    classes[3].end_time = 1120;
    classes[3].lab = "B148";
    
    classes[4].name = "CS180";
    classes[4].day = "Tuesday";
    classes[4].start_time = 1130;
    classes[4].end_time = 1320;
    classes[4].lab = "B148";
    
    classes[5].name = "CS180";
    classes[5].day = "Tuesday";
    classes[5].start_time = 1330;
    classes[5].end_time = 1520;
    classes[5].lab = "B148";
    
    classes[6].name = "CS252";
    classes[6].day = "Tuesday";
    classes[6].start_time = 1530;
    classes[6].end_time = 1720;
    classes[6].lab = "B148";
    
    classes[7].name = "CS180";
    classes[7].day = "Wednesday";
    classes[7].start_time = 930;
    classes[7].end_time = 1120;
    classes[7].lab = "B148";
    
    classes[8].name = "CS252";
    classes[8].day = "Wednesday";
    classes[8].start_time = 1330;
    classes[8].end_time = 1520;
    classes[8].lab = "B148";
    
    classes[9].name = "CS240";
    classes[9].day = "Wednesday";
    classes[9].start_time = 1530;
    classes[9].end_time = 1720;
    classes[9].lab = "B148";
    
    classes[10].name = "CS352";
    classes[10].day = "Thursday";
    classes[10].start_time = 930;
    classes[10].end_time = 1120;
    classes[10].lab = "B148";
    
    classes[11].name = "CS180";
    classes[11].day = "Thursday";
    classes[11].start_time = 1130;
    classes[11].end_time = 1320;
    classes[11].lab = "B148";
    
    classes[12].name = "CS352";
    classes[12].day = "Thursday";
    classes[12].start_time = 1330;
    classes[12].end_time = 1520;
    classes[12].lab = "B148";
    
    classes[13].name = "CS180";
    classes[13].day = "Thursday";
    classes[13].start_time = 1530;
    classes[13].end_time = 1720;
    classes[13].lab = "B148";
    
    classes[14].name = "CS180";
    classes[14].day = "Friday";
    classes[14].start_time = 930;
    classes[14].end_time = 1120;
    classes[14].lab = "B148";
    
    classes[15].name = "CS240";
    classes[15].day = "Friday";
    classes[15].start_time = 1130;
    classes[15].end_time = 1320;
    classes[15].lab = "B148";
    
    classes[16].name = "CS422";
    classes[16].day = "Friday";
    classes[16].start_time = 1330;
    classes[16].end_time = 1520;
    classes[16].lab = "B148";
    
    classes[17].name = "CS180";
    classes[17].day = "Friday";
    classes[17].start_time = 1530;
    classes[17].end_time = 1720;
    classes[17].lab = "B148";
    
    //B146
    classes[18].name = "CS426";
    classes[18].day = "Monday";
    classes[18].start_time = 1530;
    classes[18].end_time = 1720;
    classes[18].lab = "B146";
    
    classes[19].name = "CS240";
    classes[19].day = "Tuesday";
    classes[19].start_time = 1130;
    classes[19].end_time = 1320;
    classes[19].lab = "B146";
    
    classes[20].name = "CS240";
    classes[20].day = "Tuesday";
    classes[20].start_time = 1330;
    classes[20].end_time = 1520;
    classes[20].lab = "B146";
    
    classes[21].name = "CS352";
    classes[21].day = "Tuesday";
    classes[21].start_time = 1530;
    classes[21].end_time = 1720;
    classes[21].lab = "B146";
    
    classes[22].name = "CS240";
    classes[22].day = "Wednesday";
    classes[22].start_time = 1130;
    classes[22].end_time = 1320;
    classes[22].lab = "B146";
    
    classes[23].name = "CS240";
    classes[23].day = "Wednesday";
    classes[23].start_time = 1530;
    classes[23].end_time = 1720;
    classes[23].lab = "B146";
    
    classes[24].name = "CS252";
    classes[24].day = "Thursday";
    classes[24].start_time = 1130;
    classes[24].end_time = 1320;
    classes[24].lab = "B146";
    
    classes[25].name = "CS240";
    classes[25].day = "Thursday";
    classes[25].start_time = 1330;
    classes[25].end_time = 1520;
    classes[25].lab = "B146";
    
    classes[26].name = "CS422";
    classes[26].day = "Thursday";
    classes[26].start_time = 1530;
    classes[26].end_time = 1720;
    classes[26].lab = "B146";
    
    classes[27].name = "CS448 PSO";
    classes[27].day = "Thursday";
    classes[27].start_time = 1740;
    classes[27].end_time = 1930;
    classes[27].lab = "B146";
    
    classes[28].name = "CS448";
    classes[28].day = "Friday";
    classes[28].start_time = 930;
    classes[28].end_time = 1120;
    classes[28].lab = "B146";
    
    classes[29].name = "CS240";
    classes[29].day = "Friday";
    classes[29].start_time = 1130;
    classes[29].end_time = 1320;
    classes[29].lab = "B146";
    
    classes[30].name = "CS252";
    classes[30].day = "Friday";
    classes[30].start_time = 1330;
    classes[30].end_time = 1520;
    classes[30].lab = "B146";
    
    classes[31].name = "CS252";
    classes[31].day = "Friday";
    classes[31].start_time = 1530;
    classes[31].end_time = 1720;
    classes[31].lab = "B146";
    
    //B158
    classes[32].name = "CS240";
    classes[32].day = "Monday";
    classes[32].start_time = 1600;
    classes[32].end_time = 1800;
    classes[32].lab = "B158";
    
    classes[33].name = "CS240";
    classes[33].day = "Tuesday";
    classes[33].start_time = 1330;
    classes[33].end_time = 1520;
    classes[33].lab = "B158";
    
    classes[34].name = "CS426";
    classes[34].day = "Wednesday";
    classes[34].start_time = 930;
    classes[34].end_time = 1120;
    classes[34].lab = "B158";
    
    classes[35].name = "CS252";
    classes[35].day = "Wednesday";
    classes[35].start_time = 1530;
    classes[35].end_time = 1720;
    classes[35].lab = "B158";
    
    classes[36].name = "CS426 PSO";
    classes[36].day = "Thursday";
    classes[36].start_time = 915;
    classes[36].end_time = 1115;
    classes[36].lab = "B158";
    
    classes[37].name = "CS180";
    classes[37].day = "Thursday";
    classes[37].start_time = 1130;
    classes[37].end_time = 1320;
    classes[37].lab = "B158";
    
    classes[38].name = "CS252";
    classes[38].day = "Thursday";
    classes[38].start_time = 1330;
    classes[38].end_time = 1520;
    classes[38].lab = "B158";
    
    classes[39].name = "CS240";
    classes[39].day = "Friday";
    classes[39].start_time = 930;
    classes[39].end_time = 1120;
    classes[39].lab = "B158";
    
    classes[40].name = "CS426";
    classes[40].day = "Friday";
    classes[40].start_time = 1130;
    classes[40].end_time = 1320;
    classes[40].lab = "B158";
    
    classes[41].name = "CS180";
    classes[41].day = "Friday";
    classes[41].start_time = 1330;
    classes[41].end_time = 1520;
    classes[41].lab = "B158";
    
    classes[42].name = "CS240";
    classes[42].day = "Friday";
    classes[42].start_time = 1530;
    classes[42].end_time = 1720;
    classes[42].lab = "B158";
    
    classes[43].name = "CS250";
    classes[43].day = "Tuesday";
    classes[43].start_time = 1530;
    classes[43].end_time = 1720;
    classes[43].lab = "B160";
    
    classes[44].name = "CS250";
    classes[44].day = "Wednesday";
    classes[44].start_time = 1530;
    classes[44].end_time = 1720;
    classes[44].lab = "B160";
    
    classes[45].name = "CS250";
    classes[45].day = "Friday";
    classes[45].start_time = 1530;
    classes[45].end_time = 1720;
    classes[45].lab = "B160";
    
    for(int i=0;i<classes.length;i++){
    	ds.createClass(classes[i], MySQLiteHelper.TABLE_CLASSES);
    }
   
}

public boolean exists(){
	Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_CLASSES + " ", null);
	if(cursor.moveToFirst()){
		return true;
	}
	return false;
}

}


