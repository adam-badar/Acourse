package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SignInActivity extends AppCompatActivity {

    private Model model;

    private TextView signup_tag_click;
    private Button sign_in_click;
    private TextView forgotPassword;
    private EditText email;
    private EditText password;


    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        model = new Model();

        //mAuth = model.mAuth;
        //mUser = model.mUser;

        SignInView view = new SignInView();



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
            public void onClick(View v) {
                view.createToast(SignInActivity.this,
                        "Coming Soon");
            }
        });


    }


    private void perforLogin() {

        SignInView view = new SignInView();
        Presenter p = new Presenter();

        String txt_email = email.getText().toString().trim();
        String txt_password = password.getText().toString().trim();

        String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (p.checkIfEmpty(txt_email,txt_password)) {
            view.createToast(SignInActivity.this, "Empty Credentials");
        } else if (p.checkPasswordLength(txt_password)) {
            view.createToast(SignInActivity.this, "Password too short");
        }  else if (p.checkEmailFormat(txt_email)) {
            view.createToast(SignInActivity.this, "Incorrect email format");
        }
        else {
            model.mAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        view.createToast(SignInActivity.this, "Login successful!");
                        sendUserToNextActivity();
                    }
                    else {
                        view.createToast(SignInActivity.this, "Login failed");
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity() {
        email = findViewById(R.id.email);
        String txt_email = email.getText().toString().trim();

        Presenter p = new Presenter();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", txt_email);

        //Model
        FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                p.createCourseList(dataSnapshot);

//move to model
                editor.putStringSet("courses", p.taskSet);

                if (p.isStudentEmail(txt_email)) {

                    //move to model
                    FirebaseDatabase.getInstance().getReference().child("Users").child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            p.updateStudentCourse(dataSnapshot, txt_email, editor);

                            startActivity(new Intent(getApplicationContext(), StudentWelcomePage.class));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else if(p.isAdminEmail(txt_email)){


                    FirebaseDatabase.getInstance().getReference().child("Users").child("Admins").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            p.updateAdminCourse(dataSnapshot, txt_email, editor);

                            startActivity(new Intent(getApplicationContext(), AdminWelcomePage.class));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}



