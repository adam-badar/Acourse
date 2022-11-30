package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class AdminEditCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   /* Spinner spinner;
    String[] sessions = {"Fall", "Winter", "Summer"};*/
    private ListView listView;
    private Button createCourseButton;

    String [] selectCourseArray;//= {"Select Course", "MATA31", "MATA32", "MATA33", "MATA34"};
    //below is multiple dropdown
    TextView sessionButton;
    boolean [] selectedSession;
    ArrayList<Integer> sessionList = new ArrayList<>();
    String [] sessionArray = {"Fall", "Winter", "Summer"};

    TextView courseButton;
    boolean [] selectedCourse;
    ArrayList<Integer> courseList = new ArrayList<>();
   // String [] courseArray = {"CSCA48", "CSCA67", "CSCB36", "MATB41", "STAB52", "MATA31"};
    ArrayList<String> coursesList = new ArrayList<>();
    String [] courseArray;
    boolean setFirebase = false;
    String editCourseName;
    String editCourseCode;
    String editSessions;
    String editPrereq;
    private EditText courseName;
    private EditText courseCode;
    private TextView prerequisites;
    private TextView sessionOfferings;
    void setTextFunction(ArrayList<Integer> list, String [] array, TextView button) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j=0; j<list.size(); j++) {
            stringBuilder.append(array[list.get(j)]);
            if (j != list.size()-1) {
                stringBuilder.append(", ");
            }
        }
        button.setText(stringBuilder.toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        courseName = findViewById(R.id.courseName);
        courseCode = findViewById(R.id.courseCode);
        prerequisites = findViewById(R.id.prereqs);
        sessionOfferings = findViewById(R.id.sessionOffered);
        FirebaseDatabase.getInstance().getReference().child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    coursesList.add(admincourse.courseCode);
                }
                //selectCourseArray = coursesList.toArray(new String[coursesList.size()]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //get courses from firebase
        //spinner select course to edit
        Spinner spinner = findViewById(R.id.select_course);
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                selectCourseArray = coursesList.toArray(new String[coursesList.size()]);
                return view.performClick();
            }
        });
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, R.layout.edit_course_spinner, selectCourseArray);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        //
        createCourseButton = (Button) findViewById(R.id.save_change_button);
        //prerequisites
        courseButton = findViewById(R.id.prereqs);
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminEditCourseActivity.this, StudentSearchCourse.class));
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminEditCourseActivity.this
                );
                builder.setTitle("Select Sessions");
                builder.setCancelable(false);
                if (setFirebase == false) {
                    courseArray = coursesList.toArray(new String[coursesList.size()]);
                    selectedCourse = new boolean[coursesList.size()];
                    setFirebase = true;
                }
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
                        setTextFunction(courseList, courseArray, courseButton);
                        /*StringBuilder stringBuilder = new StringBuilder();
                        for (int j=0; j<courseList.size(); j++) {
                            stringBuilder.append(courseArray[courseList.get(j)]);
                            if (j != courseList.size()-1) {
                                stringBuilder.append(", ");
                            }
                        }
                        courseButton.setText(stringBuilder.toString());*/
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
        sessionButton = findViewById(R.id.sessionOffered);
        selectedSession = new boolean[sessionArray.length];
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminEditCourseActivity.this
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
                        setTextFunction(sessionList, sessionArray, sessionButton);
                        /*StringBuilder stringBuilder = new StringBuilder();
                        for (int j=0; j<sessionList.size(); j++) {
                            stringBuilder.append(sessionArray[sessionList.get(j)]);
                            if (j != sessionList.size()-1) {
                                stringBuilder.append(", ");
                            }
                        }
                        sessionButton.setText(stringBuilder.toString());*/
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
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), selectCourseArray[i], Toast.LENGTH_LONG).show();
        editCourseCode = selectCourseArray[i];
        FirebaseDatabase.getInstance().getReference().child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    if (editCourseCode == admincourse.courseCode) {
                        editCourseName = admincourse.courseName;
                        editSessions = admincourse.sessionOfferings;
                        editPrereq = admincourse.prerequisites;
                        courseName.setText(editCourseName);
                        courseCode.setText(editCourseCode);
                        String[] editSessionsArray = editSessions.split(", ");
                        String[] editPrereqArray = editPrereq.split(", ");
                        for (int i=0; i<courseArray.length; i++) {
                            for (String current: editPrereqArray) {
                                if (current == courseArray[i]) {
                                    courseList.add(i);
                                    Collections.sort(courseList);
                                }
                            }
                        }
                        for (int i=0; i<3; i++) {
                            for (String current: editSessionsArray) {
                                if (current == sessionArray[i]) {
                                    sessionList.add(i);
                                    Collections.sort(sessionList);
                                }
                            }
                        }
                        setTextFunction(sessionList, sessionArray, sessionButton);
                        setTextFunction(courseList, courseArray, courseButton);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}