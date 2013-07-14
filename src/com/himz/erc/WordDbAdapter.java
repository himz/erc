/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.himz.erc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class WordDbAdapter {



	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Database creation sql statement
	 */



	private Context mCtx;



	private static String stateTableCreate = "create table State (label text,  state1 integer,state2 integer, textType integer, grp integer)";
	// 6 states supported, with type as 1= question, 2 = inform. 

	private static final String TAG = "DBHelper";
	private static final String DATABASE_NAME = "ErcDb";
	private static final String DATABASE_TABLE1 = "State";

	private static final int DATABASE_VERSION = 2;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private     Context context;

		DatabaseHelper(Context context) {


			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			System.out.println("In DatabaseHelper constructor");

		}

		@Override
		public void onCreate(SQLiteDatabase db) {


			System.out.println("In onCreate()");

			db.execSQL(stateTableCreate);


			/* Also seed data for default values */
			seedData(db);
			
			/*Toast.makeText(context, "4 table created", Toast.LENGTH_LONG).show();*/

		}

		public void seedData(SQLiteDatabase db){
			// label, state1, state2, textType, groupNumber
			createStateRow(db, "I",1, 2, 2, 1);
			createStateRow(db, "am",2, 3, 2, 1);
			createStateRow(db, "will",2, 11, 2, 1);

			createStateRow(db, "fine",3, 4, 2, 1);
			createStateRow(db, "tired",3, 5, 2, 1);
			createStateRow(db, "cold",3, 6, 2, 1);
			createStateRow(db, "hot",3, 7, 2, 1);
			createStateRow(db, "happy",3, 8, 2, 1);
			createStateRow(db, "upset",3, 9, 2, 1);
			createStateRow(db, "worried",3, 10, 2,1);
			createStateRow(db, "do",11, 12, 2, 1);
			createStateRow(db, "it",12, 13, 2, 1);

			createStateRow(db, "my",1, 2, 2, 2);
			createStateRow(db, "knee",2, 3, 2, 2);
			createStateRow(db, "leg",2, 3, 2, 2);
			createStateRow(db, "arm",2, 3, 2, 2);
			createStateRow(db, "hurts",3, 6, 2, 2);	

			createStateRow(db, "john",1, 2, 2, 3);
			createStateRow(db, "do",2, 3, 2, 3);
			createStateRow(db, "leg_lifts",3, 4, 2, 3);
			
		}
		
		
		


		public long createStateRow(SQLiteDatabase db,String label,int state1, int state2, int textType, int grp)
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("label", label);
			initialValues.put("state1", state1);
			initialValues.put("state2", state2);
			initialValues.put("textType", textType);
			initialValues.put("grp", grp);
			System.out.println("HIMZ: creating values");
			return db.insert("State", null, initialValues);
		}

		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS TripIt");
			onCreate(db);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx the Context within which to work
	 */
	public WordDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * Open the notes database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException if the database could be neither opened or created
	 */
	public WordDbAdapter open() throws SQLException {

		System.out.println("In open()");

		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();

		return this;
	}
	/**
	 * @brief Close the adapter
	 */
	public void close() {
		mDbHelper.close();
	}




	public long createStateRow(String label,int state1, int state2, int textType, int grp)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put("label", label);
		initialValues.put("state1", state1);
		initialValues.put("state2", state2);
		initialValues.put("textType", textType);
		initialValues.put("grp", grp);
		System.out.println("HIMZ: creating values");
		return mDb.insert("State", null, initialValues);
	}


	public List<String>  getAllLabels()
	{
		System.out.println("Fetching all the trips");
		/*Toast.makeText(this.mCtx, "fetching the trips", Toast.LENGTH_LONG).show();*/
		Cursor c = mDb.query("State", null, null, null, null, null, null);
		List<String> labels = new ArrayList<String>();
		try{


			if (c.moveToFirst()) {
				do {
					System.out.println("HIMZ: inside the loop");   	
					labels.add(c.getString(1));
					System.out.println("HIMZ: c.getString(0)");
				} while (c.moveToNext());
			}

			// closing connection
			c.close();
		}
		catch(Exception e){
			System.out.println("asdf");
		}

		return labels;


	}

	public int getInitialGroupNumber(String label){
		Cursor c = null;
		int grpNo=0;
		List<String> labels = new ArrayList<String>();			
		c = mDb.rawQuery("select label from State where state1 = 1 and label = " + "\""+label+"\"", null);
		System.out.println("HIMZ: out of loop");
		grpNo = Integer.parseInt(c.getString(0));
		if(grpNo == 0)
			Toast.makeText(this.mCtx, "Cannot change the group", Toast.LENGTH_LONG).show();
		c.close();

		return grpNo ;


	}


	public int fetchNextStateValue(int state1, int group, String str)
	{
		Cursor c = null;

		try
		{
				c = mDb.rawQuery("select state2 from State where state1=" + "\""+state1+"\"" + "and grp=" + "\""+group+"\"" + "and label=" + "\""+str+"\"" , null);				
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		
			
		c.moveToFirst();
		
		return Integer.parseInt(c.getString(0)) ;
		
	}
	
	
	public int fetchCurrentStateValue(int group, String str)
	{
		Cursor c = null;

		try
		{
				c = mDb.rawQuery("select state1 from State where grp=" + "\""+group+"\"" + "and label=" + "\""+str+"\"" , null);				
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		
			
		c.moveToFirst();
		int returnValue = Integer.parseInt(c.getString(0)) ;
		c.close();
		return returnValue;
		
	}
	
	
	public List<String> getNextStateValue(int state1, int group)
	{
		String caption = null;
		Cursor c = null;
		List<String> labels = new ArrayList<String>();
		try
		{
			//c = mDb.rawQuery("select label from State"	, null);
			if( group == 0) {
				c = mDb.rawQuery("select label from State where state1=" + "\""+state1+"\"", null);
			} else {
				c = mDb.rawQuery("select label from State where state1=" + "\""+state1+"\"" + "and grp=" + "\""+group+"\"" , null);				
			}
			//c = mDb.rawQuery("select label from State where state1=" + "\""+state1+"\"", null);
			//Toast.makeText(this.mCtx, "Got the cursor", Toast.LENGTH_LONG).show();
			System.out.println("HIMZ: out of loop");
			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					System.out.println("HIMZ: inside the loop");   	
					labels.add(c.getString(0));
					System.out.println("HIMZ: c.getString(1)");
				} while (c.moveToNext());
			}

			// closing connection
			c.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return labels; 
	}




	public static ArrayList<String> convertStringToArray(String str)
	{
		List<String> items = new ArrayList<String>(Arrays.asList(str.split("\\s*,\\s*")));
		System.out.println("arraylist=" + items);
		return (ArrayList<String>) items;
	}



}