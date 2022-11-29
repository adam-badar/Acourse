package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminWelcomePage extends AppCompatActivity {
    private Button admin_create_course;
    private Button admin_edit_course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        admin_create_course = (Button) findViewById(R.id.create_course_button);
        admin_edit_course = (Button) findViewById(R.id.edit_course_button);
        admin_create_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminWelcomePage.this, AdminAddCourseActivity.class));
            }
        });
        admin_edit_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminWelcomePage.this, AdminEditCourseActivity.class));
            }
        });
    }
}
