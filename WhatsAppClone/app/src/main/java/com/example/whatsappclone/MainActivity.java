package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    boolean loginMode;
    EditText passwordText;

    public void redirectIfLoggedIn() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            startActivity(intent);
        }
    }

    public void toggleLogin(View view) {
        Button loginButton = findViewById(R.id.registerButton);
        Button singinButton = findViewById(R.id.button);

        if (loginMode) {
            loginMode = false;
            loginButton.setText("Or register");
            singinButton.setText("Login");
        } else {
            loginMode = true;
            loginButton.setText("Or login");
            singinButton.setText("Register");
        }
    }

    public void logIn(View view) {
        if (loginMode) {
            ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        redirectIfLoggedIn();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            ParseUser user = new ParseUser();

            user.setPassword(passwordText.getText().toString());
            user.setUsername(usernameText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        redirectIfLoggedIn();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("WhatsApp Login");

        redirectIfLoggedIn();

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
