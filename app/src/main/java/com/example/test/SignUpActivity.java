package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    private ProgressBar progressBar;

    @SuppressLint("RestrictedApi")
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
        //progressBar =
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
                } else if(!txt_email.matches(pattern)){
                    Toast.makeText(SignUpActivity.this, "Incorrect Email Format", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(txt_email, txt_password);
                }
            }
        });
        getSupportActionBar().setTitle("Sign-In");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
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

    /*
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText id;
    private EditText password;
    private EditText confirmpassword;
    private Button SignUpButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER = "user";
    private static final String TAG = "SignUpActivity";
    private User user;


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

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_firstname = firstname.getText().toString();
                String txt_lastname = lastname.getText().toString();
                String txt_email = email.getText().toString();
                String txt_id = id.getText().toString();
                String txt_password = password.getText().toString();
                String txt_password2 = confirmpassword.getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    if (Stream.of(txt_email, txt_password, txt_firstname, txt_lastname, txt_id, txt_password2).anyMatch(TextUtils::isEmpty)) {
                        Toast.makeText(SignUpActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                    }
                    if (txt_password.length() < 6) {
                        Toast.makeText(SignUpActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                    } else if (!txt_password.equals(txt_password2)) {
                        Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    } else {
                        user = new User(txt_email, txt_password, txt_firstname, txt_lastname, txt_id);
                        signUpUser(txt_email, txt_password);
                    }
                }
            }
        });

    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

        HashMap<String , Object> map = new HashMap<>();
        map.put("Name", txt_firstname + txt_lastname);
        map.put("Email", txt_email);
        map.put("ID", txt_id);
        map.put("Password", txt_password);
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }*/
}