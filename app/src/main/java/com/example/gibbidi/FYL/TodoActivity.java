package com.example.gibbidi.FYL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends Activity implements OnItemClickListener {

	private EditText mTaskName;
    private EditText mTaskLocation;
    private EditText mTaskCost;
	private ListView mListView;
	private TaskAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

        Parse.initialize(this,"frUKObOHmNWcG3Rj5qHMt2jrTBSE5Z4SReUyrsJT","Ov0UUlwlHlTD3Ly2rm0zLmnMrcHAY5lbR5BM4eHK");
		ParseAnalytics.trackAppOpened(getIntent());
		ParseObject.registerSubclass(Task.class);

		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}

		mAdapter = new TaskAdapter(this, new ArrayList<Task>());

		mTaskName = (EditText) findViewById(R.id.task_name);
        mTaskLocation = (EditText) findViewById(R.id.task_location);
        mTaskCost = (EditText) findViewById(R.id.task_cost);
		mListView = (ListView) findViewById(R.id.task_list);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

		updateData();
	}

	public void updateData(){
		ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.findInBackground(new FindCallback<Task>() {
			@Override
			public void done(List<Task> tasks, ParseException error) {
				if(tasks != null){
					mAdapter.clear();
					for (int i = 0; i < tasks.size(); i++) {
						mAdapter.add(tasks.get(i));
					}
				}
			}
		});
	}
	public void createTask(View v) {
		if (mTaskName.getText().length() > 0){
			Task t = new Task();

            //t.setACL(new ParseACL(ParseUser.getCurrentUser()));
            ParseACL acl = new ParseACL(ParseUser.getCurrentUser());
            acl.setPublicReadAccess(true);
            acl.setPublicWriteAccess(true);
            t.setACL(acl);
			t.setUser(ParseUser.getCurrentUser());
			t.setHotelName(mTaskName.getText().toString());
            t.setHotelPrice(mTaskCost.getText().toString());
            t.setHotelLocation(mTaskLocation.getText().toString());
            ArrayList<String> days = new ArrayList<String>();
            t.setMon(false);
            t.setTue(false);
            t.setWed(false);
            t.setThu(false);
            t.setFri(false);
            t.setSat(false);
            t.setSun(false);
			t.setCompleted(true);

			t.saveEventually();
			mAdapter.insert(t, 0);
			mTaskName.setText("");
            mTaskLocation.setText("");
            mTaskCost.setText("");
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout: 
			ParseUser.logOut();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true; 
		} 
		return false; 
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Task task = mAdapter.getItem(position);
		TextView taskDescription = (TextView) view.findViewById(R.id.task_name);

		/*task.setCompleted(!task.isCompleted());

		if(task.isCompleted()){
			taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
			taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
        */
		task.saveEventually();
	}

}
