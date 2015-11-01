package com.example.neev;

import java.io.IOException;
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

import com.example.neev.SupplierDisplay.myTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Customer extends Activity {
 EditText c_ph,add,u_phn;
 String cust_ph="",address="",u_phone="";
 Button b;
 CheckBox ua,up;
 boolean uac=false, upc=false;

 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		c_ph = (EditText)findViewById(R.id.ceditText2);
		add =(EditText)findViewById(R.id.ceditText1);
		u_phn =(EditText)findViewById(R.id.ceditText3);
		b = (Button)findViewById(R.id.cbutton1);
		ua=(CheckBox)findViewById(R.id.ccheckBox1);
		up=(CheckBox)findViewById(R.id.ccheckBox2);
		
		/*to update address*/
		ua.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox)v).isChecked())
				{
					uac=true;
				}
				else
				{
					uac=false;
				}
			}});
			
		/*To Update phone number*/
		up.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (((CheckBox)v).isChecked())
				{
					upc=true;
				}
				else
				{
					upc=false;
				}
			}});
		
		/*Adding checks to ensure the fields are not empty or in invalid format
		 *check if ph no. entered consists of digits only*/
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cust_ph = c_ph.getText().toString();
				address = add.getText().toString();
				u_phone = u_phn.getText().toString();
			try{
				if(cust_ph.equals("") || Long.parseLong(cust_ph)<=0)
					Toast.makeText(Customer.this, "Please enter a valid value in Contact Field",2000).show();
				else if(uac==false && upc==false)
			    	Toast.makeText(Customer.this, "Please Select the field you want to update",2000).show(); 
				else if(uac==true && address.equals(""))
					   Toast.makeText(Customer.this, "Please enter a value for address if you wish to update it.",2000).show();
				 else if(upc==true && (u_phone.equals("")|| Long.parseLong(u_phone)<=0))
					   Toast.makeText(Customer.this, "Please enter a valid value for Contact to be updated if you wish to update it.",2000).show();
				 else
					  new myTask().execute();
		
			}catch(java.lang.NumberFormatException e)
			{
				Toast.makeText(Customer.this, "Please Enter the value in a valid format",2000).show();
			}catch(Exception e)
			{
				Toast.makeText(Customer.this, "Sorry! An unknown error occured!",2000).show();
			}
			}
			});
	}
		
	/* AsyncTask myTask
	 * onPreExecute : adding the info required by servlet to a list and setting the method as customerinfo
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
				String method="customerinfo";
				lst.add(new BasicNameValuePair("c_ph",cust_ph));
				lst.add(new BasicNameValuePair("u_ph",u_phone));
				lst.add(new BasicNameValuePair("c_add",address));
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
			@Override
			protected void onProgressUpdate(Void... values) {
				// TODO Auto-generated method stub
			
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				int statusCode = httpResponse.getStatusLine().getStatusCode();;
				if(statusCode==409)
					{
					Toast.makeText(Customer.this, "Error accessing/updating a record. Check the values you have entered",2000).show();
					}
				else if(statusCode!=200)
				{
					Toast.makeText(Customer.this, "Error Connecting to Network!",2000).show();
				}
				else
				{
					Toast.makeText(Customer.this, "Updation Successful!",2000).show();
				}
			}
		}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customer, menu);
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
