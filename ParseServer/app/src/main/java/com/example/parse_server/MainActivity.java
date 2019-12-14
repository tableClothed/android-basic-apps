package com.example.parse_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    TextView loginText;
    Boolean signUpModeActive = true;
    EditText email;
    EditText password;

    public void showUserList() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }

    public void signUpClicked(View view) {

        if (email.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(this, "Password and email are required", Toast.LENGTH_SHORT).show();

        } else {
            if (signUpModeActive) {
                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(email.getText().toString());
                parseUser.setPassword(password.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Signup", "Success");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                // Login
                ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null ) {
                            Log.i("Login", "ok!");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram");

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);

        loginText = findViewById(R.id.loginText);
        loginText.setOnClickListener(this);
        password.setOnKeyListener(this);
        ImageView instImg = findViewById(R.id.instImg);
        LinearLayout backgroundLayout = findViewById(R.id.relativeLayout);
        instImg.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            showUserList();
        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginText) {
            Button signUpButton = findViewById(R.id.signinButton);

            if (signUpModeActive) {
                signUpModeActive = false;
                signUpButton.setText("Login");
                loginText.setText("or, Signup?");
            } else {
                signUpModeActive = true;
                signUpButton.setText("Sign up");
                loginText.setText("or, Login?");

            }
        } else if (view.getId() == R.id.instImg || view.getId() == R.id.relativeLayout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            signUpClicked(v);
        }
        return false;
    }
}
