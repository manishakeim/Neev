package com.example.neev;
import com.example.neev.FDelete.myFetchTask;
import com.example.neev.view.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class PlaceOrder extends FragmentActivity implements OnItemSelectedListener{
	
	static EditText Date;
	CheckBox cb1;
	Button order,another_order;
	EditText oid, phone,quantity;
	Spinner spinner;
	List categories = new ArrayList();
	String iname,o_id,date,c_phn,quan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_order);
		
		spinner = (Spinner) findViewById(R.id.pospinner1); 
		Date = (EditText)findViewById(R.id.poeditText2);
		cb1 = (CheckBox)findViewById(R.id.pocheckBox1);
		order = (Button)findViewById(R.id.pobutton1);
		another_order = (Button)findViewById(R.id.button1);
		oid = (EditText)findViewById(R.id.poeditText1);
		phone =(EditText)findViewById(R.id.poeditText3);
		quantity = (EditText)findViewById(R.id.poeditText4);
		categories.add("SelectItem");
		spinner.setOnItemSelectedListener(this);
		new myFetchTask().execute();	
		 Intent intent=this.getIntent();
			if(intent!=null)
			{
				
				c_phn=intent.getStringExtra("cphone");
				
				if(!(c_phn.equals("update")))
				{
					phone.setText(c_phn,TextView.BufferType.EDITABLE);
				}
			
			}
		
    
     // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
     // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);             
		        
        cb1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox)v).isChecked()) {
					Intent supply = new Intent(getApplicationContext(),AddCustomer.class);
					startActivity(supply);
				}
			}
		});
        
        order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 o_id = oid.getText().toString();
				c_phn = phone.getText().toString();
				date = Date.getText().toString(); 
				quan = quantity.getText().toString(); 
				new myTask().execute();
			}
		});
        
        another_order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Date.setText("");
				oid.setText("");
				quantity.setText("");
				spinner.setSelection(0);
			}
		});
   Date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTruitonDatePickerDialog(v);
			}
		});
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
				System.out.println("Exception in connection");
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
				Toast.makeText(PlaceOrder.this, "Error Connecting to network.",2000).show();
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
//		Toast.makeText(PlaceOrder.this, "Items Fetched",2000).show();
		}
		
	}
	class myTask extends AsyncTask<Void, Void, Void>
	{
		String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		HttpResponse httpResponse;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			String method="Place_Order";
	     	lst.add(new BasicNameValuePair("oid",o_id));
			lst.add(new BasicNameValuePair("cph",c_phn));
			lst.add(new BasicNameValuePair("date",date));
			lst.add(new BasicNameValuePair("i_name",iname));
			lst.add(new BasicNameValuePair("quan",quan));
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
			if(statusCode!=200)
			{
				Toast.makeText(PlaceOrder.this, "Error Connecting to network.",2000).show();
			}
			else
			{
			Toast.makeText(PlaceOrder.this, "order placed",2000).show();
		    }
		}
	}

	public void showTruitonDatePickerDialog(View v) {
		// TODO Auto-generated method stub
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	 public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	 
	 @Override
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
	 // Use the current date as the default date in the picker
	 final Calendar c = Calendar.getInstance();
	 int year = c.get(Calendar.YEAR);
	 int month = c.get(Calendar.MONTH);
	 int day = c.get(Calendar.DAY_OF_MONTH);
	 
	 // Create a new instance of DatePickerDialog and return it
	 return new DatePickerDialog(getActivity(), this, year, month, day);
	 }
	 
	 public void onDateSet(DatePicker view, int year, int month, int day) {
	 // Do something with the date chosen by the user
	 Date.setText(year + "-" + (month + 1) + "-" + day);
	 }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place_order, menu);
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
		// TODO Auto-generated method stub
		iname=arg0.getSelectedItem().toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
