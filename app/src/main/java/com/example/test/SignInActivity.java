package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private TextView signup_tag_click;
    private Button sign_in_click;
    private TextView forgotPassword;
    private EditText email;
    private EditText password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        sign_in_click = findViewById(R.id.Signin);
        // Takes to sign up page
        signup_tag_click = (TextView) findViewById(R.id.signup);
        signup_tag_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        // Takes to sign In page

        sign_in_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();

            }
        });

        forgotPassword = findViewById(R.id.forgotpass);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast myToast = Toast.makeText(SignInActivity.this,
                        "Coming Soon", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });


    }

    private void perforLogin() {

        String txt_email = email.getText().toString().trim();
        String txt_password = password.getText().toString().trim();

        //Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(txt_email) ||
                TextUtils.isEmpty(txt_password)) {
            Toast.makeText(SignInActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
        } else if (txt_password.length() < 6) {
            Toast.makeText(SignInActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
        }  else if (!txt_email.matches(pattern)) {
            Toast.makeText(SignInActivity.this, "Incorrect email format", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity() {
        email = findViewById(R.id.email);
        String txt_email = email.getText().toString().trim();
        int ind = txt_email.indexOf("@");
        if(txt_email.substring(ind+1, ind+8).equals("student")) {
            startActivity(new Intent(getApplicationContext(), StudentHomepageActivity.class));
        }else if(txt_email.substring(ind+1, ind+6).equals("admin")){
            startActivity(new Intent(getApplicationContext(), AdminWelcomePage.class));
        }
    }

}