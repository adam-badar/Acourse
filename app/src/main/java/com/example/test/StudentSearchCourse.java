package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentSearchCourse extends AppCompatActivity {

    TextView test_dropdown;
    Dialog dialog;
    ArrayList<String> courses;
    AdminCourse courseClicked;
    private String courseClickedCode;
    private String courseClickedPre;
    protected static String courseTitle;
    ArrayList<AdminCourse> adminCourseList = new ArrayList<AdminCourse>();
    private EditText textSearch;
    private Button pastCourseButton;
    private Set<String> tempSet;
    private String studentID;
    private String coursesTaken;
    private ArrayList<String> coursesTakenList;
    private ArrayList<String> prereqList;
    protected static String getCourseTitle() {
        return courseTitle;
    }
    Set<String> courseCodeTempSet = new HashSet<>();

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_courses2);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        coursesTaken = sp.getString("courses_taken", null);
        coursesTakenList = new ArrayList<String>(Arrays.asList(coursesTaken.split(",")));
        textSearch = findViewById(R.id.courseSearchBar);
        Context context = this;
        FirebaseDatabase.getInstance().getReference().child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    adminCourseList.add(admincourse);
                }
                NumbersViewAdapter2 numbersArrayAdapter = new NumbersViewAdapter2(context, adminCourseList);
                ListView numbersListView = findViewById(R.id.coursesView);
                numbersListView.setAdapter(numbersArrayAdapter);
                textSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        numbersArrayAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}