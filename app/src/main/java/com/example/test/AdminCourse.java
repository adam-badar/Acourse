package com.example.test;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public String toString() {
        return courseCode;
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