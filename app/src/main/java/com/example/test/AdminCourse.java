package com.example.test;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCourse {

    public String courseName;
    public String courseCode;
    public String prerequisites;
    public String sessionOfferings;

    public AdminCourse(){

    }

    public AdminCourse(String courseName, String courseCode, String prerequisites, String sessionOfferings)
    {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.prerequisites = prerequisites;
        this.sessionOfferings =sessionOfferings;
    }
    public AdminCourse(String courseID) {
        this.courseCode = courseID;
        FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    if (admincourse.courseCode.equals(courseID)) {
                        courseName = admincourse.courseName;
                        prerequisites = admincourse.prerequisites;
                        sessionOfferings = admincourse.sessionOfferings;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public String getSessionOfferings() {
        return sessionOfferings;
    }




}