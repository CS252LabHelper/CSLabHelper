package com.cs252.cslabhelper;

import android.app.Activity;
import android.os.Bundle; 
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class ResultScreen extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		TextView tv = (TextView)findViewById(R.id.titleView);
		String newResult = NetworkHandler.response.toString();
		newResult = newResult.replaceAll("!", "\n");
		
		TextView tv1 = (TextView)findViewById(R.id.resultView);
		tv1.setText(newResult);
		tv1.setMovementMethod(new ScrollingMovementMethod());
	}
}
