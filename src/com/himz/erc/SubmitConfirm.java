package com.himz.erc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class SubmitConfirm extends Activity {


TextView httpStuff;
TextView textView1;
	
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
		
		
	    httpStuff = (TextView) findViewById(R.id.tvhttp);
	
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
        returned = test.getInternetData("www.google.com");

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
