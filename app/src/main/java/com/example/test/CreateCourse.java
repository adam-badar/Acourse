package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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


    public class CreateCourse extends AppCompatActivity {

        private EditText courseName;
        private EditText courseCode;
        private Spinner prerequisites;
        private Spinner sessionOfferings;


        //private FirebaseAuth fAuth;

        private FirebaseAuth auth;
        private FirebaseDatabase db;
        private DatabaseReference reference;

        private ActivityMainBinding binding;
        private Button createcoursebutton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_course);

            courseName = findViewById(R.id.courseName);
            courseCode = findViewById(R.id.courseCode);
            prerequisites = findViewById(R.id.prereq_dropdown);
            sessionOfferings = findViewById(R.id.session_dropdown);
            createcoursebutton = findViewById(R.id.addcourses);

            auth = FirebaseAuth.getInstance();
            //progressBar =
            String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

            createcoursebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String txt_courseName = courseName.getText().toString().trim();
                    String txt_courseCode = courseCode.getText().toString().trim();
                    String txt_prerequisites = prerequisites.getText().toString().trim();
                    String txt_sessionOfferings = sessionOfferings.getText().toString().trim();

                    if ( TextUtils.isEmpty(txt_courseName) || TextUtils.isEmpty(txt_courseCode) || TextUtils.isEmpty((txt_sessionOfferings) {
                            Toast.makeText(com.example.test.CreateCourse.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                    } else if (txt_courseCode.length() != 6) {
                        Toast.makeText(com.example.test.CreateCourse.this, "CourseCode too short", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Course course = new Course(txt_courseName, txt_courseCode, prerequisites, txt_sessionOfferings);
                        db = FirebaseDatabase.getInstance();
                        reference = db.getReference("Courses");
                        reference.child(txt_courseCode).setValue(course);
                        //registerUser(txt_email, txt_password);
                    }
                }
            });
        }

        private void CreateCourse(String courseName, String courseCode, ) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateCourse.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(com.example.test.CreateCourse.this, "Course creation successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else {
                        Toast.makeText(com.example.test.CreateCourse.this, "Creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}