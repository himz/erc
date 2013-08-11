package com.himz.erc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import com.himz.erc.util.NanoHTTPD;
import com.himz.erc.util.ServerRunner;
import com.himz.erc.util.NanoHTTPD.Method;
import com.himz.erc.util.NanoHTTPD.Response;

import android.net.Uri;
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
TextView  txtThings;
private Context mCtx;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_confirm);
		
		
		String thing = getIntent().getExtras().getString("things");
		String action = getIntent().getExtras().getString("actions");
		String adjective = getIntent().getExtras().getString("adjectives");
		String NP = getIntent().getExtras().getString("NP");
		txtThings =  (TextView) findViewById(R.id.txtSubmission);
		txtThings.setText((thing + " " + action + " " + adjective + " " + NP).trim());
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
	    new LongOperation().execute("");
	   // Toast.makeText(SubmitConfirm.this, "Starting Server ....", Toast.LENGTH_SHORT).show();
	    
	    
	   // AndroidServer server = new AndroidServer() ;
	    //server.start();
  
	  

	   
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_confirm, menu);
		return true;
	}

	
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
			String sentence = "john do leg_lifts";		// Change it to dynamically getting from the text box
			String spact = "propose(dr,john,[e1,exercise_type,leg_lifts])";	 // Change it to get dynamically
			String url= "";
			sentence = txtThings.getText().toString();   // Get the sentence selected by the user. 
			try {
				url = createGetURL(serverURL, sentence, spact);
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


