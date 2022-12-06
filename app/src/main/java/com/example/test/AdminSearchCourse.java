package com.example.test;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class AdminSearchCourse extends AppCompatActivity {
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

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_courses2);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        tempSet = sp.getStringSet("courses", null);
        textSearch = findViewById(R.id.courseSearchBar);
        Context context = this;
        FirebaseDatabase.getInstance().getReference().child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    adminCourseList.add(admincourse);
                }
                NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(context, adminCourseList);
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
