package com.cs252.cslabhelper;

import java.util.LinkedList;
import java.util.List;

import edu.purdue.cs.cs180.channel.Channel;
import edu.purdue.cs.cs180.channel.ChannelException;
import edu.purdue.cs.cs180.channel.TCPChannel;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListActivity extends ListActivity {
	
	String host = "http://data.cs.purdue.edu";
	int port = 25000;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        Class list[] = MainActivity.classes;
        
        final List<String[]> classList = new LinkedList<String[]>();
        for(int i = 0; i < list.length; i++){
        	classList.add(new String[] {list[i].name + "-" + list[i].lab, list[i].day + "-" + String.valueOf(list[i].start_time)});
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

    public void sendToServer(String message){
    	Channel channel = null;
    	try {
    		channel = new TCPChannel(host,port);
		} catch (ChannelException e) {
			e.printStackTrace();
		}try {
			channel.sendMessage(message);
		} catch (ChannelException e) {
			e.printStackTrace();
		}
    }
	
@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item[] = (String[]) getListAdapter().getItem(position);
		String message = "/add-" + item[1] + "-" + MainActivity.nameString + "-" + item[0] + "-";
		new NetworkHandler().execute(message);
		Toast.makeText(this,  message,  Toast.LENGTH_LONG).show();
	}
}
