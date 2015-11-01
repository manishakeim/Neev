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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomer extends Activity {

	Button b1;
	
	EditText cn, cp, ca;
	String cname="", cph="",cadd=""; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_customer);
		
		b1 = (Button)findViewById(R.id.cdbutton1);
		cn=(EditText)findViewById(R.id.cdeditText1);
		cp=(EditText)findViewById(R.id.cdeditText2);
		ca=(EditText)findViewById(R.id.cdeditText3);
		
		/*Adding checks to ensure the fields are not empty or in invalid format
		 *check if ph no. entered consists of digits only*/
			
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				cname=cn.getText().toString();
				cph=cp.getText().toString();
				cadd=ca.getText().toString();
				try
				{
					if(cname.equals("")||cph.equals("")||cadd.equals(""))
					{
						Toast.makeText(AddCustomer.this, "Please Enter a value in the field",2000).show();
					}
				
					else if(Long.parseLong(cph)<=0)
							Toast.makeText(AddCustomer.this, "Please Enter the Phone no in a valid format",2000).show();
					else
					{	
						new myTask().execute();
						Intent p = new Intent(getApplicationContext(),PlaceOrder.class);
						p.putExtra("cphone", cph.toString());
						startActivity(p);
					
					}
				}
				catch(java.lang.NumberFormatException e)
				{
					Toast.makeText(AddCustomer.this, "Please Enter the value in a valid format : "+e,2000).show();
				}
				catch(Exception e)
				{
					Toast.makeText(AddCustomer.this, "Sorry! An unknown error occured!",2000).show();
				}
				}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_customer, menu);
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
	 * onPreExecute : adding the info required by servlet to a list and setting the method as AddCustomer
	 * doInBackGround : establishing the connection with database and sending the list made in preExecute
	 * 					getting the response back
	 * onPostExecute : handling the response
	 * 					checking if the request was successful or not*/
	class myTask extends AsyncTask<Void, Void, Void>
	{
		String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		HttpResponse httpResponse;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			String method="AddCustomer";
	     	lst.add(new BasicNameValuePair("cname",cname));
			lst.add(new BasicNameValuePair("cph",cph));
			lst.add(new BasicNameValuePair("cadd",cadd));
			lst.add(new BasicNameValuePair("method", method));
		}
		

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try{
				DefaultHttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(lst)); //sending the list in encoded form
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
				Toast.makeText(AddCustomer.this, "Error accessing/updating a record. Check the values you have entered.",2000).show();
			}
			else if(statusCode!=200)
			{
				Toast.makeText(AddCustomer.this, "Error Connecting to network.",2000).show();
			}
			else
			{
			Toast.makeText(AddCustomer.this, "Customer Added",2000).show();
		    }
		}
	}

}

