package com.himz.erc;

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
		
		TextView  txtThings =  (TextView) findViewById(R.id.txtSubmission);
		txtThings.setText(thing + " " + action + " " + adjective);
	    textView1 = (TextView) findViewById(R.id.textView1);
		
		if("I want to rest".toLowerCase().equals(txtThings.getText().toString().toLowerCase())) {
			textView1.setText("propose(john,dr,[X,current_state,suspended])");
		}
		if("I want to stop".toLowerCase().equals(txtThings.getText().toString().toLowerCase())) {
			textView1.setText("propose(john,dr,[X,current_state,terminated])");
		}
		
		if("my leg hurts".toLowerCase().equals(txtThings.getText().toString().toLowerCase())) {
			textView1.setText("inform(john,dr, [leg,state,pain])");
		}		
		
		if("john do leg_lifts".toLowerCase().equals(txtThings.getText().toString().toLowerCase())) {
			textView1.setText("propose(dr, john, [e1, exercise_type,leg_lifts])");
		}		
		
		
	    httpStuff = (TextView) findViewById(R.id.tvhttp);
	    Toast.makeText(SubmitConfirm.this, "Starting Server ....", Toast.LENGTH_SHORT).show();
	    
	    
	    AndroidServer server = new AndroidServer() ;
	    server.start();
	    
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
        returned = test.getInternetData("http://localhost:8080/");

    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        return returned;
  }      

  @Override
  protected void onPostExecute(String result) {    
     httpStuff.setText(result);       
  }
	
  
}
	
}






class AndroidServer extends NanoHTTPD {
	public AndroidServer() {
		super(8080);
	}
	
	

	@Override
	public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files) {
		System.out.println(method + " '" + uri + "' ");
		String msg = "<html><body><h1>Hello server</h1>\n";
		if (parms.get("username") == null)
			msg +=
			"<form action='?' method='get'>\n" +
					"  <p>Your name: <input type='text' name='username'></p>\n" +
					"</form>\n";
		else
			msg += "<p>Hello, " + parms.get("username") + "!</p>";

		msg += "</body></html>\n";

		return new NanoHTTPD.Response(msg);
	}

	public void start(){
		ServerRunner.run(AndroidServer.class);
		
	}

	//    public static void main(String[] args) {
	//        ServerRunner.run(AndroidServer.class);
	//    }
}


