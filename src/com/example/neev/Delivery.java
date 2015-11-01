package com.example.neev;
import com.example.neev.SupplierInfo.myTask;
import com.example.neev.view.*;

import java.io.IOException;
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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Delivery extends FragmentActivity {
	static EditText Date;
	EditText oid,d_quan;
	String o_id="",quantity="",date="";
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery);
		b = (Button)findViewById(R.id.dbutton1);
		Date = (EditText)findViewById(R.id.deditText3);
		oid=(EditText)findViewById(R.id.deditText1);
		d_quan=(EditText)findViewById(R.id.deditText2);
		
		/*Adding checks to ensure the fields are not empty or in invalid format
		 */
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			try{
				o_id = oid.getText().toString();
			
				quantity = d_quan.getText().toString();
				date = Date.getText().toString();
				if(o_id.equals("")|| quantity.equals("")||date.equals(""))
					Toast.makeText(Delivery.this, "Please Enter a value in the field",2000).show();
				else if(Integer.parseInt(o_id)<=0 || Double.parseDouble(quantity)<=0)
					Toast.makeText(Delivery.this, "Please Enter a valid Quantity",2000).show();
				else
					new myTask().execute();
				
			}catch(java.lang.NumberFormatException e)
			{
				Toast.makeText(Delivery.this, "Please Enter the value in a valid format",2000).show();
			}catch(Exception e)
			{
				Toast.makeText(Delivery.this, "Sorry! An unknown error occured!",2000).show();
			}
		}});
		Date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTruitonDatePickerDialog(v);
			}
		});
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
		// Date.setText(day + "/" + (month + 1) + "/" + year);
	 }
	}
	
	
	
	/* AsyncTask myTask
	 * onPreExecute : adding the info required by servlet to a list and setting the method as delivery_data
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
	
			String method="delivery_data";
			lst.add(new BasicNameValuePair("oid",o_id));
			lst.add(new BasicNameValuePair("quantity",quantity));
			lst.add(new BasicNameValuePair("date",date));
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

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode==409)
			{
				Toast.makeText(Delivery.this, "Error accessing/updating a record. Check the values you have entered",4000).show();
			}
			else if(statusCode!=200)
			{
				Toast.makeText(Delivery.this, "Error Connecting to network",2000).show();
			}
			else
			{
				Toast.makeText(Delivery.this, "Updation to database successful!",2000).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delivery, menu);
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
