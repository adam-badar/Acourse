package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewPastCourses extends AppCompatActivity {

    ArrayList<String> courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_courses);

        courses = new ArrayList<>();

        courses.add("CSCA08");
        courses.add("MATA31");
        courses.add("MATA37");
        courses.add("MATA22");
        courses.add("ENGA01");
        courses.add("CSCB07");
        courses.add("CSCB20");

        ListView listView = (ListView) findViewById(R.id.PastCoursesView);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(ViewPastCourses.this, android.R.layout.simple_list_item_1,courses);
        listView.setAdapter(adapter);
    }
}