package com.example.neev;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CustomPieFragment3 extends Fragment {		
	LinearLayout chartContainer1;
	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (LinearLayout) inflater.inflate(R.layout.activity_custom_pie_fragment3,container, false);
		chartContainer1  =(LinearLayout) view.findViewById(R.id.chart_container);
			//int value=0;

		Intent in = getActivity().getIntent();

		String[] code =in.getStringArrayExtra("code");
		double[] distribution = in.getDoubleArrayExtra("distribution");
		double[] unit_price = in.getDoubleArrayExtra("unit_price");
		//int pos = distribution.length;
		//in.getIntExtra("pos",value);
		openChart2(code, distribution, unit_price);
		
		return view; 
	}
	
	private void openChart2(final String[] code, final double[] distribution, final double[] unit_price){    	
    	
    	// Pie Chart Section Names
    	/*String[] code = new String[] {
    			"Eclair & Older", "Froyo", "Gingerbread", "Honeycomb",
    			"IceCream Sandwich", "Jelly Bean" 
    	// Pie Chart Section Value
    	double[] distribution = { 13.9, 12.9, 55.8, 11.9, 23.7, 1.8 } ;*/
    	double[] percent = new double [distribution.length];
    	//percent.length = distribution.length;  
    	
    	double values_sum=0.0;
    	
    	for(double values : distribution)
    		values_sum = values + values_sum;
    	
    	int i= 0;
    	for(double datum : distribution)
    	{
    		percent[i] = Math.round(getpercent(datum,values_sum)*100.0)/100.0;
    		i++;
    	}
    	
    	// Color of each Pie Chart Sections
    	int[] colors = { Color.rgb(255,255,153), Color.rgb(102, 153, 0), Color.rgb(131, 48, 89), Color.rgb(209, 209, 25), Color.rgb(204,204,204), Color.rgb(255,204,255) };
    	
    	// Instantiating CategorySeries to plot Pie Chart    	
    	CategorySeries distributionSeries = new CategorySeries(" ");
    	
    	for(int j=0 ;j < distribution.length;j++){
    		// Adding a slice with its values and name to the Pie Chart
    		distributionSeries.add(code[j] + "(" + percent[j]+"%)", percent[j]);  
    	}   
    	
    	// Instantiating a renderer for the Pie Chart
    	DefaultRenderer defaultRenderer  = new DefaultRenderer();  
    	
    	for(int j = 0 ;j<distribution.length;j++)
    	{   
    		SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();    	
    		seriesRenderer.setColor(colors[j]);
    		seriesRenderer.setDisplayChartValues(true);
    		// Adding a renderer for a slice
    		defaultRenderer.addSeriesRenderer(seriesRenderer);
    	}
    	
    	
    	defaultRenderer.setLegendTextSize(18);
    	defaultRenderer.setFitLegend(true);
    	defaultRenderer.setLabelsColor(Color.BLACK);
    	defaultRenderer.setChartTitle("Unit Price");
    	defaultRenderer.setChartTitleTextSize(20);
    	defaultRenderer.setZoomButtonsVisible(true);    

    	// Getting PieChartView to add to the custom layout
    	GraphicalView chart = ChartFactory.getPieChartView(getActivity().getApplicationContext(), distributionSeries, defaultRenderer);
    	
    	defaultRenderer.setClickEnabled(true);
    	defaultRenderer.setSelectableBuffer(10);
        
    	chartContainer1.addView(chart);   	
    }
    
    double getpercent(double data, double values_sum)
	{
		double temp;
		temp = (data / values_sum)*100;
		return temp;
	}
}

