package com.cs252.cslabhelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkHandler extends AsyncTask<String, Void, String>{
	
	public static StringBuffer response;
	@Override
	protected String doInBackground(String... params) {
		String message = params[0];
		String message1 = "/simple.html";
		String url1 = "http://sslab01.cs.purdue.edu:7777"+message;
		String url2 = "http://data.cs.purdue.edu:39001"+message;
		String url = "http://sac01.cs.purdue.edu:39001"+message;
		 Log.d("HTTP", url);
		URL obj = null;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
 
		// optional default is GET
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
 
		//add request header
		con.setRequestProperty("User-Agent", "CS252Lab");
 
		int responseCode = 0;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("HTTP", "\nSending 'GET' request to URL : " + url);
		Log.d("HTTP", "Response Code : " + responseCode);
 
		BufferedReader in = null;
		try {
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String inputLine;
		response = new StringBuffer();
 
		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		//print result
		Log.d("HTTP",response.toString());
		
		return null;
	}

}
