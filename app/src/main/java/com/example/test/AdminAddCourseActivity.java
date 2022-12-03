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
import android.widget.ImageView;
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
    private ImageView signout;
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
        createCourseButton = (Button) findViewById(R.id.createCourseBtn);
        signout = findViewById(R.id.logOutButton);
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
                                stringBuilder.append(",");
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
                                stringBuilder.append(",");
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
                    Toast.makeText(AdminAddCourseActivity.this, "Course Code Must Be Length 6", Toast.LENGTH_SHORT).show();
                }
                else if (tempSet.contains(txt_courseCode)) {
                    Toast.makeText(AdminAddCourseActivity.this, "Course Code Already Exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    AdminCourse course = new AdminCourse(txt_courseName, txt_courseCode, txt_prerequisites, txt_sessionOfferings);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Courses");
                    reference.child(txt_courseCode).setValue(course);
                    sendUserToNextActivity();
                }
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAddCourseActivity.this, SignInActivity.class));
                Toast.makeText(AdminAddCourseActivity.this, "Successfully Signed Out", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void sendUserToNextActivity() {
        Toast.makeText(AdminAddCourseActivity.this, "Course Created Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AdminWelcomePage.class));
    }

}