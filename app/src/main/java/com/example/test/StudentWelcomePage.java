package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StudentWelcomePage extends AppCompatActivity {
    private Button student_create_course;
    private Button student_past_courses;
    private Button student_view_timeline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);
        student_create_course = (Button) findViewById(R.id.search_courses);
        student_past_courses = (Button) findViewById(R.id.past_courses);
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
    }
}