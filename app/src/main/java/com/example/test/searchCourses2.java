package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;

public class searchCourses2 extends AppCompatActivity {

    AutoCompleteTextView test_dropdown;
    Spinner items;
    ArrayList<String> courses = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_courses2);

        test_dropdown = findViewById(R.id.SearchCoursesId);
        items = findViewById(R.id.courses);

        courses.add("CSCA08");
        courses.add("MATA31");
        courses.add("MATA37");
        courses.add("MATA22");
        courses.add("ENGA01");
        courses.add("CSCB07");
        courses.add("CSCB20");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(searchCourses2.this,
                android.R.layout.simple_spinner_dropdown_item,courses);
//
        ArrayAdapter<String>  adapter2 = new ArrayAdapter<String>(searchCourses2.this,
                android.R.layout.simple_spinner_dropdown_item,courses);

        test_dropdown.setAdapter(adapter);
        items.setAdapter(adapter2);
    }
}