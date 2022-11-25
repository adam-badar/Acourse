package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.test.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.stream.Stream;


public class SignUpActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText id;
    private EditText password;
    private EditText confirmpassword;
    private Button signupbutton;

    //private FirebaseAuth fAuth;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference reference;

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        id = findViewById(R.id.idnum);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        signupbutton = findViewById(R.id.Signup);

        auth = FirebaseAuth.getInstance();
        String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_firstname = firstname.getText().toString().trim();
                String txt_lastname = lastname.getText().toString().trim();
                String txt_email = email.getText().toString().trim();
                String txt_id = id.getText().toString().trim();
                String txt_password = password.getText().toString().trim();
                String txt_password2 = confirmpassword.getText().toString().trim();
                //Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(txt_firstname) || TextUtils.isEmpty(txt_lastname) || TextUtils.isEmpty(txt_email) ||
                        TextUtils.isEmpty(txt_id) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_password2)) {
                    Toast.makeText(SignUpActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else if (!txt_password.equals(txt_password2)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (!txt_email.matches(pattern)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (txt_id.length() != 10) {
                    Toast.makeText(SignUpActivity.this, "Invalid IDD", Toast.LENGTH_SHORT).show();
                } else if (!txt_id.substring(0, 3).equals("100") && !txt_id.substring(0, 3).equals("200")) {
                    Toast.makeText(SignUpActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User(txt_email, txt_password, txt_firstname, txt_lastname, txt_id);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    if (txt_id.substring(0, 3).equals("100")) {
                        reference.child("Students").child(txt_id).setValue(user);
                    }
                    else {
                        reference.child("Admins").child(txt_id).setValue(user);
                    }
                    registerUser(txt_email, txt_password);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Registering user successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}