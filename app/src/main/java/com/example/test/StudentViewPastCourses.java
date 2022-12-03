package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentViewPastCourses extends AppCompatActivity {
    DatabaseReference rootRef;
    String courseList;
    TextView user;
    ListView list;
    ArrayList<String> courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_courses);

        courses = new ArrayList<>();

        rootRef = FirebaseDatabase.getInstance().getReference("Users").child("Students")
                    .child("1002349856");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList = snapshot.child("coursesTaken").getValue(String.class);
                String str[] = courseList.split(",");
                for(String i:str) courses.add(i);
                list = findViewById(R.id.PastCoursesView);
                ArrayAdapter adapt = new ArrayAdapter(StudentViewPastCourses.this, android.R.layout.simple_list_item_1, courses);
                list.setAdapter(adapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}