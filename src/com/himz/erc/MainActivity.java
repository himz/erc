package com.himz.erc;


import java.util.List;
import java.util.Map;

import com.himz.erc.util.ErcUtility;
import com.himz.erc.util.NanoHTTPD;
import com.himz.erc.util.ServerRunner;
import com.himz.erc.util.NanoHTTPD.Method;
import com.himz.erc.util.NanoHTTPD.Response;





import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
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
	Spinner  spnSelect5 ;
	Spinner  spnSelect6 ;  
	TextView txtCurrentSelected;
	EditText editText1;
    // Add button
    Button btnAdd;
    String chat;
 
    // Input text
    EditText inputLabel;
    public static boolean  flag = true;
    
    int currentState=1;
    int group =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		setTitle(getString(R.string.app_name) + "    "+"Hi" +  " " + ErcUtility.speaker);
		// Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
		spnSelect1 =  (Spinner) findViewById(R.id.spinner1);
		spnSelect2 =  (Spinner) findViewById(R.id.spinner2);
		spnSelect3 =  (Spinner) findViewById(R.id.spinner3);
		spnSelect4 =  (Spinner) findViewById(R.id.spinner4);
		spnSelect5 =  (Spinner) findViewById(R.id.spinner5);
		spnSelect6 =  (Spinner) findViewById(R.id.spinner6);
		editText1 =  (EditText) findViewById(R.id.editText1);	 
		chat = new String();
		
		spnSelect2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int currentState = (Integer)spnSelect2.getTag();
				//int currentState = db.fetchCurrentStateValue(group, spnSelect2.getSelectedItem().toString());
				int test = db.fetchNextStateValue(currentState, group, spnSelect2.getSelectedItem().toString() );
				loadSpinnerData(test, group, spnSelect3);
				/* Store the next state in the next spinner -- 
				 * which would be its current state 
				 */
				spnSelect3.setTag(test);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			});

		spnSelect3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int currentState = (Integer)spnSelect3.getTag(); 
						//db.fetchCurrentStateValue(group, spnSelect3.getSelectedItem().toString());
				int test = db.fetchNextStateValue(currentState, group, spnSelect3.getSelectedItem().toString() );
				chat = spnSelect1.getSelectedItem().toString()  + " " + spnSelect2.getSelectedItem().toString() + " " +  spnSelect3.getSelectedItem().toString();
				if(loadSpinnerData(test, group, spnSelect4) == 0){
//					editText1.setText(spnSelect1.getSelectedItem().toString()  + " " + spnSelect2.getSelectedItem().toString() + " " +  spnSelect3.getSelectedItem().toString());
					
					editText1.setText(chat);
					spnSelect4.setVisibility(4);
					spnSelect5.setVisibility(4);
					spnSelect6.setVisibility(4);
				
				} else {
					spnSelect4.setVisibility(0);
					/* Store the next state in the next spinner -- 
					 * which would be its current state 
					 */
					spnSelect4.setTag(test);
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
				int currentState =0;
				if(spnSelect4.getVisibility()==View.VISIBLE)
					currentState = (Integer)spnSelect4.getTag();
				else
					currentState = db.fetchCurrentStateValue(group, spnSelect4.getSelectedItem().toString());
				int test = db.fetchNextStateValue(currentState, group, spnSelect4.getSelectedItem().toString() );
				chat = chat + " " + spnSelect4.getSelectedItem().toString();
				if(loadSpinnerData(test, group, spnSelect5) == 0){
//					editText1.setText(spnSelect1.getSelectedItem().toString()  + " " + spnSelect2.getSelectedItem().toString() + " " +  spnSelect3.getSelectedItem().toString() + " " +  spnSelect4.getSelectedItem().toString());					
					editText1.setText(chat);
					spnSelect5.setVisibility(4);
					spnSelect6.setVisibility(4);
					
				} else {
					spnSelect5.setVisibility(0);
					/* Store the next state in the next spinner -- 
					 * which would be its current state 
					 */
					spnSelect5.setTag(test);
				}	
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
		spnSelect5.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int currentState =0;
				if(spnSelect5.getVisibility()==View.VISIBLE)
					currentState = (Integer)spnSelect5.getTag();
				else
					currentState = db.fetchCurrentStateValue(group, spnSelect5.getSelectedItem().toString());
				int test = db.fetchNextStateValue(currentState, group, spnSelect5.getSelectedItem().toString() );
				chat = chat + " " + spnSelect5.getSelectedItem().toString();
				if(loadSpinnerData(test, group, spnSelect6) == 0){
//					editText1.setText(spnSelect1.getSelectedItem().toString()  + " " + spnSelect2.getSelectedItem().toString() + " " +  spnSelect3.getSelectedItem().toString() + " " +  spnSelect4.getSelectedItem().toString()+ " " +  spnSelect5.getSelectedItem().toString());
				
					editText1.setText(chat);
					spnSelect6.setVisibility(4);
				
				} else {
					spnSelect6.setVisibility(0);
					/* Store the next state in the next spinner -- 
					 * which would be its current state 
					 */
					spnSelect6.setTag(test);
				}	
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
		spnSelect6.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				chat = chat + " " + spnSelect6.getSelectedItem().toString();
				editText1.setText(chat);
				//editText1.setText(spnSelect1.getSelectedItem().toString()  + " " + spnSelect2.getSelectedItem().toString() + " " +  spnSelect3.getSelectedItem().toString() + " " +  spnSelect4.getSelectedItem().toString()+ " " +  spnSelect5.getSelectedItem().toString() + " " +  spnSelect6.getSelectedItem().toString());
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
				/* Store the next state in the next spinner -- 
				 * which would be its current state 
				 */
				spnSelect2.setTag(test);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
		
		
        
        db = new WordDbAdapter(this);
        db.open();
		
	


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
				try{
					i.putExtra("NP2", spnSelect5.getSelectedItem().toString());	
				} catch (Exception e){
					i.putExtra("NP2", "");					
				}
				try{
					i.putExtra("NP3", spnSelect6.getSelectedItem().toString());	
				} catch (Exception e){
					i.putExtra("NP3", "");					
				}
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);


			}
		}); 
		
		
		Button btnSwitch =  (Button) findViewById(R.id.Button01);
		btnSwitch.setOnClickListener(new View.OnClickListener() {

			private Context context = getApplicationContext();

			public void onClick(View v) {
				
				Intent i = new Intent( MainActivity.this , SubmitConfirm.class);
				i.putExtra("switch", "1");
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
        
        /*Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();*/
 
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
