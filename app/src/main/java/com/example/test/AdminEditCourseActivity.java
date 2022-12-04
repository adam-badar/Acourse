package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.HashSet;
import java.util.Set;


public class AdminEditCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   /* Spinner spinner;
    String[] sessions = {"Fall", "Winter", "Summer"};*/
    String [] selectCourseArray;

    //String [] selectCourseArray;//= GetCourses.fetch();//= {"Select Course", "MATA31", "MATA32", "MATA33", "MATA34"};

    private ListView listView;
    private Button saveChangesButton;
    //below is multiple dropdown
    TextView sessionButton;
    boolean [] selectedSession;
    boolean [] finalSelectedSession;

    ArrayList<Integer> sessionList = new ArrayList<>();
    ArrayList<Integer> finalSessionList = new ArrayList<>();
    String [] sessionArray = {"Fall", "Winter", "Summer"};

    TextView courseButton;
    boolean [] selectedCourse;
    boolean [] finalSelectedCourse;
    ArrayList<Integer> courseList = new ArrayList<>();
    ArrayList<Integer> finalCourseList = new ArrayList<>();

   // String [] courseArray = {"CSCA48", "CSCA67", "CSCB36", "MATB41", "STAB52", "MATA31"};
    ArrayList<String> coursesList = new ArrayList<>();
    String [] courseArray;
    boolean setFirebase = false;
    String editCourseName;
    String editCourseCode="";
    String editSessions;
    String editPrereq;
    String[] editPrereqArray;
    String[] editSessionArray;
    Set<String> tempSet;
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
    void clearMenu(ArrayList<Integer> list, ArrayList<Integer> finalList, boolean[] selectedArray, TextView button) {
        for (int i=0; i<selectedArray.length; i++) {
            selectedArray[i] = false;
            list.clear();
            finalList.clear();
            button.setText("");
        }
    }
    void arrayCopy(boolean[] currentArray, boolean[] wantedArray) {
        for (int i=0; i<wantedArray.length; i++) {
            currentArray[i] = wantedArray[i];
        }
    }
    void arrayListCopy(ArrayList<Integer> currentList, ArrayList<Integer> wantedList) {
        currentList.clear();
        if (wantedList == null) return;
        for (int num: wantedList){
            currentList.add(num);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        //home button
        ImageView homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminEditCourseActivity.this, AdminWelcomePage.class));
            }
        });
        //sign out
        ImageView signout = findViewById(R.id.logOutButton);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminEditCourseActivity.this, SignInActivity.class));
                Toast.makeText(AdminEditCourseActivity.this, "Successfully Signed Out", Toast.LENGTH_SHORT).show();

            }
        });
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        tempSet = sp.getStringSet("courses", null);
        courseArray = tempSet.toArray(new String[tempSet.size()]);
        selectedCourse = new boolean[tempSet.size()];
        finalSelectedCourse = new boolean[tempSet.size()];

        courseName = findViewById(R.id.courseName);
        courseCode = findViewById(R.id.courseCode);
        prerequisites = findViewById(R.id.prereqs);
        sessionOfferings = findViewById(R.id.sessionOffered);
        //spinner select course to edit
        Spinner spinner = findViewById(R.id.select_course);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, R.layout.edit_course_spinner, courseArray);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        //
        saveChangesButton = (Button) findViewById(R.id.saveChanges);
        //prerequisites
        courseButton = findViewById(R.id.prereqs);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_courseName = courseName.getText().toString().trim();
                String txt_courseCode = courseCode.getText().toString().trim();
                String txt_prerequisites = prerequisites.getText().toString().trim();
                String txt_sessionOfferings = sessionOfferings.getText().toString().trim();

                if ( TextUtils.isEmpty(txt_courseName) || TextUtils.isEmpty(txt_courseCode) || TextUtils.isEmpty((txt_sessionOfferings))) {
                    Toast.makeText(AdminEditCourseActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                } else if (txt_courseCode.length() != 6) {
                    Toast.makeText(AdminEditCourseActivity.this, "CourseCode too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    AdminCourse course = new AdminCourse(txt_courseName, txt_courseCode, txt_prerequisites, txt_sessionOfferings);
                    if (editCourseCode != txt_courseCode) {
                        //adam add code here
                        FirebaseDatabase.getInstance().getReference().child("Courses").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot sd: snapshot.getChildren()) {
                                    AdminCourse newcourse = sd.getValue(AdminCourse.class);
                                    DatabaseReference prereqs = FirebaseDatabase.getInstance().getReference().child("Courses").child(sd.getKey()).child("prerequisites");
                                    String na = newcourse.getPrerequisites();
                                    if(na.contains(editCourseCode)){
                                        na = na.replaceAll(editCourseCode, txt_courseCode);
                                        prereqs.setValue(na);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        FirebaseDatabase.getInstance().getReference().child("Users").child("Students").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    User user = ds.getValue(User.class);
                                    DatabaseReference again = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(ds.getKey()).child("coursesTaken");
                                    String naya = user.getCoursesTaken();
                                    if(naya.contains(editCourseCode)){
                                        naya = naya.replaceAll(editCourseCode, txt_courseCode);
                                        again.setValue(naya);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        //
                        tempSet.add(editCourseCode);
                        tempSet.remove(txt_courseCode);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putStringSet("courses", new HashSet<>(tempSet));
                        editor.commit();
                        DatabaseReference del = FirebaseDatabase.getInstance().getReference().child("Courses").child(editCourseCode);
                        del.removeValue();
                    }
                    FirebaseDatabase.getInstance().getReference("Courses").child(txt_courseCode).setValue(course);
                    sendUserToNextActivity();
                }
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminEditCourseActivity.this
                );
                builder.setTitle("Select Prerequisites");
                builder.setCancelable(false);
                /*if (setFirebase == false) {
                    courseArray = coursesList.toArray(new String[coursesList.size()]);
                    selectedCourse = new boolean[coursesList.size()];
                    setFirebase = true;
                }*/
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
                        int courseNameIndex = Arrays.asList(courseArray).indexOf(courseCode.getText().toString().trim());
                        if (selectedCourse[courseNameIndex] == true) {
                            selectedCourse[courseNameIndex] = false;
                            courseList.remove(courseNameIndex);
                            Toast.makeText(getApplicationContext(), "Cannot Add Itself", Toast.LENGTH_SHORT).show();
                        }
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
                        arrayCopy(selectedCourse, finalSelectedCourse);
                        arrayListCopy(courseList, finalCourseList);
                        /*for (int j=0; j<selectedCourse.length; j++) {
                            selectedCourse[j] = false;
                            courseList.clear();
                            courseButton.setText("");
                        }*/
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*for (int j=0; j<selectedCourse.length; j++) {
                            selectedCourse[j] = false;
                            courseList.clear();
                            courseButton.setText("");
                        }*/
                        clearMenu(courseList, finalCourseList, selectedCourse, courseButton);
                    }
                });
                builder.show();
            }
            //prerequisites

        });
        //sessions
        sessionButton = findViewById(R.id.sessionOffered);
        selectedSession = new boolean[3];
        finalSelectedSession = new boolean[3];
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
                        arrayCopy(selectedSession, finalSelectedSession);
                        arrayListCopy(sessionList, finalSessionList);
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
                        arrayCopy(selectedSession, finalSelectedSession);
                        arrayListCopy(sessionList, finalSessionList);
                        /*for (int j=0; j<selectedSession.length; j++) {
                            selectedSession[j] = false;
                            sessionList.clear();
                            sessionButton.setText("");
                        }*/
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*for (int j=0; j<selectedSession.length; j++) {
                            selectedSession[j] = false;
                            sessionList.clear();
                            sessionButton.setText("");
                        }*/
                        clearMenu(sessionList, finalSessionList, selectedSession, sessionButton);
                    }
                });
                builder.show();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), courseArray[i], Toast.LENGTH_SHORT).show();
        editCourseCode = courseArray[i];
        clearMenu(sessionList, finalCourseList, selectedSession, sessionButton);
        clearMenu(courseList, finalSessionList, selectedCourse, courseButton);
        FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    if (editCourseCode.equals(admincourse.courseCode)) {
                        editCourseName = admincourse.courseName;
                        editSessions = admincourse.sessionOfferings;
                        editPrereq = admincourse.prerequisites;
                        courseName.setText(editCourseName);
                        courseCode.setText(editCourseCode);
                        if (editSessions != null) {
                            editSessionArray = editSessions.split(", ");
                            System.out.println(Arrays.toString(editSessionArray));
                            for (String current: editSessionArray) {
                                int index = Arrays.asList(sessionArray).indexOf(current);
                                if (index>=0) {
                                    sessionList.add(index);
                                    selectedSession[index] = true;
                                }
                                Collections.sort(sessionList);
                            }
                            setTextFunction(sessionList, sessionArray, sessionButton);
                            arrayCopy(finalSelectedSession, selectedSession);
                            arrayListCopy(finalSessionList, sessionList);
                        }
                        if (editPrereq != null) {
                            editPrereqArray = editPrereq.split(", ");
                            System.out.println(Arrays.toString(editPrereqArray));
                            for (String current: editPrereqArray) {
                                int index = Arrays.asList(courseArray).indexOf(current);
                                System.out.println(index);
                                if (index>=0) {
                                    courseList.add(index);
                                    selectedCourse[index] = true;
                                }
                                Collections.sort(courseList);
                            }
                            setTextFunction(courseList, courseArray, courseButton);
                            arrayCopy(finalSelectedCourse, selectedCourse);
                            arrayListCopy(finalCourseList, courseList);
                        }
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

    private void sendUserToNextActivity() {
        Toast.makeText(AdminEditCourseActivity.this, "Course Edited Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AdminWelcomePage.class));
    }
}