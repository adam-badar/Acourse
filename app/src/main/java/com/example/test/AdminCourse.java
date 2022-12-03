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