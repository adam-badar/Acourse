package com.example.test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.Set;

public class AdminWelcomePage extends AppCompatActivity {
    private Button admin_create_course;
    private Button admin_edit_course;
    private Button admin_delete_course;
    private ListView listView;
    boolean [] selectedCourse;
    boolean [] finalSelectedCourse;
    String [] courseArray;
    String [] finalCourseArray;
    Set<String> tempSet;
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
        setContentView(R.layout.activity_admin_welcome);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        tempSet = sp.getStringSet("courses", null);
        courseArray = tempSet.toArray(new String[tempSet.size()]);
        finalCourseArray = tempSet.toArray(new String[tempSet.size()]);
        selectedCourse = new boolean[tempSet.size()];
        finalSelectedCourse = new boolean[tempSet.size()];
        admin_create_course = (Button) findViewById(R.id.create_course_button);
        admin_edit_course = (Button) findViewById(R.id.edit_course_button);
        admin_delete_course = (Button) findViewById(R.id.courseDeleteBtn);

        //ArrayList<String> coursesList = new ArrayList<>();
        ArrayList<Integer> courseList = new ArrayList<>();
        ArrayList<Integer> finalCourseList = new ArrayList<>();
        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Courses");

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
        });*/


        admin_delete_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminWelcomePage.this
                );
                builder.setTitle("Select Courses to Delete");
                builder.setCancelable(false);
                courseArray = tempSet.toArray(new String[tempSet.size()]);
                selectedCourse = new boolean[courseArray.length];
                finalSelectedCourse = new boolean[courseArray.length];

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
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        int size=0;
                        for (int j = 0; j < courseArray.length; j++) {
                            if(selectedCourse[j] == true) {
                                DatabaseReference del = FirebaseDatabase.getInstance().getReference().child("Courses").child(courseArray[j]);
                                del.removeValue();
                                tempSet.remove(courseArray[j]);
                            }
                        }
                        arrayCopy(finalSelectedCourse, selectedCourse);
                        arrayListCopy(finalCourseList, courseList);
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
                        }
                    }
                });
                builder.show();
            }
            //prerequisites

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
    }
}