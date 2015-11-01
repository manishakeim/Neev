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


import com.example.neev.view.*;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FNew extends Activity {
	EditText pn,price, qty;
	Button b;
	String pna="",pprice="",pqty="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fnew);
		b=(Button)findViewById(R.id.fnbutton1);
		pn=(EditText)findViewById(R.id.fneditText1);
		price=(EditText)findViewById(R.id.fneditText2);
		qty=(EditText)findViewById(R.id.fneditText3);
		
		/*Adding checks to ensure the fields are not empty or in invalid format
		 Number format Exception when a string cannot be parsed into double/Integer
		 */
		
		 b.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View view) {
				 try{
		          pna=pn.getText().toString();
		        	  pprice=price.getText().toString();
		        	  pqty=qty.getText().toString();
		        	  if(pna.equals("")||pprice.equals("")|| pqty.equals(""))
							Toast.makeText(FNew.this, "Please Enter a value in the field",2000).show();
		        	  else if(Double.parseDouble(pprice)<=0 || Integer.parseInt(pqty)<=0)
		        		  Toast.makeText(FNew.this, "Please Enter a value greater than zero",2000).show();
		        	  else
		        		  new myTask().execute();
				 }catch(java.lang.NumberFormatException e)
					{
						Toast.makeText(FNew.this, "Please Enter the value in a valid format",2000).show();
					}
					catch(Exception e)
					{
						Toast.makeText(FNew.this, "Sorry! An unknown error occured!",2000).show();
					}
					}
			 
	        });      
	}
	
	/* AsyncTask myTask
	 * onPreExecute : adding the info required by servlet to a list and setting the method as FNew
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
			String method="FNew";
	     	lst.add(new BasicNameValuePair("pname",pna));
			lst.add(new BasicNameValuePair("pprice",pprice));
			lst.add(new BasicNameValuePair("pqty",pqty));
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
			try{
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				
				if(statusCode==409)
					Toast.makeText(FNew.this, "Error accessing/updating a record. Check the values you have entered.",2000).show();
				else if(statusCode!=200)
				{
					Toast.makeText(FNew.this, "Error Connecting to network.",2000).show();
				}
				else
				{
					Toast.makeText(FNew.this, "Furnished Product Added",2000).show();
				}	
			}catch(Exception e)
			{
				Toast.makeText(FNew.this, "No Network Access",2000).show();
			}
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fnew, menu);
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
