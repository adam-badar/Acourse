package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentPopUpMenu extends AppCompatActivity {

    private Button courselist;
    private FirebaseDatabase db;
    private AdminCourse course;
    private String precourse;
    private String plsprecourse;
    String courseCode;
    private String coursename;
    private String prereq;
    private String session;
    private String takenCourses = "";

    private String x;
    private int j = 0;
    private int k = 0;
    //private String y = StudentSearchCourse.get


    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
    DatabaseReference referencia;
    DatabaseReference referenzi;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7), (int)(height*.6));
        TextView field = (TextView) findViewById(R.id.popupCourseTitle);
        field.setText("AddText");
        db = FirebaseDatabase.getInstance();
        referencia = db.getReference("Users").child("Students");
        //if (FirebaseDatabase.getInstance().getReference("Users").child("1002349856").child("Past Courses");
        referenzi = FirebaseDatabase.getInstance().getReference("Users").child("Students").child("1003456789");

        courselist = findViewById(R.id.addButton);
        courselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = StudentSearchCourse.getCourseTitle();
                // sometimes there may not be a Past Courses section so could be bad
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            course = childSnapshot.getValue(AdminCourse.class);
                            if (course.courseCode.equals(x)) {
                                prereq = course.prerequisites;
                                ArrayList<String> listy = new ArrayList<>(Arrays.asList(prereq.split(", ")));
                                Toast.makeText(StudentPopUpMenu.this, prereq , Toast.LENGTH_SHORT).show();
                                //String z = "";

                                /*
                                for (String corse: listy) {
                                    if (!z.contains(corse)) {
                                        j++;
                                        Toast.makeText(StudentPopUpMenu.this, "Prerequisites not met", Toast.LENGTH_SHORT).show();

                                    }
                                }*/
                                //Toast.makeText(StudentPopUpMenu.this, Integer.toString(j), Toast.LENGTH_SHORT).show();

                                referenzi.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        /*if (!snapshot.hasChild("coursesTaken")) {
                                            referencia.child("1003456789").child("coursesTaken").setValue("");
                                            Toast.makeText(StudentPopUpMenu.this, "buddy" , Toast.LENGTH_SHORT).show();
                                        }*/
                                        for (DataSnapshot broSnapshot: snapshot.getChildren()) {
                                            precourse = broSnapshot.getKey();
                                            //Toast.makeText(StudentPopUpMenu.this, precourse , Toast.LENGTH_SHORT).show();
                                            if (precourse.equals("coursesTaken")) {
                                                plsprecourse = broSnapshot.getValue(String.class);
                                                //Toast.makeText(StudentPopUpMenu.this, "precourse" , Toast.LENGTH_SHORT).show();
                                                for (String corse: listy) {
                                                    if (!plsprecourse.contains(corse)) {
                                                        j++;
                                                        Toast.makeText(StudentPopUpMenu.this, "Prerequisites not met" , Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        takenCourses = plsprecourse;
                                        Toast.makeText(StudentPopUpMenu.this, takenCourses, Toast.LENGTH_SHORT).show();
                                        if (takenCourses.contains(x)) {
                                            Toast.makeText(StudentPopUpMenu.this, "You have already taken this course", Toast.LENGTH_SHORT).show();
                                            k++;
                                        }
                                        if (k == 0) {
                                            String newTakenCourses = "";
                                            newTakenCourses = takenCourses + x + ",";
                                            //takenCourses = takenCourses + x;
                                            //takenCourses = takenCourses + ",";
                                            Toast.makeText(StudentPopUpMenu.this, Integer.toString(j) , Toast.LENGTH_SHORT).show();
                                            if (j == 0) {
                                                Toast.makeText(StudentPopUpMenu.this, "yo" , Toast.LENGTH_SHORT).show();
                                                referenzi.child("coursesTaken").setValue(newTakenCourses);
                                            }
                                        }

                                        //String z = snapshot.getValue(String.class);
                                        //Toast.makeText(StudentPopUpMenu.this, "hey", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(StudentPopUpMenu.this, Integer.toString(j), Toast.LENGTH_SHORT).show();
                                        //String p = takenCourses + "," + x;
                                        //takenCourses = takenCourses + "," + x;
                                        //Toast.makeText(StudentPopUpMenu.this, takenCourses, Toast.LENGTH_SHORT).show();
                                        //coursename = course.courseName;
                                        //session = course.sessionOfferings;
                                        //AdminCourse course1 = new AdminCourse(coursename, x, prereq, session);
                                        //if (course.courseName == null) Toast.makeText(StudentPopUpMenu.this, "u bum", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(StudentPopUpMenu.this, coursename, Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(StudentPopUpMenu.this, coursename, Toast.LENGTH_SHORT).show();
                                        //referencia.child("1002349856").child("Past Courses").setValue(takenCourses);


                                        /*
                                        for(DataSnapshot data: dataSnapshot.getChildren()) {
                                            if (data.child(prerequisites))
                                        }*/
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        throw error.toException();
                                    }
                                });


                            }
                        }
                        /*
                        courseName = dataSnapshot.child(x).child("courseName").getValue(String.class);
                        Toast.makeText(StudentPopUpMenu.this, "snapshot", Toast.LENGTH_SHORT).show();
                        prereq = dataSnapshot.child(x).child("prerequisites").getValue(String.class);
                        session = dataSnapshot.child(x).child("sessionOffering").getValue(String.class);
                        showData(dataSnapshot);*/
                        //Toast.makeText(StudentPopUpMenu.this, "snapshot", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });

                //
                //Toast.makeText(StudentPopUpMenu.this, coursename, Toast.LENGTH_SHORT).show();
                //Toast.makeText(StudentPopUpMenu.this, "yoyo", Toast.LENGTH_SHORT).show();
                //referencia.child("1002349856").child("Past Courses").child(x).child("courseName").setValue("hdsffdsfds");

                startActivity(new Intent(StudentPopUpMenu.this, StudentSearchCourse.class));
            }

        });

    }
    /*
    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            course1.setName(ds.child(x).getValue(AdminCourse.class).getCourseName());
            course1.setPrerequisites(ds.child(x).getValue(AdminCourse.class).getPrerequisites());
            course1.setSessionOfferings(ds.child(x).getValue(AdminCourse.class).getSessionOfferings());
        }
    }*/
}
