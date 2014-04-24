package com.cs252.cslabhelper;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
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
	 
	public void testDatabase(View view){
		Class classes[] = datasource.getAllClasses();
		Log.d("testDatabase", "Size of array: "+String.valueOf(classes.length));
	}
}

