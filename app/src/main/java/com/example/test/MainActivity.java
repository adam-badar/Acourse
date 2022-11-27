package com.example.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
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
                Toast myToast = Toast.makeText(MainActivity.this,
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
            Toast.makeText(MainActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
        } else if (txt_password.length() < 6) {
            Toast.makeText(MainActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
        }  else if (!txt_email.matches(pattern)) {
            Toast.makeText(MainActivity.this, "Incorrect email format", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(getApplicationContext(), WelcomeAdminActivity.class));
        }
    }

}