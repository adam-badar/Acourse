package com.example.test;

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
    public void setName(String name) {this.courseName = name;}
    public void setPrerequisites(String prereq) {this.prerequisites = prereq;}
    public void setSessionOfferings(String session) {this.sessionOfferings = session;}


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