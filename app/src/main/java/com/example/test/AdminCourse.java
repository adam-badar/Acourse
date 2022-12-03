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
                        System.out.println(courseID);
                        courseName = admincourse.courseName;
                        prerequisites = admincourse.prerequisites;
                        sessionOfferings = admincourse.sessionOfferings;
                        System.out.println("current course: "+courseName);
                        System.out.println("prereuisites: "+prerequisites);
                        System.out.println("sessions: "+sessionOfferings);
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setSessionOfferings(String sessionOfferings) {
        this.sessionOfferings = sessionOfferings;
    }
}