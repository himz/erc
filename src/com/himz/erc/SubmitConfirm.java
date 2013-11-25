package com.himz.erc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.himz.erc.util.ErcUtility;
import com.himz.erc.util.NanoHTTPD;
import com.himz.erc.util.ServerRunner;
import com.himz.erc.util.NanoHTTPD.Method;
import com.himz.erc.util.NanoHTTPD.Response;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitConfirm extends Activity {


TextView httpStuff;
TextView textView1;
String  globalSentence;
private Context mCtx;
private Timer myTimer;
String globalSpact;

Button btnChat;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_confirm);
		setTitle(getString(R.string.app_name) + "    "+"Hi" +  " " + ErcUtility.speaker);
		myTimer = new Timer();
		/* Change the refresh frequency of the timer. */
		myTimer.schedule(new TimerTask() {			
			@Override
			public void run() {
				TimerMethod();
			}
			
		}, 0, 2000);
	    httpStuff = (TextView) findViewById(R.id.tvhttp1);	
		btnChat = (Button) findViewById(R.id.button1);
		btnChat.setOnClickListener(new View.OnClickListener() {

			private Context context = getApplicationContext();

			public void onClick(View v) {
				Intent i = new Intent(SubmitConfirm.this , MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
//				finish();
			}
		});  
  
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    //Refresh the intent
	    setIntent(intent);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if( "1".equals(getIntent().getExtras().getString("1")))
			return;
		
		
		// Add parameters for global values
	
		String thing = getIntent().getExtras().getString("things");
		String action = getIntent().getExtras().getString("actions");
		String adjective = getIntent().getExtras().getString("adjectives");
		String NP = getIntent().getExtras().getString("NP");
		String NP2 = getIntent().getExtras().getString("NP2");
		String NP3 = getIntent().getExtras().getString("NP3");
		//globalSentence =  (TextView) findViewById(R.id.txtSubmission);
		globalSentence = (thing + " " + action + " " + adjective + " " + NP+ " " + NP2+ " " + NP3).trim();
	    
	    /* Store the spact value here in globalSpact */
		
		globalSpact = getGlobalSpact(globalSentence);
		
					
		/* Stop the server code for the demo*/

	    new LongOperation().execute("");
  
	    

		
		
	}


	public String getGlobalSpact(String globalSentence){
		
		String globalSpact= "";
		if("I want to rest".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="propose(john,dr,[X,current_state,suspended])";
			
		}
		if("I want to stop".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="propose(john,dr,[X,current_state,terminated])";
		}
		
		if("my leg hurts".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="inform(john,dr, [leg,state,pain])";
		}		
		
		if("john do leg_lifts".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="propose(dr,john,[[e1,exercise_type,leg_lifting],[e1,agent,john]])";
		}		
		
		
		if("I will do leg_lifts".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="accept(john, dr, [[e1,exercise_type,leg_lifting],[e1,agent,john]])";
		}		
		
		if("john lie down".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="propose(dr,john, [[e_ld,type,lying_down],[e_ld,agent,john]])";
		}	
		
		if("I don't want to lie down".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="reject(john,dr,[[e_ld,type,lying_down],[e_ld,agent,john]])";
		}	
		
		if("I want to do leg_lifts".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="propose(john,dr, [[e_ll,exercise_type,leg_lifting],[e_ll,agent,john]])";
		}	
		if("OK do leg_lifts".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="accept(dr,john, [[e_ll,exercise_type,leg_lifting],[e_ll,agent,john]])";
		}
		if("I want to rest".toLowerCase().trim().equals(globalSentence.toLowerCase().trim())) {
			globalSpact ="propose(john, dr, [[e_rest, activity_type, resting],[e_rest, agent, john]] )";
		}	
		
		
		return globalSpact;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_confirm, menu);
		return true;
	}

