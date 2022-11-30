package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminWelcomePage extends AppCompatActivity {
    private Button admin_create_course;
    private Button admin_edit_course;
    public static String[] courseArray;
    ArrayList<String> courseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        /*FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    courseList.add(admincourse.courseCode);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/
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
