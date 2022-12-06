package com.example.test;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminCourse {

    public String courseName;
    public String courseCode;
    public String prerequisites;
    public String sessionOfferings, sessionTake;
    public int yearToTake, sessionToTake;
    public int weigth;
    public ArrayList<AdminCourse> prereqs;

    public AdminCourse(){
        this.prerequisites = "";
        this.sessionOfferings ="";
        this.weigth = 0;
        this.yearToTake = 0;
        this.sessionToTake = 0;
    }
    public AdminCourse(String courseName){
        this.courseCode = courseName;
        this.prerequisites = "";
        this.sessionOfferings ="";
        this.weigth = 0;
        this.yearToTake = 0;
        this.sessionToTake = 0;
    }

    public AdminCourse(String courseName, String courseCode, String prerequisites, String sessionOfferings)
    {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.prerequisites = prerequisites;
        this.sessionOfferings =sessionOfferings;
        this.weigth = 0;
        this.yearToTake = 0;
        this.sessionToTake = 0;
    }
    public AdminCourse (String courseCode,String prerequisites, String sessionOfferings, int weigth){
        this.courseCode = courseCode;
        this.sessionOfferings =sessionOfferings;
        this.prerequisites = prerequisites;
        this.weigth = weigth;
        this.yearToTake = 0;
        this.sessionToTake = 0;
    }
    public AdminCourse (String courseCode,ArrayList<AdminCourse> prerequisites, String sessionOfferings, int weigth){
        this.courseCode = courseCode;
        this.sessionOfferings =sessionOfferings;
        this.prereqs = prereqs;
        this.weigth = weigth;
        this.yearToTake = 0;
        this.sessionToTake = 0;
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

    public List<String> getPrerequisitesList() {
        List<String> courses = Arrays.asList(this.prerequisites.split(","));
        return courses;
    }

    public String [] getSessionOfferings() {
        return this.sessionOfferings.split(",");
    }



}