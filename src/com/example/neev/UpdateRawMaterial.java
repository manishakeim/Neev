package com.example.neev;
import com.example.neev.view.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateRawMaterial extends FragmentActivity implements OnItemSelectedListener{
	
	CheckBox cb1;
	static EditText Date;
	Spinner spinner;
	Spinner spinner2;
	List categories;
	EditText rm_name,rm_quan,rm_price,supp_phno,deduct_quan;
	String name,rm_quantity,price,s_phno,date,item_name,deduct_quantity;
	Button b,deduct;
	int flag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_raw_material);
		
		Date = (EditText)findViewById(R.id.ureditText6);
		cb1 = (CheckBox)findViewById(R.id.urcheckBox1);
		spinner = (Spinner) findViewById(R.id.urspinner1); 
		spinner2 = (Spinner) findViewById(R.id.urspinner2);
		supp_phno = (EditText)findViewById(R.id.ureditText8);
        rm_name = (EditText)findViewById(R.id.ureditText1);
        rm_quan	=(EditText)findViewById(R.id.ureditText3);
        rm_price = (EditText)findViewById(R.id.ureditText4);
        deduct_quan = (EditText)findViewById(R.id.ureditText5);
        deduct = (Button)findViewById(R.id.urbutton2);
        b=(Button)findViewById(R.id.urbutton1);
        
       
		 // Spinner Drop down elements
		categories = new ArrayList();
		categories.add("SelectItem");
		
		//fetching the Raw Materials present in the
		new myFetchTask().execute();
		
		spinner.setOnItemSelectedListener(this);
        
		/*fetching the supplier phone number from Supplier_Info Screen 
		 * if supplier is a new supplier and he just made an entry*/
		 Intent intent=this.getIntent();
		if(intent!=null)
		{
			
			s_phno=intent.getStringExtra("s_ph_no");
			
			if(!(s_phno.equals("update")))
			{
				supp_phno.setText(s_phno,TextView.BufferType.EDITABLE);
			}
		
		}
	 
		// Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
     // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);
       
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
	
        /*Takes to SupplierInfo Activity if supplier is a new supplier*/
        cb1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox)v).isChecked()) {
					Intent supply = new Intent(getApplicationContext(),SupplierInfo.class);
					startActivity(supply);
				}
			}
		});
        
Date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTruitonDatePickerDialog(v);
			}
		});
		
        
		/*to Update raw material
		 * Adding checks to ensure the fields are not empty or in invalid format
		 *check if ph no. entered consists of digits only
		 *number format exception when string cannot be parsed into int/long/double*/
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				s_phno = supp_phno.getText().toString();
				name = rm_name.getText().toString();
				rm_quantity = rm_quan.getText().toString();
				price = rm_price.getText().toString();
				date = Date.getText().toString();
				try{
					if(s_phno.equals("")||name.equals("")||rm_quantity.equals("")||price.equals("")||date.equals(""))
						Toast.makeText(UpdateRawMaterial.this, "Please Enter a valid value in the field",2000).show();
					else if(Long.parseLong(s_phno)<=0 && Double.parseDouble(rm_quantity)<=0 && Double.parseDouble(price)<=0)
					{
						Toast.makeText(UpdateRawMaterial.this, "Please enter a valid value in the field.",2000).show();
					}
					else{		
						new myTask().execute();
						}
				}catch(java.lang.NumberFormatException e)
					{
						Toast.makeText(UpdateRawMaterial.this, "Please Enter the value in a valid format",2000).show();
					}catch(Exception e)
					{
						Toast.makeText(UpdateRawMaterial.this, "Sorry! An unknown error occured!",2000).show();
					}
				}
			}
		);
       
        
        /*to Deduct qty from raw materials*/
        deduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag =1;
				deduct_quantity = deduct_quan.getText().toString();
				if(deduct_quantity.equals("")|| Double.parseDouble(deduct_quantity)<=0)
					Toast.makeText(UpdateRawMaterial.this, "Please Enter a valid value in the field",2000).show();
				else
					new myTask().execute();
			}
		});
		        
}
	//datePicker
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
		 //setting Date as selected by user
		 public void onDateSet(DatePicker view, int year, int month, int day) {
		 // Do something with the date chosen by the user
		 Date.setText(year + "-" + (month + 1) + "-" + day);
		 }
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	/*Selecting item from spinner*/
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		// TODO Auto-generated method stub
		// On selecting a spinner item
        item_name = parent.getItemAtPosition(pos).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item_name, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	/* AsyncTask myTask : combined for deduct raw material and update raw material
	 * onPreExecute : adding the info required by servlet to a list and setting the method as deduct_raw_material
	 * or Update_raw_material 
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
			if(flag == 1)
			{
				
			flag =0;
			String method ="deduct_raw_material";
			lst.add(new BasicNameValuePair("method", method));
			lst.add(new BasicNameValuePair("iname", item_name));
			lst.add(new BasicNameValuePair("deduct_quan", deduct_quantity));
			}
			else
			{
			
				String method="Update_raw_material";
				lst.add(new BasicNameValuePair("method", method));
	     	
	     	
	     	if(item_name.equals("SelectItem"))
	     		lst.add(new BasicNameValuePair("iname", name));
	     	else
	     		lst.add(new BasicNameValuePair("iname", item_name));
	     	
	     	lst.add(new BasicNameValuePair("supplier_phno", s_phno));
	     	lst.add(new BasicNameValuePair("unit_price", price));
	     	lst.add(new BasicNameValuePair("rm_quantity", rm_quantity));
	     	lst.add(new BasicNameValuePair("date", date));
	     	}
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
			{
				Toast.makeText(UpdateRawMaterial.this, "Error accessing/updating a record. Check the values you have entered.",2000).show();
			}
			else if(statusCode!=200)
			{
				Toast.makeText(UpdateRawMaterial.this, "Error Connecting to network.",2000).show();
			}
			else
			{
				Toast.makeText(UpdateRawMaterial.this, "Successful!",2000).show();
			}
		}
	}
	
	/*To fetch the name of items
	 * onPreExecute : adding the info required by servlet to a list and setting the method as FetchRM
	 * doInBackGround : establishing the connection with database and sending the list made in preExecute
	 * 					getting the response back
	 * onPostExecute : handling the response
	 * 					checking if the request was successful or not
	 * */
	class myFetchTask extends AsyncTask<Void, Void, Void>
	{
		String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		
		HttpResponse httpResponse;
		protected void onPreExecute()
		{
			String method="FetchRM";
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
		
			int n=30;
			n=jarr.length();
		
			//categories.add("New Item");
			for(int i=0; i<n; i++)
			{
				json=jarr.getJSONObject(i);
				categories.add(json.getString("itemName"));
			}
						
		}catch(Exception e)
	    {
	    	e.printStackTrace();
	    	
	   	}
	//	Toast.makeText(UpdateRawMaterial.this, "Items Fetched Into Array",2000).show();
		}
   }
	

}
