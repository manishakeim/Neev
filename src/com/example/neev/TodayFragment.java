package com.example.neev;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TodayFragment extends Fragment {
	String[] code;
	double[] distribution;
	double[] unit_price;
	int position;
	String var=null;
	
	// Array of strings storing country names
    String[] kpi = new String[] {
        "Inventory",
        "Sales",
        "In Transit",
        "Personnel",
        "Returned"       
    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
        R.drawable.d1,
        R.drawable.d2,
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d5       
    }; 

    // Array of strings to store currencies
    String[] price;/* = new String[]{
        "Rs. 5,00,000",
        "Rs. 5,00,000",
        "Rs. 5,00,000",
        "Rs. 10000000",
        "Rs. 5,00,000"      
    };*/
    
    String[] count;/*=  new String[]{
            "120",
            "122",
            "134",
            "143",
            "143"          
        };*/
    
	ListView listview;
	List<HashMap<String,String>> aList ;
	String[] from ;
	int[] to;
	SimpleAdapter adapter;
	View rootview ;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.todayfragment, container, false);     
        
        listview = (ListView) rootview.findViewById(R.id.listview);  
        
        /*use kpi queries before hashmap*/
        
        new myKPIPrice().execute();
        new myKPICost().execute();
        
         // Each row in the list stores country name, currency and flag
        aList = new ArrayList<HashMap<String,String>>();

        
        // Keys used in Hashmap
        from = new String[]{ "image","txt","cur", "pri" };

        // Ids of views in listview_layout
        to = new int[]{ R.id.image,R.id.txt,R.id.cur, R.id.pri};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
            
    return rootview; 
}

    class myKPICost extends AsyncTask<Void, Void,Void>
    {
    	String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		
		HttpResponse httpResponse;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			String method="myKPICount";
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
			count=new String[5];
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode==409)
				Toast.makeText(getActivity().getApplicationContext(), "You are trying to access non-existing data.",2000).show();
			else if(statusCode!=200)
			{
				Toast.makeText(getActivity().getApplicationContext(), "Error Connecting to network.",2000).show();
			}
			else
			{
			Toast.makeText(getActivity().getApplicationContext(), "Successful",2000).show();
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
			
			
			
			//	int n=jarr.length();
				//price = new String[5];
				
				/////////unit_price=new double[n];
			for(int i = 0; i<5;i++)
			{
				
				json=jarr.getJSONObject(i);
			//	price[i]=(json.getString("price")).toString();
				count[i]=""+json.getDouble("kpiCount");
			    //unit_price[i]=json.getDouble("iPrice");
				
			}
			
			for(int i=0;i<5;i++){
	            HashMap<String, String> hm = new HashMap<String,String>();            
	            hm.put("txt", "" + kpi[i]);
	            hm.put("cur","" + price[i]);
	            hm.put("pri","" + count[i]);
	            hm.put("image", Integer.toString(flags[i]) );
	            aList.add(hm);
	        }
			
			adapter = new SimpleAdapter(getActivity(), aList, R.layout.listview_layout, from, to);          
	        {     	
	        	 
	        	
	        // Setting the adapter to the listView
	        	listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	        	listview.setAdapter(adapter);     
	        
	        
	        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
	        {
	              @Override
	             public void onItemClick(AdapterView<?> arg0, View view, int position, long id) 
	              {

	            	  switch(position)
	            	  {
	            	  	case 0 : //update the value of code and distribution for each case
	            	  			//changes will get reflected in piechart1;
	            	  			//code will be our labels i.e raw material names and furnished products names
	            	  			// distribution corresponts to values
	            	  			{
	            	  				var="0";
	            	  			//new myTask().execute();	            	  			
	            //	  			 intentfunction(code,distribution);
	        
	            	  			}break;
	            	  			
	            	  	case 1 :{
	            	  		
	            	  		//intentfunction(code, distribution, unit_price);
	            	  		var="1";
				       		      }break;
			       		      
	            	  	case 2 :{
				       		      //intentfunction(code, distribution, unit_price);
				       		   var="2";
				       	          }break;
			       		     
	            	  	case 3 :{
				       		      // intentfunction(code, distribution, unit_price);
				       		    var="3";
				       	          }break;
			       		     
	            	  	case 4 :{
				   		         // intentfunction(code, distribution, unit_price);
				   		       var="4";
				   		         }break;		       		     
			       	  }	
	            	  new myTask().execute();
			      }
		  }); 
	        
	    }

  			
		}catch(Exception e)
		   {
				
				e.printStackTrace();
		    	System.out.println("JSON Exception in mainActivity"+e);
		   	}
			
			}
      }
   }


    
    class myKPIPrice extends AsyncTask<Void, Void,Void>
    {
    	String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		
		HttpResponse httpResponse;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			String method="myKPIPrice";
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
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode==409)
				Toast.makeText(getActivity().getApplicationContext(), "Looks like you are truing to access unexisting data.",2000).show();
			else if(statusCode!=200)
			{
				Toast.makeText(getActivity().getApplicationContext(), "Error Connecting to network.",2000).show();
			}
			else
			{
			Toast.makeText(getActivity().getApplicationContext(), "Successful",2000).show();
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
			
			
			
			//	int n=jarr.length();
				price = new String[5];
				//count = new String[5];
				/////////unit_price=new double[n];
			for(int i = 0; i<5;i++)
			{
				
				json=jarr.getJSONObject(i);
				price[i]=""+json.getDouble("kpiPrice");
				//count[i]=json.getString("count");
			    //unit_price[i]=json.getDouble("iPrice");
			}
  						
		} catch(Exception e)
	    {
			
			e.printStackTrace();
	    	System.out.println("JSON Exception in mainActivity"+e);
	   	}
			
			}
    }
		}
  
    
    
    class myTask extends AsyncTask<Void, Void, Void>
	{
		String url = "http://10.0.2.2:8080/webapp/reg";
		List<NameValuePair> lst = 	new ArrayList<NameValuePair>();
		
		HttpResponse httpResponse;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			String method="pieChart";
	     	lst.add(new BasicNameValuePair("method", method));
	     	lst.add(new BasicNameValuePair("var", var));
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
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode!=200)
			{
				Toast.makeText(getActivity().getApplicationContext(), "Error Connecting to network.",2000).show();
			}
			else
			{
			Toast.makeText(getActivity().getApplicationContext(), "Successful",2000).show();
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
			
			
			
				int n=jarr.length();
				code = new String[n];
				distribution  = new double[n];
				unit_price=new double[n];
			for(int i = 0; i<n;i++)
			{
				
				json=jarr.getJSONObject(i);
				code[i]=(json.getString("itemName")).toString();
				distribution[i]=json.getDouble("iQuantity");
			    unit_price[i]=json.getDouble("iPrice");
			}
  			
		}catch(Exception e)
		   {
				
				e.printStackTrace();
		    	System.out.println("JSON Exception in mainActivity"+e);
		   	}
			
			intentfunction(code,distribution,unit_price);
		}

			
			}
		}

    void intentfunction(String[] code, double[] distribution, double[] unit_price) {
		// TODO Auto-generated method stub
// becoz not displayed    	Toast.makeText(getActivity().getApplicationContext(), "Error Connecting to network.",2000).show();
    	Intent intent = new Intent(getActivity().getApplicationContext(), PieChart1.class);
		intent.putExtra("pos",position);
		intent.putExtra("code",code );
		intent.putExtra("distribution",distribution );
		intent.putExtra("unit_price",unit_price );
		startActivity(intent);
      } 
}

