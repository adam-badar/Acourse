package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;


public class AdminAddCourseActivity extends AppCompatActivity {
   /* Spinner spinner;
    String[] sessions = {"Fall", "Winter", "Summer"};*/

    private Button createCourseButton;
    //below is multiple dropdown
    TextView sessionButton;
    TextView courseButton;
    boolean [] selectedSession;
    boolean [] selectedCourse;
    ArrayList<Integer> sessionList = new ArrayList<>();
    String [] sessionArray = {"Fall", "Winter", "Summer"};
    ArrayList<Integer> courseList = new ArrayList<>();
    String [] courseArray = {"None", "CSCA48", "CSCA67", "CSCB36", "MATB41", "STAB52", "MATA31"};

    private EditText courseName;
    private EditText courseCode;
    private TextView prerequisites;
    private TextView sessionOfferings;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference reference;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        /*spinner = findViewById(R.id.session_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_spinner_item, sessions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddCourseActivity.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        createCourseButton = (Button) findViewById(R.id.createCourseBtn);
        //prerequisites
        courseButton = findViewById(R.id.prereqs);
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
        sessionButton = findViewById(R.id.sessionOffered);
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

        courseName = findViewById(R.id.courseName);
        courseCode = findViewById(R.id.courseCode);
        prerequisites = findViewById(R.id.prereqs);
        sessionOfferings = findViewById(R.id.sessionOffered);


        auth = FirebaseAuth.getInstance();
        //progressBar =
        String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_courseName = courseName.getText().toString().trim();
                String txt_courseCode = courseCode.getText().toString().trim();
                String txt_prerequisites = prerequisites.getText().toString().trim();
                String txt_sessionOfferings = sessionOfferings.getText().toString().trim();

                if ( TextUtils.isEmpty(txt_courseName) || TextUtils.isEmpty(txt_courseCode) || TextUtils.isEmpty((txt_sessionOfferings))) {
                    Toast.makeText(AdminAddCourseActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                } else if (txt_courseCode.length() != 6) {
                    Toast.makeText(AdminAddCourseActivity.this, "CourseCode too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    Course course = new Course(txt_courseName, txt_courseCode, prerequisites, txt_sessionOfferings);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Courses");
                    reference.child(txt_courseCode).setValue(course);
                    //registerUser(txt_email, txt_password);
                }
            }
        });

    }

    private void CreateCourse(String courseName, String courseCode ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(AdminAddCourseActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AdminAddCourseActivity.this, "Course creation successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AdminWelcomePage.class));
                }
                else {
                    Toast.makeText(AdminAddCourseActivity.this, "Creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}