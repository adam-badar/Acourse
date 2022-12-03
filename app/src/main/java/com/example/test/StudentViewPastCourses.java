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
    ArrayList<AdminCourse> adminCourseList = new ArrayList<AdminCourse>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_courses);
        pastCourseButton = findViewById(R.id.pastCourseButton);
        Context context = this;
        FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    adminCourseList.add(admincourse);
                }
                NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(context, adminCourseList);
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