package com.example.snapchatclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwrodText;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public void goClicked(View view) {
        firebaseAuth.signInWithEmailAndPassword(usernameText.getText().toString(), passwrodText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            login();
                        } else {
                            firebaseAuth.createUserWithEmailAndPassword(usernameText.getText().toString(), passwrodText.getText().toString())
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                login();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
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


        if (firebaseAuth.getCurrentUser() != null) {
            login();
        }

        usernameText = findViewById(R.id.usernameText);
        passwrodText = findViewById(R.id.passwordText);
    }

    public void login() {
        Intent intent = new Intent(this, SnapActivity.class);
        startActivity(intent);
    }
}
