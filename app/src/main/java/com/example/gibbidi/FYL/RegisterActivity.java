package com.example.gibbidi.FYL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {

	private EditText mUsernameField;
	private EditText mPasswordField;
    private EditText mTypeField;
	private TextView mErrorField;


    String acctype;

    Spinner spinnertype;
    TextView account_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

        //account_type = (TextView) findViewById(R.id.account_type);
        //Spinner spinnertype = (Spinner) findViewById(R.id.spinnertype);

		mUsernameField = (EditText) findViewById(R.id.register_username);
		mPasswordField = (EditText) findViewById(R.id.register_password);
        mTypeField = (EditText) findViewById(R.id.register_type);
        //int position = spinnertype.getSelectedItemPosition();
        //spinnertype.setSelection(position);
        //acctype = (String) spinnertype.getSelectedItem();

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnertype.setAdapter(adapter);

		mErrorField = (TextView) findViewById(R.id.error_messages);

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	public void register(final View v){

        Toast.makeText(getApplicationContext(), "Successfully Registered ", Toast.LENGTH_LONG).show();
		if(mUsernameField.getText().length() == 0 || mPasswordField.getText().length() == 0)
			return;

		v.setEnabled(false);
		final ParseUser user = new ParseUser();
		user.setUsername(mUsernameField.getText().toString());
		user.setPassword(mPasswordField.getText().toString());
        user.put("type",mTypeField.getText().toString());
        //Toast.makeText(getApplicationContext(),"Got Data", Toast.LENGTH_LONG).show();
		mErrorField.setText("");

		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
                    if((user.getString("type")).equalsIgnoreCase("manager")){
                        Intent intent = new Intent(RegisterActivity.this, Mangeroption.class);
                        startActivity(intent);
                        finish();
                    }

                    else if ((user.getString("type")).equalsIgnoreCase("user")) {
                        Intent intent = new Intent(RegisterActivity.this, BookHotel1.class);
                        startActivity(intent);
                        finish();
                    }

				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
					switch(e.getCode()){
					case ParseException.USERNAME_TAKEN:
						mErrorField.setText("Sorry, this username has already been taken.");
						break;
					case ParseException.USERNAME_MISSING:
						mErrorField.setText("Sorry, you must supply a username to register.");
						break;
					case ParseException.PASSWORD_MISSING:
						mErrorField.setText("Sorry, you must supply a password to register.");
						break;
					default:
						mErrorField.setText(e.getLocalizedMessage());
					}
					v.setEnabled(true);
				}
			}
		});
	}

	public void showLogin(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}


}
