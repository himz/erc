package com.himz.erc;


import java.util.List;
import java.util.Map;

import com.himz.erc.util.NanoHTTPD;
import com.himz.erc.util.ServerRunner;
import com.himz.erc.util.NanoHTTPD.Method;
import com.himz.erc.util.NanoHTTPD.Response;





import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.*;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity implements
OnItemSelectedListener{
	public static WordDbAdapter db;
    // Spinner element
    Spinner spinner;
    Spinner  spnSelect1 ;
    int flagTxtActionActive = 0;
	Spinner  spnSelect2 ;
	int flagTxtAdjectiveActive = 0;
	Spinner  spnSelect3 ;
	Spinner  spnSelect4 ;      
	TextView txtCurrentSelected;
	EditText editText1;
    // Add button
    Button btnAdd;
 
    // Input text
    EditText inputLabel;
    public static boolean  flag = true;
    
    int currentState=1;
    int group =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		// Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
		spnSelect1 =  (Spinner) findViewById(R.id.spinner1);
		spnSelect2 =  (Spinner) findViewById(R.id.spinner2);
		spnSelect3 =  (Spinner) findViewById(R.id.spinner3);
		spnSelect4 =  (Spinner) findViewById(R.id.spinner4);
		editText1 =  (EditText) findViewById(R.id.editText1);	 
		
		
		spnSelect2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int currentState = db.fetchCurrentStateValue(group, spnSelect2.getSelectedItem().toString());
				int test = db.fetchNextStateValue(currentState, group, spnSelect2.getSelectedItem().toString() );
				loadSpinnerData(test, group, spnSelect3);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			});

		spnSelect3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int currentState = db.fetchCurrentStateValue(group, spnSelect3.getSelectedItem().toString());
				int test = db.fetchNextStateValue(currentState, group, spnSelect3.getSelectedItem().toString() );
				if(loadSpinnerData(test, group, spnSelect4) == 0){
					editText1.setText(spnSelect1.getSelectedItem().toString()  + " " + spnSelect2.getSelectedItem().toString() + " " +  spnSelect3.getSelectedItem().toString());
					
					spnSelect4.setVisibility(4);
				} else {
					spnSelect4.setVisibility(0);
				}
				
			}

			
			
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
		
		
		

		spnSelect4.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				editText1.setText(spnSelect1.getSelectedItem().toString()  + " " + spnSelect2.getSelectedItem().toString() + " " +  spnSelect3.getSelectedItem().toString() + " " +  spnSelect4.getSelectedItem().toString());
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});

		spnSelect1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//Get the Intial selected member's group no. 
				
				//group = db.getInitialGroupNumber(txtThings.getSelectedItem().toString());
				group = spnSelect1.getSelectedItemPosition() + 1;
				int currentState = db.fetchCurrentStateValue(group, spnSelect1.getSelectedItem().toString());
				int test = db.fetchNextStateValue(currentState, group, spnSelect1.getSelectedItem().toString() );
				loadSpinnerData(test, group, spnSelect2);
				//txtCurrentSelected.setText(txtCurrentSelected.getText() + txtThings.getSelectedItem().toString());
				spnSelect2.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
		
		
        
        db = new WordDbAdapter(this);
        db.open();
		
		if(flag)
		{
			// label, state1, state2, textType, groupNumber
			db.createStateRow("I",1, 2, 2, 1);
			db.createStateRow("am",2, 3, 2, 1);
			db.createStateRow("will",2, 11, 2, 1);
			
			db.createStateRow("fine",3, 4, 2, 1);
			db.createStateRow("tired",3, 5, 2, 1);
			db.createStateRow("cold",3, 6, 2, 1);
			db.createStateRow("hot",3, 7, 2, 1);
			db.createStateRow("happy",3, 8, 2, 1);
			db.createStateRow("upset",3, 9, 2, 1);
			db.createStateRow("worried",3, 10, 2,1);
			db.createStateRow("do",11, 12, 2, 1);
			db.createStateRow("it",12, 13, 2, 1);
			
			db.createStateRow("my",1, 2, 2, 2);
			db.createStateRow("knee",2, 3, 2, 2);
			db.createStateRow("leg",2, 3, 2, 2);
			db.createStateRow("arm",2, 3, 2, 2);
			db.createStateRow("hurts",3, 6, 2, 2);	

			db.createStateRow("john",1, 2, 2, 3);
		    db.createStateRow("do",2, 3, 2, 3);
		    db.createStateRow("leg_lifts",3, 4, 2, 3);
		    
		    
		    
			flag = false;
	}


		// Loading spinner data from database
        loadSpinnerData(currentState, group,  spinner);
		
        
        loadSpinnerData(currentState, group, spnSelect1);
        
        
        Button btnSubmit =  (Button) findViewById(R.id.button1);
		
		
		
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			private Context context = getApplicationContext();

			public void onClick(View v) {
				//TextView  txtThings =  (TextView) findViewById(R.id.TextView01);
				Spinner  txtThings =  (Spinner) findViewById(R.id.spinner1);
				Spinner  txtActions =  (Spinner) findViewById(R.id.spinner2);
				Spinner  txtAdjectives =  (Spinner) findViewById(R.id.spinner3);
				
				
				Intent i = new Intent( MainActivity.this , SubmitConfirm.class);
				i.putExtra("things", txtThings.getSelectedItem().toString());
				i.putExtra("actions", txtActions.getSelectedItem().toString());
				i.putExtra("adjectives", txtAdjectives.getSelectedItem().toString());
				try{
					i.putExtra("NP", spnSelect4.getSelectedItem().toString());
					
	
				} catch (Exception e){
					i.putExtra("NP", "");
					
				}
				
				startActivity(i);


			}
		}); 
        
	}

	
	/**
     * Function to load the spinner data from SQLite database
     * */
    private int loadSpinnerData(int currentState, int group, Spinner spinner) {
        // database handler
        //WordDbAdapter db = new WordDbAdapter(getApplicationContext());
 
        // Spinner Drop down elements
        List<String> lables = db.getNextStateValue(currentState, group); 	//forward the current state
 
        
        //List<String> lables = db.getAllLabels(); 	//forward the current state
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        int test = dataAdapter.getCount();
        return test;
    }
 

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();
        
        
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
 
    }
 
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
 
    }
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
