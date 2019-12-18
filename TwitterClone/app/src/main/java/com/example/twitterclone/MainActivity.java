package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {


    public void redirectUser() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
        }
    }

    public void logIn(View view) {
        final EditText userName = findViewById(R.id.nicknameText);
        final EditText passwordText = findViewById(R.id.passwordText);

        ParseUser.logInInBackground(userName.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    //Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT);
                    redirectUser();
                } else {
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(userName.getText().toString());
                    parseUser.setPassword(passwordText.getText().toString());

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //Toast.makeText(MainActivity.this, "Sign up OK", Toast.LENGTH_SHORT);
                                redirectUser();
                            } else {
                                Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT);
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Twitter: Login");
        redirectUser();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
