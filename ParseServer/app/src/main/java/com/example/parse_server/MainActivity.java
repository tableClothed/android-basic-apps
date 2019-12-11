package com.example.parse_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView loginText;
    Boolean signUpModeActive = false;

    public void signIn(View view) {
        EditText email = findViewById(R.id.emailText);
        EditText password = findViewById(R.id.passwordText);

        if (email.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(this, "Password and email are required", Toast.LENGTH_SHORT);

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
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null ) {
                            Log.i("Login", "ok!");
                        } else {
                            e.printStackTrace();
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

        loginText = findViewById(R.id.loginText);
        loginText.setOnClickListener(this);


//        ParseObject score = new ParseObject("Score");
//        score.put("username", "nick");
//        score.put("score", 45);
//        score.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.i("success", "we sved the score");
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//        query.getInBackground("jfOvID0yXp", new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (e == null && object != null) {
//                    object.put("score", 85);
//                    object.saveInBackground();
//                }
//            }
//        });

        ParseAnalytics.trackAppOpenedInBackground(new Intent());

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
        }
    }
}
