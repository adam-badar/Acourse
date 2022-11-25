package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CourseListPopUp extends AppCompatActivity {

    ListView Courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_pop_up);

        Courses = findViewById(R.id.List_of_courses);

        CourseListAdapter CSCA48 = new CourseListAdapter("CSCA48", "IPR", "Yes");
        CourseListAdapter CSCA08 = new CourseListAdapter("CSCA08", "CR", "Yes");
        CourseListAdapter MATA31 = new CourseListAdapter("MATA31", "CR", "Yes");
        CourseListAdapter MATA67 = new CourseListAdapter("MATA67", "CR", "Yes");
        CourseListAdapter MATA37 = new CourseListAdapter("MATA37", "ICR", "Yes");
        CourseListAdapter MATA22 = new CourseListAdapter("MATA22", "ICR", "Yes");

        ArrayList<CourseListAdapter> courses = new ArrayList<>();
        courses.add(CSCA48);
        courses.add(CSCA08);
        courses.add(MATA31);
        courses.add(MATA67);
        courses.add(MATA22);
        courses.add(MATA37);

        CourseAdapter adapter = new CourseAdapter(this, R.layout.adapter_view_timeline, courses);
        Courses.setAdapter(adapter);
    }

}