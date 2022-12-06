package com.example.test;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StudentHomepageActivity extends AppCompatActivity {

    private Button view_timeline_click, view_generate_courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);

//        view_timeline_click = findViewById(R.id.timeline_button);
//        view_timeline_click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity((new Intent(StudentHomepageActivity.this, StudentSearchCourse.class)));
//            }
//        });
//
//        view_generate_courses = findViewById(R.id.view_timeline);
//        view_generate_courses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity((new Intent(StudentHomepageActivity.this, ViewTimetable.class)));
//            }
//        });
    }
}