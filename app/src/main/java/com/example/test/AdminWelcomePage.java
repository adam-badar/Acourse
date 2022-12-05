package com.example.test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AdminWelcomePage extends AppCompatActivity {
    private Button admin_create_course;
    private Button admin_edit_course;
    private Button admin_delete_course;
    private Button signout;
    private Button view_courses;
    boolean [] selectedCourse;
    Set<String> tempSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        tempSet = sp.getStringSet("courses", null);
        TextView welcomeText = findViewById(R.id.welcome);
        String name = sp.getString("first_name",null);
        welcomeText.setText("WELCOME\n"+name);
        SharedPreferences.Editor editor = sp.edit();

        admin_create_course = (Button) findViewById(R.id.create_course_button);
        admin_edit_course = (Button) findViewById(R.id.edit_course_button);
        admin_delete_course = (Button) findViewById(R.id.courseDeleteBtn);
        signout = (Button) findViewById(R.id.logOutButton);
        view_courses = (Button) findViewById(R.id.view_courses);

        ArrayList<String> coursesList = new ArrayList<>();
        ArrayList<Integer> courseList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Courses");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    coursesList.add(admincourse.courseCode);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        admin_delete_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminWelcomePage.this
                );
                builder.setTitle("Select Courses to Delete");
                builder.setCancelable(false);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        coursesList.clear();
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                            coursesList.add(admincourse.courseCode);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                String [] courseArray = new String[coursesList.size()];
                courseArray = coursesList.toArray(courseArray);
                selectedCourse = new boolean[courseArray.length];
                String[] finalCourseArray = courseArray;

                builder.setMultiChoiceItems(finalCourseArray, selectedCourse, new DialogInterface.OnMultiChoiceClickListener() {
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
                String[] finalCourseArray1 = courseArray;

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < finalCourseArray1.length; j++) {
                            tempSet = new HashSet<>();
                            tempSet = sp.getStringSet("courses", null);
                            SharedPreferences.Editor editor = sp.edit();
                            if(selectedCourse[j] == true) {
                                DatabaseReference del = FirebaseDatabase.getInstance().getReference().child("Courses").child(finalCourseArray1[j]);
                                del.removeValue();
                                tempSet.remove(finalCourseArray1[j]);
                                editor.putStringSet("courses", new HashSet<>(tempSet));
                                editor.commit();
                                int finalJ1 = j;
                                FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot sd: snapshot.getChildren()) {
                                            AdminCourse newcourse = sd.getValue(AdminCourse.class);
                                            DatabaseReference prereqs = FirebaseDatabase.getInstance().getReference().child("Courses").child(sd.getKey()).child("prerequisites");
                                            String na = newcourse.getPrerequisites();
                                            if(na.contains("," + finalCourseArray1[finalJ1])){
                                                na = na.replaceAll("," + finalCourseArray1[finalJ1], "");
                                                prereqs.setValue(na);
                                            }
                                            else if (na.contains(finalCourseArray1[finalJ1] + ",")){
                                                na = na.replaceAll(finalCourseArray1[finalJ1] + ",", "");
                                                prereqs.setValue(na);
                                            }
                                            else if (na.contains(finalCourseArray1[finalJ1])){
                                                na = na.replaceAll(finalCourseArray1[finalJ1], "");
                                                prereqs.setValue(na);
                                            }
                                            else{

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                                int finalJ = j;
                                FirebaseDatabase.getInstance().getReference().child("Users").child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot ds: snapshot.getChildren()){
                                            User user = ds.getValue(User.class);
                                            DatabaseReference again = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(ds.getKey()).child("coursesTaken");
                                            String naya = user.getCoursesTaken();
                                            if(naya.contains("," + finalCourseArray1[finalJ])){
                                                naya = naya.replaceAll("," + finalCourseArray1[finalJ], "");
                                                again.setValue(naya);
                                            }
                                            else if (naya.contains(finalCourseArray1[finalJ] + ",")){
                                                naya = naya.replaceAll(finalCourseArray1[finalJ] + ",", "");
                                                again.setValue(naya);
                                            }
                                            else{
                                                naya = naya.replaceAll(finalCourseArray1[finalJ], "");
                                                again.setValue(naya);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
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
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
            //prerequisites

        });
        view_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminWelcomePage.this, AdminSearchCourse.class));
            }
        });
        admin_create_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminWelcomePage.this, AdminAddCourseActivity.class));
            }
        });
        admin_edit_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminWelcomePage.this, AdminEditCourseActivity.class));
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminWelcomePage.this, SignInActivity.class));
                Toast.makeText(AdminWelcomePage.this, "Successfully Signed Out", Toast.LENGTH_SHORT).show();

            }
        });


    }
}