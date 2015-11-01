package com.example.neev;
import com.example.neev.view.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FUpdate extends Activity implements OnItemSelectedListener {
	String iname="";
	String iprice="0", iqty="0";
	Button bupdate;
	EditText pr, qty;
	Spinner spinner;
	List categories;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fupdate);
		bupdate=(Button)findViewById(R.id.fubutton1);
		pr=(EditText)findViewById(R.id.fueditText1);
		qty=(EditText)findViewById(R.id.fueditText2);
		spinner = (Spinner) findViewById(R.id.fuspinner1);
		categories = new ArrayList();
		categories.add("SelectItem");
		spinner.setOnItemSelectedListener(this);
				
		//for adding the Products in Spinner
        new myFetchTask().execute();
     // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
     // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        	
        /*Adding checks to ensure the fields are not empty or in invalid format
		 Number formatException when string cannot be parsed into double/int/long*/
		
        bupdate.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				try{
				iprice=pr.getText().toString();
				iqty=qty.getText().toString();
				
				if(iprice.equals("") && iqty.equals(""))
					Toast.makeText(FUpdate.this, "Please Enter a value in the field",2000).show();
				else if(iprice.equals(""))
					{
						if(Integer.parseInt(iqty)<=0 )
							Toast.makeText(FUpdate.this, "Please Enter a valid value in the field",2000).show();
						else;
					}
					
				else if(iqty.equals(""))
					{
						if(Double.parseDouble(iprice)<=0)
							Toast.makeText(FUpdate.this, "Please Enter a valid value in the field",2000).show();
						else;
					}
					
				else if(Double.parseDouble(iprice)<=0 || Integer.parseInt(iqty)<=0 )
					Toast.makeText(FUpdate.this, "Please Enter a valid value in the field",2000).show();
				
					
				if(iname!="SelectItem")
						new myTask().execute();
					else
						Toast.makeText(FUpdate.this, "Please select an Item",2000).show();
					
				
			}catch(java.lang.NumberFormatException e)
			{
				Toast.makeText(FUpdate.this, "Please Enter the value in a valid format"+e,2000).show();
			}catch(Exception e)
			{
				Toast.makeText(FUpdate.this, "Sorry! An unknown error occured!",2000).show();
			}	}
			});
        
	}
	
	/* AsyncTask myTask
	 * onPreExecute : adding the info required by servlet to a list and setting the method as FUpdate
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
			String method="FUpdate";
	     	lst.add(new BasicNameValuePair("method", method));
	     	lst.add(new BasicNameValuePair("iname", iname));
	     	//if(!iprice.equals(""))
	     	lst.add(new BasicNameValuePair("iprice", iprice));
	     	//if(!iprice.equals(""))
	     	lst.add(new BasicNameValuePair("iqty",iqty));
	     	
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
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode==409)
				Toast.makeText(FUpdate.this, "Error accessing/updating a record. Check the values you have entered.",2000).show();
			else if(statusCode!=200)
			{
				Toast.makeText(FUpdate.this, "Error Connecting to network.",2000).show();
			}
			else
			{
				Toast.makeText(FUpdate.this, "Item Updated",2000).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fupdate, menu);
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
			if(statusCode!=200)
			{
				Toast.makeText(FUpdate.this, "Error Connecting to network.",2000).show();
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
		Toast.makeText(FUpdate.this, "Items Fetched Into Array",2000).show();
		}
   }
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		iname=arg0.getSelectedItem().toString();
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}}