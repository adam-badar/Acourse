package com.example.test;

import java.util.ArrayList;

public class Course {

        public String courseName;
        public String courseCode;
        public ArrayList<String> prerequisites;
        public ArrayList<String> sessionOfferings;

        public Course(){

        }

        public Course(String courseName, String courseCode, ArrayList<String> prerequisites, ArrayList<String> sessionOfferings)
        {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.prerequisites = new ArrayList<String>();
        this.sessionOfferings = new ArrayList<String>();

        }

        public String getCourseName() {
            return courseName;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public ArrayList<String> getPrerequisites() {
                return prerequisites;
        }

        public ArrayList<String> getSessionOfferings() {
                return sessionOfferings;
        }



}
