package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class StudentViewPastCourses extends AppCompatActivity {
    private TextView pastCourseButton;
    public ArrayList<AdminCourse> pastCourseList = new ArrayList<AdminCourse>();
    private String studentID;
    private String coursesTaken;
    private ArrayList<String> coursesTakenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_courses);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        coursesTaken = sp.getString("courses_taken", null);
        coursesTakenList = new ArrayList<String>(Arrays.asList(coursesTaken.split(",")));
        studentID = sp.getString("id", null);
        pastCourseButton = findViewById(R.id.pastCourseButton);
        Context context = this;
        FirebaseDatabase.getInstance().getReference().child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AdminCourse course = snapshot.getValue(AdminCourse.class);
                    if (coursesTakenList.contains(course.courseCode)) {
                        pastCourseList.add(course);
                    }
                }
                NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(context, pastCourseList);
                ListView numbersListView = findViewById(R.id.PastCoursesView);
                numbersListView.setAdapter(numbersArrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        pastCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentSearchCourse.class));
            }
        });
    }
}