package com.example.neev;

import android.app.Activity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.os.Build;

public class PieFragment3 extends Fragment {
	LinearLayout chartContainer2;
	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (LinearLayout) inflater.inflate(R.layout.fragment_pie_fragment3,container, false);
		chartContainer2  =(LinearLayout) view.findViewById(R.id.chart_container);	


		Intent in = getActivity().getIntent();
		String[] code = in.getStringArrayExtra("code");
		//double[] distribution = in.getDoubleArrayExtra("distribution");
		double[] unit_price = in.getDoubleArrayExtra("unit_price");
		openChart(code, unit_price);
		
		return view;
	}

	private void openChart(String[] code, double[] unit_price) {       	
	    	
	    	// Pie Chart Section Names
	    	/*String[] code = new String[] {
	    			"Eclair & Older", "Froyo", "Gingerbread", "Honeycomb", "IceCream Sandwich", "Jelly Bean" 
	    	*/
		
	    	
	    	// Pie Chart Section Value
	    	//double[] distribution = { 3.9, 12.9, 55.8, 1.9, 23.7, 1.8 } ;
	    	
	    	// Color of each Pie Chart Sections    	
			int[] colors = { Color.rgb(255,255,153), Color.rgb(102, 153, 0), Color.rgb(131, 48, 89), Color.rgb(209, 209, 25), Color.rgb(204,204,204), Color.rgb(255,204,255) };
	    	// Instantiating CategorySeries to plot Pie Chart    	
	    	CategorySeries distributionSeries = new CategorySeries(" Prices ");
	    	
	    	for(int i=0 ;i < unit_price.length;i++) {
	    		// Adding a slice with its values and name to the Pie Chart
	    		distributionSeries.add(code[i] + "(" + unit_price[i]+")", unit_price[i]);  
	    	}   
	    	
	    	// Instantiating a renderer for the Pie Chart
	    	DefaultRenderer defaultRenderer  = new DefaultRenderer();  
	    	
	    	for(int i = 0 ;i<unit_price.length;i++) {        		
	    		SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();    	
	    		seriesRenderer.setColor(colors[i]);
	    		seriesRenderer.setDisplayChartValues(true);
	    		// Adding a renderer for a slice
	    		defaultRenderer.addSeriesRenderer(seriesRenderer);
	    	}
	    	
	    	defaultRenderer.setLabelsColor(Color.BLACK);
	    	defaultRenderer.setChartTitle(" Values ");
	    	defaultRenderer.setChartTitleTextSize(20);
	    	defaultRenderer.setZoomButtonsVisible(true);   
	    	
	    	GraphicalView mchart = ChartFactory.getPieChartView(getActivity().getBaseContext(), distributionSeries, defaultRenderer);

	        
	    	defaultRenderer.setClickEnabled(true);
	    	defaultRenderer.setSelectableBuffer(10);
	    	 /*mchart.setOnClickListener(new View.OnClickListener() {
	           @Override
	            public void onClick(View v) {
	            	//Toast.makeText(getApplicationContext(), "You Clicked... ", Toast.LENGTH_LONG).show();
	                //openChart2();
	            	Intent i = new Intent(getActivity().getApplicationContext()), PieChart3.class);
					startActivity(i);
	            }            
	         }); 
	        */
	        chartContainer2.addView(mchart);    	    	
	     }
	}

