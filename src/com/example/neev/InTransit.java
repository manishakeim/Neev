package com.example.neev;
import com.example.neev.view.*;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InTransit extends FragmentActivity {
	Button b,d_name;
	EditText o_id,d_phn,prod_name;
    String order_id,d_phno, t_date,p_name;
    TextView driver_name;
	
	static EditText Date;
	
     protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_transit);
		
		o_id = (EditText)findViewById(R.id.teditText1);
		d_phn = (EditText)findViewById(R.id.teditText2);
		d_name = (Button)findViewById(R.id.button1);
		driver_name=(TextView)findViewById(R.id.textView1);
		b =(Button)findViewById(R.id.tbutton1);
		d_name.setClickable(false);
		
		/*fetching the name of the driver from his phone number*/
		
		d_name.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 d_phno= d_phn.getText().toString();
			 try{
			 if(Long.parseLong(d_phno)<=0)
			 {
				 Toast.makeText(InTransit.this, "Please Enter a valid value in the field",2000).show();
			 }
			 else
              new myFetchTask().execute();//to extract the name of the driver			
			 }catch(java.lang.NumberFormatException e)
				{
					Toast.makeText(InTransit.this, "Please Enter the value in a valid format : "+e,2000).show();
				}
				catch(Exception e)
				{
					Toast.makeText(InTransit.this, "Sorry! An unknown error occured!",2000).show();
				}
				
			 
			}
		});
		/*adding the checks on click of button
		 * to check if the value in the field is not empty or in an invalid format*/
		
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				order_id = o_id.getText().toString();
				d_phno= d_phn.getText().toString();
				p_name = prod_name.getText().toString();
				Date.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showTruitonDatePickerDialog(v);
					}
					
					/*Set date*/
				});
				try{	
				if(order_id.equals("")||d_phno.equals(""))
				{
					Toast.makeText(InTransit.this, "Please Enter a value in the field",2000).show();
				}
				else if(Integer.parseInt(order_id)<=0)
				{
					Toast.makeText(InTransit.this, "Please Enter a value greater than zero",2000).show();
				}
				else
				{
					new myTask().execute(); //InTransit
				}}catch(java.lang.NumberFormatException e)
				{
					Toast.makeText(InTransit.this, "Please Enter the value in a valid format : "+e,2000).show();
				}
				catch(Exception e)
				{
					Toast.makeText(InTransit.this, "Sorry! An unknown error occured!",2000).show();
				}
				
		}});}
     	
	

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
		/* AsyncTask myTask
		 * onPreExecute : adding the info required by servlet to a list and setting the method as Transit
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
				String method="Transit";
				lst.add(new BasicNameValuePair("dph",d_phno)); 
				lst.add(new BasicNameValuePair("oid",order_id));
				lst.add(new BasicNameValuePair("method", method));
				lst.add(new BasicNameValuePair("p_name", p_name));
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
			@Override
		
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if(statusCode==409)
				{
					Toast.makeText(InTransit.this, "Error accessing/updating the record. Check the values you have entered.",2000).show();
				}
				else if(statusCode!=200)
				{
					Toast.makeText(InTransit.this, "Error Connecting to network.",2000).show();
				}
				else
				{
				Toast.makeText(InTransit.this, "Transit Added!",2000).show();
				}
			}
		}
		/*fetching the name of the driver
		 *onPreExecute : adding the info required by servlet to a list and setting the method as Extract_D_Name 
		 * doInBackGround : establishing the connection with database and sending the list made in preExecute
		 * 					getting the response back
		 * onPostExecute : handling the response
		 * 					checking if the request was successful or not and updating the name of the driver in Dname EditText*/
			class myFetchTask extends AsyncTask<Void, Void, Void>
			{
				String url = "http://10.0.2.2:8080/webapp/reg";
				List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
				
				HttpResponse httpResponse;
				protected void onPreExecute()
				{
					String method="Extract_D_Name";
			     	lst.add(new BasicNameValuePair("method", method));
			     	lst.add(new BasicNameValuePair("dph",d_phno));
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
			if(statusCode==409)
			{
				Toast.makeText(InTransit.this, "Error accessing/updating record. Check the values you have entered",2000).show();
			}
			else if(statusCode!=200)
			{
				Toast.makeText(InTransit.this, "Error Connecting to network.",2000).show();
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
			json=jarr.getJSONObject(0);
			String dname=json.getString("D_Name");
			
			d_name.setText(dname);
		   
			}	
		   }    catch(Exception e)
		    {
		    	e.printStackTrace();
		    	System.out.println("JSON Exception in mainActivity");
		   	}
	 	}
		
		}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.in_transit, menu);
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
}

