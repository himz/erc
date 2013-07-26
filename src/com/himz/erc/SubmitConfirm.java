package com.himz.erc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import com.himz.erc.util.NanoHTTPD;
import com.himz.erc.util.ServerRunner;
import com.himz.erc.util.NanoHTTPD.Method;
import com.himz.erc.util.NanoHTTPD.Response;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitConfirm extends Activity {


TextView httpStuff;
TextView textView1;
private Context mCtx;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_confirm);
		
		
		String thing = getIntent().getExtras().getString("things");
		String action = getIntent().getExtras().getString("actions");
		String adjective = getIntent().getExtras().getString("adjectives");
		String NP = getIntent().getExtras().getString("NP");
		TextView  txtThings =  (TextView) findViewById(R.id.txtSubmission);
		txtThings.setText(thing + " " + action + " " + adjective + " " + NP);
	    textView1 = (TextView) findViewById(R.id.textView1);
		
		if("I want to rest".toLowerCase().trim().equals(txtThings.getText().toString().toLowerCase().trim())) {
			textView1.setText("propose(john,dr,[X,current_state,suspended])");
		}
		if("I want to stop".toLowerCase().trim().equals(txtThings.getText().toString().toLowerCase().trim())) {
			textView1.setText("propose(john,dr,[X,current_state,terminated])");
		}
		
		if("my leg hurts".toLowerCase().trim().equals(txtThings.getText().toString().toLowerCase().trim())) {
			textView1.setText("inform(john,dr, [leg,state,pain])");
		}		
		
		if("john do leg_lifts".toLowerCase().trim().equals(txtThings.getText().toString().toLowerCase().trim())) {
			textView1.setText("propose(dr, john, [e1, exercise_type,leg_lifts])");
		}		
		
		
		if("I will do it".toLowerCase().trim().equals(txtThings.getText().toString().toLowerCase().trim())) {
			textView1.setText("accept(john, dr, [e1, exercise_type,leg_lifts])");
		}		
		
		/* Stop the server code for the demo*/
	    httpStuff = (TextView) findViewById(R.id.tvhttp1);
//	    Toast.makeText(SubmitConfirm.this, "Starting Server ....", Toast.LENGTH_SHORT).show();
//	    
//	    
//	    AndroidServer server = new AndroidServer() ;
//	    server.start();
//	    
	    new LongOperation().execute("");

	   
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_confirm, menu);
		return true;
	}

	


	private class LongOperation extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {

			GetMethodEx test = new GetMethodEx();
			String returned = null;

			try {
				returned = test
						.getInternetData("http://209.129.244.24:5000/diga_sys?sentence=john%20do%20leg_lifts&spact=propose(dr,john,[e1,exercise_type,leg_lifts])");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returned;
		}

		@Override
		protected void onPostExecute(String result) {
			httpStuff.setText(interpretHtml(result));
		}
		
		

		/**
		 * Function to parse the html and print out the spat.
		 * @param s
		 * @return
		 */
		private String interpretHtml(String s){
		    String text = null;
		    
		    BufferedReader bufReader = new BufferedReader(new StringReader(s));
		    String line=null;
		    try {
				while( (line=bufReader.readLine()) != null )
				{
					if(line.startsWith("<div class=\"spact\">")){
				    	// Get the spact out of the html
				         text = line.substring(19, line.length() - 6);
				         break;
				    }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		    return text;
		}

	}

	

	
}


