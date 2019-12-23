package com.example.uberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public  void redirectActivity() {
        if (ParseUser.getCurrentUser().get("riderOrDriver") == "rider") {
            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
        }
    }

    public void getStarted(View view) {
        Switch userSwitch = (Switch) findViewById(R.id.switch1);

        String userType = "rider";

        if (userSwitch.isChecked()) {
            userType = "driver";
        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if (ParseUser.getCurrentUser() == null) {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {

                    } else {

                    }
                }
            });
        } else {
            if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {
                redirectActivity();
            }
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
