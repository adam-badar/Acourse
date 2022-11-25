package com.example.test;

import java.util.ArrayList;
import java.util.Objects;

public class Course {

        public String courseName;
        public int courseCode;
        public ArrayList<String> prerequisites;
        public ArrayList<String> session_offerings;
        public boolean approved;


        public String getCourseName() {
            return courseName;
        }

        public int getCourseCode() {
            return courseCode;
        }

        public Boolean getApproved() {
            return this.approved;
        }


        public Course(String name)
        {
            this.courseName = courseName;
            this.courseCode = courseCode;
            this.prerequisites = new ArrayList<String>();
            this.session_offerings = new ArrayList<String>();
            this.approved = false;


        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Course course = (Course) o;
            return courseName == course.courseName && courseCode == course.courseCode;
        }

        @Override
        public int hashCode() {
            return courseCode;
        }

}
