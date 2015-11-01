package com.example.neev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  
        
        Button aboutus = (Button)findViewById(R.id.about);        
        aboutus.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
          }     
          
        });      
	
        Button ex = (Button)findViewById(R.id.export);  
        ex.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent i2 = new Intent(MainActivity.this, Export.class);
				startActivity(i2);
			}
		});
        
	 Button login = (Button)findViewById(R.id.login);	 
     login.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
         Intent intent = new Intent(MainActivity.this, DashboardActivity.class); //change it to Login.class
         startActivity(intent);
       }
     });  
}
	
}
