package com.example.neev;
import com.example.neev.TodayFragment.myTask;
import com.example.neev.view.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class CustomFragment extends Fragment {	
	
	 DatePicker picker1, picker2;  
 	 Button displayDate;  
 	 TextView textview1, textview2;  
     String toDate, fromDate;
    	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		
	        View rootview = inflater.inflate(R.layout.customfragment, container, false);     
	        // Inflate the layout for this fragment	       
	    	
	    		Intent in = getActivity().getIntent();
	    		 
	    	    
	    	 picker1=(DatePicker)rootview.findViewById(R.id.datePicker1);  
	   	     displayDate=(Button)rootview.findViewById(R.id.button1);  
	   	    
	   	    textview2=(TextView)rootview.findViewById(R.id.textView2);  
	   	    picker2=(DatePicker)rootview.findViewById(R.id.datePicker2);
	   	    
	   	    /*int m1 = picker1.getMonth()+1;
	   	    int d1 = picker1.getDayOfMonth();
	   	    int y1 = picker1.getYear();
	   	    */
	   	   
	   	   
	   	    StringBuilder builderfrom=new StringBuilder();   
	   	    //month is 0 based  
	   	   
	   	   	builderfrom.append(picker1.getYear()+"-");  
	   	    builderfrom.append((picker1.getMonth() + 1)+"-");
	   	    builderfrom.append(picker1.getDayOfMonth());  
	   	    fromDate = builderfrom.toString();  	
	   	 
	   	    
	   	    StringBuilder builderto=new StringBuilder();   
	   	    
	   	   	builderto.append(picker2.getYear()+"-");  
	   	    builderto.append(picker2.getDayOfMonth()+"-"); 
	   	    builderto.append((picker2.getMonth() + 1));//month is 0 based  
	   	     toDate = builderto.toString();  	
	   	    //SimpleDateFormat ddateto = new SimpleDateFormat("yyyy-MM-dd");
	   	     //toDate = ddateto.toString();
	    	    
	    	    displayDate.setOnClickListener(new View.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View arg0)
	    			{
	    				// TODO Auto-generated method stub
	    				
	    		
	    				Intent intent = new Intent(getActivity().getApplicationContext(), CustomListViewFragment.class);
	    				
	    				intent.putExtra("toDate",toDate );
	    				intent.putExtra("fromDate",fromDate);
	    				startActivity(intent);
	    				
	    			}
	    				});
				return rootview;
	    				   	  
	    	}
}
