package com.example.neev;
import com.example.neev.view.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UpdateActivity extends Activity {
	Button b1, b2, b3, b4, b5, b6, b7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		
		/*LayoutInflater inflater = (LayoutInflater) this.getLayoutInflater();
		View rootView = inflater.inflate(R.layout.activity_update, null);	*/	
			
		
		b1 = (Button)findViewById(R.id.mmbutton1);	//Raw material
		b2 = (Button)findViewById(R.id.mmbutton2);   //Place order
		b3 = (Button)findViewById(R.id.mmbutton3);   //Furnished products
		b4 = (Button)findViewById(R.id.mmbutton4);   //In transit
		b5 = (Button)findViewById(R.id.mmbutton5);   //Delivery
		b6 = (Button)findViewById(R.id.mmbutton6);   //Customer details
		b7 = (Button)findViewById(R.id.mmbutton7);   //Supplier details
				
		b1.setOnClickListener(new View.OnClickListener() {
	          @Override
	          public void onClick(View view) {
	        	  Intent intent = new Intent(getApplicationContext(), UpdateRawMaterial.class);
	                intent.putExtra("s_ph_no", "update");  //this line
	                startActivity(intent);
	          }     
	         
	        });      
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(getApplicationContext(), PlaceOrder.class);
				i1.putExtra("cphone", "update");
				startActivity(i1);
			}
		});
		
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(getApplicationContext(), FurnishedMenu.class);
				startActivity(i1);
			}
		});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(getApplicationContext(), InTransit.class);
				startActivity(i1);
			}
		});
		
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(getApplicationContext(), Delivery.class);
				startActivity(i1);
			}
		});
		
		b6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(getApplicationContext(), Customer.class);
				startActivity(i1);
			}
		});
		
		b7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(getApplicationContext(), SupplierDisplay.class);
				startActivity(i1);
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update, menu);
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
