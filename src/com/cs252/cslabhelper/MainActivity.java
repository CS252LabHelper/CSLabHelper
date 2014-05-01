package com.cs252.cslabhelper;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	public static Class classes[];
	public static String nameString;
	
	public static ClassDataSource datasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		datasource = new ClassDataSource(this);
		datasource.open();
		if(!datasource.exists()){
			datasource.insertAllIntoTable(datasource);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 protected void onResume() {
		    datasource.open();
		    super.onResume();
		}

	 @Override
	  protected void onPause() {
		 	datasource.close();
		    super.onPause();
		 }
	 
	 public void startList(View view) {
			Intent list = new Intent(this, MyListActivity.class);
			startActivity(list);
	 }
	 public void okayPushed(View view)
		{
			TextView nameHelper = (TextView)findViewById(R.id.nameTextBox);
			Spinner labHelper = (Spinner)findViewById(R.id.classSpinner);
			Spinner timeHelper =(Spinner)findViewById(R.id.timeSpinner);
			Spinner dayHelper = (Spinner)findViewById(R.id.daySpinner);
			nameString = nameHelper.getText().toString();
			String labString = labHelper.getSelectedItem().toString();
			String timeString = timeHelper.getSelectedItem().toString();
			String dayString = dayHelper.getSelectedItem().toString();
			int time = 0;
			char temp = 0;
			char real = 0;
			String x;
			temp = timeString.charAt(1);
			if(temp == ':')
			{
				real = timeString.charAt(0);
				time = Character.getNumericValue(real);
				temp = timeString.charAt(2);
				time *= 100;
				if(temp == '3')
					time += 30; 
			}
			else
			{
				x = timeString.substring(0,2);
				time = Integer.parseInt(x);
				temp = timeString.charAt(3);
				time*=100;
				if(temp == '3')
					time+=30;
			}
			if(timeString.endsWith("p.m.") && time < 1200)
			{
				time += 1200;
			}
			
			Log.d("Okay pushed:", "name is:" + nameString);
			Log.d("Okay pushed:", "class is:" + labString);
			Log.d("Okay pushed:", "day is:" + dayString);
			Log.d("Okay pushed:", "time is:" + timeString);
			Log.d("Okay pushed:", "time really is" + String.valueOf(time));
			classes = datasource.retrieveClasses(labString, dayString, time);
			int y = 0;
			Log.d("Okay pushed:", "There are " + classes.length + " labs");
			while(y < classes.length)
			{
				Log.d("Class #" + y, "Lab is: " + classes[y].lab);
				Log.d("Class #" + y, "Day is: " + classes[y].day);
				Log.d("Class #" + y, "Lab is: " + String.valueOf(classes[y].start_time));
				y++;
			}
			startList(view);
		}
		
		public void viewAllClicked(View view)
		{
			TextView nameHelper = (TextView)findViewById(R.id.nameTextBox);
			Spinner labHelper = (Spinner)findViewById(R.id.classSpinner);
			//Spinner timeHelper =(Spinner)findViewById(R.id.timeSpinner);
			Spinner dayHelper = (Spinner)findViewById(R.id.daySpinner);
			nameString = nameHelper.getText().toString();
			String labString = labHelper.getSelectedItem().toString();
			//String timeString = timeHelper.getSelectedItem().toString();
			String dayString = dayHelper.getSelectedItem().toString();
			int time = 0;
			classes = datasource.getAllClasses();
			startList(view);
		}
		
	 /*void testDatabase(View view){
		Class classes[] = datasource.getAllClasses();
		Class classes2[] = datasource.retrieveClasses("CS250", "Wednesday", 1630);
		Log.d("testDatabase", "Size of array: "+String.valueOf(classes.length));
		Log.d("testDatabase", "Retrieved CS250 on Wednesday at 1630: " + String.valueOf(classes2.length));
	}*/
}

