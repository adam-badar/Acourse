package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class AdminAddCourseActivity extends AppCompatActivity {
   /* Spinner spinner;
    String[] sessions = {"Fall", "Winter", "Summer"};*/

    private Button createCourseButton;
    //below is multiple dropdown
    TextView sessionButton;
    boolean [] selectedSession;
    ArrayList<Integer> sessionList = new ArrayList<>();
    String [] sessionArray = {"Fall", "Winter", "Summer"};

    TextView courseButton;
    boolean [] selectedCourse;
    ArrayList<Integer> courseList = new ArrayList<>();
    String [] courseArray = {"CSCA48", "CSCA67", "CSCB36", "MATB41", "STAB52", "MATA31"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        createCourseButton = (Button) findViewById(R.id.create_course);

        //prerequisites
        courseButton = findViewById(R.id.course_dropdown);
        selectedCourse = new boolean[courseArray.length];

        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAddCourseActivity.this, StudentSearchCourse.class));
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminAddCourseActivity.this
                );
                builder.setTitle("Select Sessions");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(courseArray, selectedCourse, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            courseList.add(i);
                            Collections.sort(courseList);
                        } else {
                            courseList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j=0; j<courseList.size(); j++) {
                            stringBuilder.append(courseArray[courseList.get(j)]);
                            if (j != courseList.size()-1) {
                                stringBuilder.append(", ");
                            }
                        }
                        courseButton.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j=0; j<selectedCourse.length; j++) {
                            selectedCourse[j] = false;
                            courseList.clear();
                            courseButton.setText("");
                        }
                    }
                });
                builder.show();
            }
            //prerequisites

        });
        //sessions
        sessionButton = findViewById(R.id.session_dropdown_multiple);
        selectedSession = new boolean[sessionArray.length];
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminAddCourseActivity.this
                );
                builder.setTitle("Select Sessions");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(sessionArray, selectedSession, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            sessionList.add(i);
                            Collections.sort(sessionList);
                        } else {
                            sessionList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j=0; j<sessionList.size(); j++) {
                            stringBuilder.append(sessionArray[sessionList.get(j)]);
                            if (j != sessionList.size()-1) {
                                stringBuilder.append(", ");
                            }
                        }
                        sessionButton.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j=0; j<selectedSession.length; j++) {
                            selectedSession[j] = false;
                            sessionList.clear();
                            sessionButton.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

    }



}