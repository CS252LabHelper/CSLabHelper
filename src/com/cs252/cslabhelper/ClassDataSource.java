package com.cs252.cslabhelper;
import java.util.ArrayList;
import java.util.List;

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
}


public class ClassDataSource {

  private Class classesB146[];
  private Class classesB148[];
  private Class classesB156[];
  private Class classesB158[];
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private  String[] allColumns = { MySQLiteHelper.CLASS_ID,
      MySQLiteHelper.CLASS_NAME , MySQLiteHelper.CLASS_DAY, MySQLiteHelper.CLASS_START_TIME, MySQLiteHelper.CLASS_END_TIME}; 

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
    
    long insertId = database.insert(table_name, null,
        values);
    Log.d("insertId", String.valueOf(insertId));
    Cursor cursor = database.query(table_name,
        allColumns, MySQLiteHelper.CLASS_ID + " = " + insertId, null,
        null, null, null);
    //Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_QUESTIONS + " ", null);
    cursor.moveToFirst();
    Class newClass = cursorToClass(cursor);
    cursor.close();
    Log.d("createQuestion", newClass.name);
    return newClass;
  }

  public Class retrieveClass(){
      Cursor cursor  = database.rawQuery("SELECT * FROM "+  MySQLiteHelper.TABLE_QUESTIONS + 
    		  " WHERE USED = 0 ORDER BY RANDOM()", null);
      cursor.moveToFirst();
      Question q = cursorToQuestion(cursor);
      //database.execSQL("UPDATE " + MySQLiteHelper.TABLE_QUESTIONS + " SET USED = 1 WHERE ID = " + q.id);            
      return q;       
 }
			
  public List<Question> getAllQuestions() {
    List<Question> questions = new ArrayList<Question>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_QUESTIONS,
        allColumns, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Question question = cursorToQuestion(cursor);
      questions.add(question);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return questions;
  }
  
  private Class cursorToClass(Cursor cursor) {
	  //cursor.moveToFirst();
	    Class c = new Class();
	    c.id = cursor.getInt(0);
	    c.name = cursor.getString(1);
	    c.day = cursor.getString(2);
	    c.start_time = cursor.getInt(3);
	    c.end_time = cursor.getInt(4);
	    return c;
}
  

