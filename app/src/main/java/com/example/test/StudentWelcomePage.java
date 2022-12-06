package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentWelcomePage extends AppCompatActivity {
    private Button student_create_course;
    private Button student_past_courses;
    private Button student_view_timeline;
    private Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        TextView welcomeText = findViewById(R.id.welcome);
        //String name = sp.getString("first_name",null);
        //welcomeText.setText("WELCOME\n"+name);
        signout = (Button) findViewById(R.id.logOutButton);
        student_create_course = (Button) findViewById(R.id.search_courses);
        student_past_courses = (Button) findViewById(R.id.past_courses);
        student_view_timeline = (Button) findViewById(R.id.view_timeline);
        student_create_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentWelcomePage.this, StudentSearchCourse.class));
            }
        });
        student_past_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentWelcomePage.this, StudentViewPastCourses.class));
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentWelcomePage.this, SignInActivity.class));
                Toast.makeText(StudentWelcomePage.this, "Successfully Signed Out", Toast.LENGTH_SHORT).show();

            }
        });
        student_view_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentWelcomePage.this, ViewTimetable.class));
            }
        });
    }
}