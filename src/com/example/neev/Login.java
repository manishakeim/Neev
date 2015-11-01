package com.example.neev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class Login extends Activity {
	 String uname="", upass="";
	Button blogin;
	 EditText um, pass;
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        blogin = (Button)findViewById(R.id.login);
        um=(EditText)findViewById(R.id.editText);
        pass=(EditText)findViewById(R.id.editText1);
        
        
        /*Adding checks to ensure the fields are not empty */   
        
        blogin.setOnClickListener(new View.OnClickListener() {

        	
          @Override
          public void onClick(View view) {
        	  uname=um.getText().toString();
        	  upass=pass.getText().toString();
        	  if(uname.equals("")||upass.equals(""))
        		  Toast.makeText(Login.this, "Please enter a value in the field",2000).show();
        	  else
        		  new myTask().execute();
          }     
         
        });      
    
    }
	 
	 /* AsyncTask myTask
		 * onPreExecute : adding the info required by servlet to a list and setting the method as Login
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
			String method="Login";
	     	lst.add(new BasicNameValuePair("method", method));
	     	lst.add(new BasicNameValuePair("uname", uname));
	     	lst.add(new BasicNameValuePair("upass", upass));
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
		@Override
		protected void onPostExecute(Void result) {
			JSONArray jarr=new JSONArray();
			JSONObject json=new JSONObject();
			StringBuilder sb=new StringBuilder();
			// TODO Auto-generated method stub
		try{
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode!=200)
			{
				Toast.makeText(Login.this, "Error Connecting to network.",2000).show();
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
			String status=json.getString("status");
		    
		    if(status.equals("success"))
			{
				Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
	            startActivity(intent);
			}
			else
			{
				Toast.makeText(Login.this, "Incorrect Id Password! Please try Again.",2000).show();	
			}
			
			}
		   }    catch(Exception e)
		    {
		    	e.printStackTrace();
		    	System.out.println("JSON Exception in mainActivity");
		   	}
	 	
		
			
		}
	}
}
