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
 
        final List<String[]> classList = new LinkedList<String[]>();
        classList.add(new String[] { "LWSN B146", "CS 180" });
        classList.add(new String[] { "LWSN B148", "CS 240" });
        classList.add(new String[] { "LWSN B158", "CS 252" });
        
        setListAdapter(new ArrayAdapter<String[]>(
            this,
            android.R.layout.simple_list_item_2,
            android.R.id.text1,
            classList) {
 
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
 
                // Must always return just a View.
                View view = super.getView(position, convertView, parent);
 
                // If you look at the android.R.layout.simple_list_item_2 source, you'll see
                // it's a TwoLineListItem with 2 TextViews - text1 and text2.
                //TwoLineListItem listItem = (TwoLineListItem) view;
                String[] entry = classList.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(entry[0]);
                text2.setText(entry[1]);
                return view;
            }
        });
    }
}

	
/*	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this,  item + " selected",  Toast.LENGTH_LONG).show();
	}
}*/
