package com.example.neev;
import com.example.neev.FUpdate.myFetchTask;
import com.example.neev.FUpdate.myTask;
import com.example.neev.view.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class FDelete extends Activity implements OnItemSelectedListener {
	List categories; //= new ArrayList();
	Button bdelete;
	Spinner spinner;
	String iname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fdelete);
		bdelete=(Button)findViewById(R.id.fdbutton1);
		spinner = (Spinner) findViewById(R.id.fdspinner1); 
		categories = new ArrayList();
		categories.add("SelectItem");
		spinner.setOnItemSelectedListener(this);
		
		// fetching the list of the furnished products to be present in database
		new myFetchTask().execute();
		
		
     // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
     // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        
       
        spinner.setOnItemSelectedListener(this); 
           

         /*Making a selection and deleting the Product*/
        bdelete.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if(iname!="SelectItem")
					new myTask().execute();
				else
					Toast.makeText(FDelete.this, "Please select an Item",2000).show();
			}
               });

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fdelete, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/* AsyncTask myTask
	 * onPreExecute : adding the info required by servlet to a list and setting the method as FDelete
	 * doInBackGround : establishing the connection with database and sending the list made in preExecute
	 * 					getting the response back
	 * onPostExecute : handling the response
	 * 					checking if the request was successful or not*/
	
	class myTask extends AsyncTask<Void, Void, Void>
	{
		String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		
		HttpResponse httpResponse;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			String method="FDelete";
	     	lst.add(new BasicNameValuePair("method", method));
	     	lst.add(new BasicNameValuePair("iname", iname));
	     	}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				DefaultHttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(lst));
		    	httpResponse=httpClient.execute(httpPost);
			}catch(UnsupportedEncodingException e){
				
			System.out.println("\nAN EXCEPTION HAS OCCURED\n");
			e.printStackTrace();	
			}
			catch(ClientProtocolException e){
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
				
				return null;
		}
		
		protected void onPostExecute(Void result) {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode==409)
			{
				Toast.makeText(FDelete.this, "Error accessing/updating a record. Check the values you have entered",2000).show();
			}
			else if(statusCode!=200)
			{
				Toast.makeText(FDelete.this, "Error Connecting to network.",2000).show();
			}
			else
			{
				Toast.makeText(FDelete.this, "Item Deleted",2000).show();
			}
		}
	}
	class myFetchTask extends AsyncTask<Void, Void, Void>
	{
		String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		
		HttpResponse httpResponse;
		protected void onPreExecute()
		{
			String method="FetchItem";
	     	lst.add(new BasicNameValuePair("method", method));
		
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				DefaultHttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(lst));
		    	httpResponse=httpClient.execute(httpPost);
			}catch(Exception e)
			{
				System.out.println("Exception in connectin");
			}
				
		    		
			return null;
		}
		
		protected void onPostExecute(Void result) {
			JSONArray jarr=new JSONArray();
			JSONObject json=new JSONObject();
			StringBuilder sb=new StringBuilder();
			// TODO Auto-generated method stub
		try{
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode==409)
				Toast.makeText(FDelete.this, "Looks Like item you tre trying to delete doesn't exists!",2000).show();
			else if(statusCode!=200)
			{
				Toast.makeText(FDelete.this, "Error Connecting to network.",2000).show();
			}
			else
			{
			BufferedReader buf=new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
	 	
			String s="";
			while(true)
		 	{
				s=buf.readLine();
				if(s==null || s.length()==0)
				  break;
				sb.append(s);
			}
			buf.close();
		
			
			jarr=new JSONArray(sb.toString());
		
			int n=10;
			n=jarr.length();
			
			for(int i=0; i<n; i++)
			{
				json=jarr.getJSONObject(i);
				categories.add(json.getString("itemName"));
			}
			}
						
		}catch(Exception e)
	    {
	    	e.printStackTrace();
	    	System.out.println(" Exception in PostExecute");
	   	}
		Toast.makeText(FDelete.this, "Items Fetched",2000).show();
		}
   }/*
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}*/
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		iname=arg0.getSelectedItem().toString();
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
