package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

class

public class AddCourseActivity extends AppCompatActivity {
    Spinner spinner;
    String[] sessions = {"Fall", "Winter", "Summer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        spinner = findViewById(R.id.dropdown_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_spinner_item, sessions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddCourseActivity.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    /*
    AutoCompleteTextView test_dropdown;
    Spinner items;
    ArrayList<String> sessions = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        test_dropdown = findViewById(R.id.);
        items = findViewById(R.id.dropdown_menu);

        sessions.add("Fall");
        sessions.add("Winter");
        sessions.add("Summer");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(searchCourses2.this,
                android.R.layout.simple_spinner_dropdown_item, courses);
//
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(searchCourses2.this,
                android.R.layout.simple_spinner_dropdown_item, courses);

        test_dropdown.setAdapter(adapter);
        items.setAdapter(adapter2);
    }*/


}