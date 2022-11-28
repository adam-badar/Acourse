package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StudentHomepageActivity extends AppCompatActivity {

    private Button view_timeline_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homepage);

        view_timeline_click = findViewById(R.id.timeline_button);
        view_timeline_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(StudentHomepageActivity.this, searchCourses2.class)));
            }
        });
    }
}