public void insertAllIntoTable(ClassDataSource ds){
	int numClasses = 20;
    classesB146 = new Class[numClasses];
    classesB148 = new Class[numClasses];
    classesB156 = new Class[numClasses];
    classesB158 = new Class[numClasses];

    for(int i=0;i<classesB146.length;i++){
    	classesB146[i] = new Class();
    }
    
    for(int i=0;i<classesB148.length;i++){
    	classesB148[i] = new Class();
    }
    
    for(int i=0;i<classesB156.length;i++){
    	classesB156[i] = new Class();
    }
    
    for(int i=0;i<classesB158.length;i++){
    	classesB158[i] = new Class();
    }
    
    //B148
    classesB148[0].name = "CS352";
    classesB148[0].day = "Monday";
    classesB148[0].start_time = 930;
    classesB148[0].end_time = 1120;
    
    classesB148[1].name = "CS448";
    classesB148[1].day = "Monday";
    classesB148[1].start_time = 1130;
    classesB148[1].end_time = 1320;
    
    classesB148[2].name = "CS180 OFFICE HOURS";
    classesB148[2].day = "Monday";
    classesB148[2].start_time = 1730;
    classesB148[2].end_time = 1930;
    
    classesB148[3].name = "CS448";
    classesB148[3].day = "Tuesday";
    classesB148[3].start_time = 930;
    classesB148[3].end_time = 1120;
    
    classesB148[4].name = "CS180";
    classesB148[4].day = "Tuesday";
    classesB148[4].start_time = 1130;
    classesB148[4].end_time = 1320;
    
    classesB148[5].name = "CS180";
    classesB148[5].day = "Tuesday";
    classesB148[5].start_time = 1330;
    classesB148[5].end_time = 1520;
    
    classesB148[6].name = "CS252";
    classesB148[6].day = "Tuesday";
    classesB148[6].start_time = 1530;
    classesB148[6].end_time = 1720;
    
    classesB148[7].name = "CS180";
    classesB148[7].day = "Wednesday";
    classesB148[7].start_time = 930;
    classesB148[7].end_time = 1120;
    
    classesB148[8].name = "CS252";
    classesB148[8].day = "Wednesday";
    classesB148[8].start_time = 1330;
    classesB148[8].end_time = 1520;
    
    classesB148[9].name = "CS240";
    classesB148[9].day = "Wednesday";
    classesB148[9].start_time = 1530;
    classesB148[9].end_time = 1720;
    
    classesB148[10].name = "CS352";
    classesB148[10].day = "Thursday";
    classesB148[10].start_time = 930;
    classesB148[10].end_time = 1120;
    
    classesB148[11].name = "CS180";
    classesB148[11].day = "Thursday";
    classesB148[11].start_time = 1130;
    classesB148[11].end_time = 1320;
    
    classesB148[12].name = "CS352";
    classesB148[12].day = "Thursday";
    classesB148[12].start_time = 1330;
    classesB148[12].end_time = 1520;
    
    classesB148[13].name = "CS180";
    classesB148[13].day = "Thursday";
    classesB148[13].start_time = 1530;
    classesB148[13].end_time = 1720;

    classesB148[14].name = "CS180";
    classesB148[14].day = "Friday";
    classesB148[14].start_time = 930;
    classesB148[14].end_time = 1120;
    
    classesB148[15].name = "CS240";
    classesB148[15].day = "Friday";
    classesB148[15].start_time = 1130;
    classesB148[15].end_time = 1320;
    
    classesB148[16].name = "CS422";
    classesB148[16].day = "Friday";
    classesB148[16].start_time = 1330;
    classesB148[16].end_time = 1520;
    
    classesB148[17].name = "CS180";
    classesB148[17].day = "Friday";
    classesB148[17].start_time = 1530;
    classesB148[17].end_time = 1720;
    
    classesB146[0].name = "CS426";
    classesB146[0].day = "Monday";
    classesB146[0].start_time = 1530;
    classesB146[0].end_time = 1720;
    
    classesB146[1].name = "CS240";
    classesB146[1].day = "Tuesday";
    classesB146[1].start_time = 1130;
    classesB146[1].end_time = 1320;
    
    classesB146[2].name = "CS240";
    classesB146[2].day = "Tuesday";
    classesB146[2].start_time = 1330;
    classesB146[2].end_time = 1520;
    
    classesB146[3].name = "CS352";
    classesB146[3].day = "Tuesday";
    classesB146[3].start_time = 1530;
    classesB146[3].end_time = 1720;
    
    classesB146[4].name = "CS240";
    classesB146[4].day = "Wednesday";
    classesB146[4].start_time = 1130;
    classesB146[4].end_time = 1320;
    
    classesB146[5].name = "CS240";
    classesB146[5].day = "Wednesday";
    classesB146[5].start_time = 1530;
    classesB146[5].end_time = 1720;
    
    classesB146[6].name = "CS252";
    classesB146[6].day = "Thursday";
    classesB146[6].start_time = 1130;
    classesB146[6].end_time = 1320;
    
    classesB146[7].name = "CS240";
    classesB146[7].day = "Thursday";
    classesB146[7].start_time = 1330;
    classesB146[7].end_time = 1520;
    
    classesB146[8].name = "CS422";
    classesB146[8].day = "Thursday";
    classesB146[8].start_time = 1530;
    classesB146[8].end_time = 1720;
    
    classesB146[9].name = "CS448 PSO";
    classesB146[9].day = "Thursday";
    classesB146[9].start_time = 1740;
    classesB146[9].end_time = 1930;
    
    classesB146[10].name = "CS448";
    classesB146[10].day = "Friday";
    classesB146[10].start_time = 930;
    classesB146[10].end_time = 1120;
    
    classesB146[11].name = "CS240";
    classesB146[11].day = "Friday";
    classesB146[11].start_time = 1130;
    classesB146[11].end_time = 1320;
    
    classesB146[12].name = "CS252";
    classesB146[12].day = "Friday";
    classesB146[12].start_time = 1330;
    classesB146[12].end_time = 1520;
    
    classesB146[13].name = "CS252";
    classesB146[13].day = "Friday";
    classesB146[13].start_time = 1530;
    classesB146[13].end_time = 1720;
}

public boolean exists(){
	Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_B146 + " ", null);
	if(cursor.moveToFirst()){
		return true;
	}
	return false;
}

}


