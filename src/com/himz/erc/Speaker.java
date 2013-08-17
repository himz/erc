package com.himz.erc;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.himz.erc.util.ErcUtility;

public class Speaker extends Activity {

	private Button btnSubmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speaker);
		/* If the app has speaker value set, open the main activity */
		if(!ErcUtility.speaker.equals("")){

			Intent i = new Intent( Speaker.this , MainActivity.class);
			
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(i);
		}
		
		btnSubmit =  (Button) findViewById(R.id.button1);
		
		
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			private Context context = getApplicationContext();

			public void onClick(View v) {
				/* Go back to the previous screen. Check and implement that over here */
				EditText editText1 = (EditText) findViewById(R.id.editText1);
				ErcUtility.speaker = editText1.getText().toString();
				
				
				
				Intent i = new Intent( Speaker.this , MainActivity.class);
				
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i);
			}
		}); 
        
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first

	    // CLose the activity, after the main activity is opened
	    finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.speaker, menu);
		return true;
	}

}
