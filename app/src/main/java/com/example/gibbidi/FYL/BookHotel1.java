/**
 * Created by gibbidi on 30/4/15.
 */
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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class BookHotel1 extends Activity implements OnItemClickListener {

    private EditText mTaskName;
    private EditText mTaskLocation;
    private EditText mTaskday;
    private ListView mListView;
    private TaskAdapter mAdapter;
    //private ListAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hotel1);

        Parse.initialize(this, "frUKObOHmNWcG3Rj5qHMt2jrTBSE5Z4SReUyrsJT", "Ov0UUlwlHlTD3Ly2rm0zLmnMrcHAY5lbR5BM4eHK");
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
        mTaskday = (EditText) findViewById(R.id.task_day);
        mListView = (ListView) findViewById(R.id.task_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        //updateData();
    }

    public void updateData(){
        Toast.makeText(getApplicationContext(),"Entered upDate", Toast.LENGTH_LONG).show();
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        //query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("Completed", true);
        query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Task>() {
            @Override
            public void done(List<Task> tasks, ParseException error) {
                Toast.makeText(getApplicationContext(),"Entered done", Toast.LENGTH_LONG).show();
                if(tasks != null){
                    Toast.makeText(getApplicationContext(),"Tasks not null", Toast.LENGTH_LONG).show();
                    //mAdapter.clear();
                    //Toast.makeText(getApplicationContext(),"Tasks not null", Toast.LENGTH_LONG).show();
                    for (int i = 0; i < tasks.size(); i++) {
                        mAdapter.add(tasks.get(i));
                        Toast.makeText(getApplicationContext(),"adding to adapter", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    /*public void createTask(View v) {
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
            t.setBookedDay("NONE");
            t.setCompleted(true);

            t.saveEventually();
            mAdapter.insert(t, 0);
            mTaskName.setText("");
            mTaskLocation.setText("");
            mTaskCost.setText("");
        }
    }*/

    public void searchByName(View v) {
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        //query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("HotelName",mTaskName.getText().toString());
        //query.whereEqualTo("HotelPrice",mTaskCost.getText().toString());
        //query.whereEqualTo("HotelLocation",mTaskLocation.getText().toString());
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

    public void searchByLocation(View v) {
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        //query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("HotelLocation",mTaskLocation.getText().toString());
        //query.whereEqualTo("HotelPrice",mTaskCost.getText().toString());
        //query.whereEqualTo("HotelLocation",mTaskLocation.getText().toString());
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

    public void searchByNameAndLocation(View v) {
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        //query.whereEqualTo("user", ParseUser.getCurrentUser());
        //query.whereEqualTo("HotelLocation",mTaskLocation.getText().toString());
        //query.whereEqualTo("HotelName",mTaskName.getText().toString());
        query.whereEqualTo("completed",true);
        //query.whereEqualTo("HotelLocation",mTaskLocation.getText().toString());
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
        mAdapter.clear();
        //lAdapter.clear();
        //lAdapter.add(task);
        if (task.getMon() && task.getTue() && task.getWed() && task.getThu() && task.getFri() && task.getSat() && task.getSun()) {
            Toast.makeText(getApplicationContext(),"No slots in the selected Hotel", Toast.LENGTH_LONG).show();
            //task.setCompleted(!task.isCompleted());

        } else {
            //Toast.makeText(getApplicationContext(), "Can book", Toast.LENGTH_LONG).show();

            if (mTaskday.getText().toString().equalsIgnoreCase("monday")){
                if(!task.getMon()){
                    Toast.makeText(getApplicationContext(), "Venue is booked on the given day", Toast.LENGTH_LONG).show();
                    task.setMon(true);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can not book on the given day! Please select other day", Toast.LENGTH_LONG).show();
                }
            }

            else if (mTaskday.getText().toString().equalsIgnoreCase("tuesday")){
                if(!task.getTue()){
                    Toast.makeText(getApplicationContext(), "Venue is booked on the given day", Toast.LENGTH_LONG).show();
                    task.setTue(true);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can not book on the given day! Please select other day", Toast.LENGTH_LONG).show();
                }
            }
            else if (mTaskday.getText().toString().equalsIgnoreCase("Wednesday")){
                if(!task.getWed()){
                    Toast.makeText(getApplicationContext(), "Venue is booked on the given day", Toast.LENGTH_LONG).show();
                    task.setWed(true);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can not book on the given day! Please select other day", Toast.LENGTH_LONG).show();
                }
            }
            else if (mTaskday.getText().toString().equalsIgnoreCase("Thursday")){
                if(!task.getThu()){
                    Toast.makeText(getApplicationContext(), "Venue is booked on the given day", Toast.LENGTH_LONG).show();
                    task.setThu(true);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can not book on the given day! Please select other day", Toast.LENGTH_LONG).show();
                }
            }
            else if (mTaskday.getText().toString().equalsIgnoreCase("friday")){
                if(!task.getFri()){
                    Toast.makeText(getApplicationContext(), "Venue is booked on the given day", Toast.LENGTH_LONG).show();
                    task.setFri(true);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can not book on the given day! Please select other day", Toast.LENGTH_LONG).show();
                }
            }
            else if (mTaskday.getText().toString().equalsIgnoreCase("Saturday")){
                if(!task.getSat()){
                    Toast.makeText(getApplicationContext(), "Venue is booked on the given day", Toast.LENGTH_LONG).show();
                    task.setSat(true);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can not book on the given day! Please select other day", Toast.LENGTH_LONG).show();
                }
            }
            else if (mTaskday.getText().toString().equalsIgnoreCase("Sunday")){
                if(!task.getSun()){
                    Toast.makeText(getApplicationContext(), "Venue is booked on the given day", Toast.LENGTH_LONG).show();
                    task.setSun(true);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can not book on the given day! Please select other day", Toast.LENGTH_LONG).show();
                }
            }

            else {

                Toast.makeText(getApplicationContext(), "Please enter vaild day of the week!!(Eg: Monday)", Toast.LENGTH_LONG).show();
            }



        }
        task.saveEventually();
    }


}

