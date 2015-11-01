package com.example.neev;
import com.example.neev.view.*;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import android.widget.Toast;


public class SupplierInfo extends Activity {
	Button b1;
	EditText n,p,address;
	String nm="",ph="",ad="";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_info);
		
			b1 = (Button)findViewById(R.id.sbutton1);
			n = (EditText)findViewById(R.id.seditText1);
			p=(EditText)findViewById(R.id.seditText2);
			address=(EditText)findViewById(R.id.seditText3);
			
			/*Adding checks to ensure the fields are not empty or in invalid format
			 *check if ph no. entered consists of digits only*/
			
			b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
					
					nm = n.getText().toString();
					ph=p.getText().toString();
					ad=address.getText().toString();
					try{
					if(nm.equals("")||ph.equals("")||ad.equals(""))
						Toast.makeText(SupplierInfo.this, "Please enter a value in all fields",2000).show();
					else if(Long.parseLong(ph)<=0)
					{
						Toast.makeText(SupplierInfo.this, "Please enter a valid Phone number",2000).show();
					}
					else
					{
						new myTask().execute();
						Intent i = new Intent(getApplicationContext(),UpdateRawMaterial.class);
						i.putExtra("s_ph_no",ph);
						startActivity(i);
					}
					}catch(java.lang.NumberFormatException e)
					{
						Toast.makeText(SupplierInfo.this, "Please Enter the value in a valid format",2000).show();
					}catch(Exception e)
					{
						Toast.makeText(SupplierInfo.this, "Sorry! An unknown error occured!",2000).show();
						}
			}
	});
}

	/* AsyncTask myTask
	 * onPreExecute : adding the info required by servlet to a list and setting the method as supplierInsert
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
			String method="supplierInsert";
			lst.add(new BasicNameValuePair("nm",nm));
			lst.add(new BasicNameValuePair("ph",ph));
			lst.add(new BasicNameValuePair("ad",ad));
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
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if(statusCode==409)
			{
				Toast.makeText(SupplierInfo.this, "Error accessing/updating a record. Check the values you have entered.",2000).show();
			}	
			else if(statusCode!=200)
			{
				Toast.makeText(SupplierInfo.this, "Error Connecting to network.",2000).show();
			}
			else
			{
				Toast.makeText(SupplierInfo.this, "Updation to database successful!",2000).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.supplier_info, menu);
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
