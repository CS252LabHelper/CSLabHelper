package com.cs252.cslabhelper;

import java.util.LinkedList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListActivity extends ListActivity {
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        Class list[] = MainActivity.classes;
        
        final List<String[]> classList = new LinkedList<String[]>();
        for(int i = 0; i < list.length; i++){
        	String AmPm = " AM";
        	int time = list[i].start_time;
        	if(time > 1200){
        		AmPm = " PM";
        		time-=1200;
        	}
        	classList.add(new String[] {list[i].lab + ": " + list[i].name, list[i].day + " " + String.valueOf(time) + AmPm});
        }
        setListAdapter(new ArrayAdapter<String[]>(
            this,
            android.R.layout.simple_list_item_2,
            android.R.id.text1,
            classList) {
 
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
 
                View view = super.getView(position, convertView, parent);
 
                String[] entry = classList.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(entry[0]);
                text2.setText(entry[1]);
                return view;
            }
        });
    }

	
@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item[] = (String[]) getListAdapter().getItem(position);
		Toast.makeText(this,  MainActivity.nameString + " is going to " + item[0] + ": " + item[1] + " selected",  Toast.LENGTH_LONG).show();
	}
}