/* Take care of the refresh timer function */
	
	private void TimerMethod()
	{
		//This method is called directly by the timer
		//and runs in the same thread as the timer.

		/*
		 * Change the value of a shared variable to a new string fetched from the server.
		 * If it is different from the previous values, then update the box
		 * */
		
		
		
		//We call the method that will work with the UI
		//through the runOnUiThread method.
		this.runOnUiThread(Timer_Tick);
	}
	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			
		//This method runs in the same thread as the UI.    	       
		
		//Do something to the UI thread here
			new LongOperation().execute("1");
	
		}
	};
	
	
	
	
	
	private class AndroidServer extends NanoHTTPD {
		public AndroidServer() {
			super(8080);
		}

		@Override
		public Response serve(String uri, Method method,
				Map<String, String> header, Map<String, String> parms,
				Map<String, String> files) {
			System.out.println(method + " '" + uri + "' ");
			String msg = "<html><body><h1>Hello server</h1>\n";
			if (parms.get("username") == null)
				msg += "<form action='?' method='get'>\n"
						+ "  <p>Your name: <input type='text' name='username'></p>\n"
						+ "</form>\n";
			else
				msg += "<p>Hello, " + parms.get("username") + "!</p>";

			msg += "</body></html>\n";

			return new NanoHTTPD.Response(msg);
		}

		public void start() {
			//ServerRunner.run(AndroidServer.class);
			//ServerRunner.executeInstance(new AndroidServer());

		}

		// public static void main(String[] args) {
		// ServerRunner.run(AndroidServer.class);
		// }
	}


	


	private class LongOperation extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {

			GetMethodEx test = new GetMethodEx();
			String returned = null;
			String serverURL = "http://209.129.244.24:5000/diga_sys";
			String sentence = "";		// Change it to dynamically getting from the text box
			String spact = "propose(dr,john,[e1,exercise_type,leg_lifts])";	 // Change it to get dynamically
			String url= "";
			
			if("1".equals(params[0])){
				/* Its refresh request */
				serverURL = "http://209.129.244.24:5000/diga_refresh";
				url = serverURL;
				
			} else {
				sentence = globalSentence;   // Get the sentence selected by the user.
				spact = getSpact();
				url = createGetURL(serverURL, sentence, spact);
				
			}
			
			
			 
			try {
				//returned = test
					//	.getInternetData("http://209.129.244.24:5000/diga_sys?sentence=john%20do%20leg_lifts&spact=propose(dr,john,[e1,exercise_type,leg_lifts])");
				returned = test.getInternetData(url);
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
		
		private String createGetURL(String serverURL, String sentence, String spact){
			String url = "";
			
			//http://stackoverflow.com/questions/573184/java-convert-string-to-valid-uri-object
			//String requestURL = String.format("http://www.example.com/?a=%s&b=%s", Uri.encode("foo bar"), Uri.encode("100% fubar'd"));
			
			url = String.format(serverURL + "?sentence=%s&spact=%s", Uri.encode(sentence), Uri.encode(spact));
			return url;
		}
		
		private String getSpact(){
			return globalSpact;
		}
		
		/**
		 * Get the speaker from the context
		 * @param spact
		 * @return
		 */
	   private String getSpeaker(String spact){
		   String speaker =  null ;
		   String temp;
		   String[] r = spact.split(",");
		   temp = r[1];
		   speaker = temp.substring(temp.indexOf('(') + 1, temp.length());
		   		   
		   return speaker;
	   }
		

		/**
		 * Function to parse the html and print out the spat.
		 * @param s
		 * @return
		 */
		private String interpretHtml(String s){
		    String text = "";
		    String sentence = "";
		    String speaker = "";
		    String returnText= "";
		    String temp = "";
		    if(s == null || s.equals(""))
		    	return null;
		    BufferedReader bufReader = new BufferedReader(new StringReader(s));
		    String line=null;
		    try {
		    	while( (line=bufReader.readLine()) != null )
				{
					if(line.startsWith("<div class=\"sentence\">")){
				    	// Get the spact out of the html
				         temp = line.substring(22, line.length() - 6) + "\n";
				         continue; 
				    } else if(line.startsWith("<div class=\"spact\">")){
				    	// Get the spact out of the html
				    	 temp = getSpeaker(line) + " -> " + temp ;
				    	 text = text + temp;
				    	 temp = "";
				         continue;
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


