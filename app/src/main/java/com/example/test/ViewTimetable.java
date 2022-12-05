package com.example.test;
//
//import static com.example.test.TimelineOps.getAllCourses;
//import static com.example.test.TimelineOps.getCoursesTaken;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import kotlin.contracts.Returns;

public class ViewTimetable extends AppCompatActivity {
    TextView tv;
    ListView lv;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timetable);

        tv = findViewById(R.id.timeTableBar);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArrays();
            }
        });
    }

    public void getArrays() {

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String tempSet2 = sp.getString("courses_taken", null); //String of all courses taken
        String [] coursesTaken = tempSet2.split(",");


        String[] allCourses;
        Set<String> tempSet = sp.getStringSet("courses", null);
        allCourses = tempSet.toArray(new String[tempSet.size()]);

        boolean[] selectedCourse;
        ArrayList<Integer> courseList = new ArrayList<>();
        ArrayList<String> tempArray = new ArrayList<>(); //creates tempArray


//      Returns the list of courses to be displayed on the dialog
        for (String i : allCourses) {//If course exists and is not taken
            if (!Arrays.asList(coursesTaken).contains(i)) tempArray.add(i); //add to temp array;
        }
        String[] courseArray = tempArray.toArray(new String[tempArray.size()]); //make string list

        selectedCourse = new boolean[courseArray.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTimetable.this);

        builder.setTitle("Select Courses");
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

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder courses = new StringBuilder();

                for (int j = 0; j < courseList.size(); j++) {
                    courses.append(courseArray[courseList.get(j)]);

                    if (j != courseList.size() - 1) {
                        courses.append(",");
                    }
                }
                tv.setText(courses.toString());
                lv = findViewById(R.id.timeline_list);
                String [] targets = courses.toString().split(",");
                ArrayList<ArrayList<String>> adj_list = generateCourseList(allCourses, sp);
                ArrayList<String>preReqs = getPrereqs(adj_list, targets);
////                String userId = sp.getString("id", null);
////                removeTakenCourses(preReqs, sp);
//
//                orderPrereqs(preReqs,adj_list);
                ArrayAdapter adapt = new ArrayAdapter(ViewTimetable.this, android.R.layout.simple_expandable_list_item_1, adj_list);
                lv.setAdapter(adapt);
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
                for (int j = 0; j < selectedCourse.length; j++) {
                    selectedCourse[j] = false;
                    courseList.clear();
                    tv.setText("");
                }
            }
        });
        builder.show();

    }

    public ArrayList<ArrayList<String>> generateCourseList(String [] coursesString,
                                                                  SharedPreferences sp) {
        //Creates Adjacency list structure
        ArrayList<ArrayList<String>> to_return = new ArrayList<ArrayList<String>>();
        Set<String> tempSet = sp.getStringSet("courses", null);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Courses");



        for (int i = 0; i < coursesString.length; i++) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(coursesString[i]);

            to_return.add(temp);
        }
        return to_return;
    }

    public ArrayList<String> getPrereqs(ArrayList<ArrayList<String>> am, String[] targets){
        //Creates Queue
        Queue<String> last = new LinkedList<>();
        ArrayList <String> to_return = new ArrayList<>();

        for(int a = 0; a < targets.length; a++){
            String target = targets[a].trim();
//            Toast.makeText(ViewTimetable.this, targets[a], Toast.LENGTH_SHORT).show();
            for (int i = 0; i < am.size(); i++){ //Enters loop
                //Finds target course
                if (am.get(i).get(0).equals(target)){
                    //adds pre-reqs
                    for (int j = 1; j < am.get(i).size();j++){
                        if(!last.contains(am.get(i).get(j).trim())) last.offer(am.get(i).get(j).trim());
                        if(!to_return.contains(am.get(i).get(j).trim()))to_return.add(am.get(i).get(j).trim());
                    }
                }
            } //Ends the original thing
        }
        // enters loop
        while(last.size() != 0){
            for(int i = 0; i < am.size();i++){
                if(am.get(i).get(0).trim().equals(last.peek().trim())){
                    for(int j = 0; j < am.get(i).size();j++){
                        if(!last.contains(am.get(i).get(j).trim())) last.add(am.get(i).get(j).trim());
                        if(!to_return.contains(am.get(i).get(j).trim())) to_return.add(am.get(i).get(j).trim());
                    }
                }
            }
            String t = last.poll();
        }
//        for (String i: targets){
//            if(!to_return.contains(i.trim())) to_return.add(i.trim());
//        }
        return to_return;
    }



    static void removeTakenCourses(ArrayList<String> prereqs, SharedPreferences sp){ //Self-explanatory
        ArrayList<String> coursesTaken = new ArrayList<>();
//        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String userId = sp.getString("id", null);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users").child("Students")
                .child(userId);

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String courseList = snapshot.child("coursesTaken").getValue(String.class);
                String str[] = courseList.split(",");
                for(String i:str) coursesTaken.add(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for(String i: coursesTaken){
            if(prereqs.contains(i.trim())) prereqs.remove(i);
        }
    }

     int weight(String a, ArrayList<ArrayList<String>> master){ //finds the number of prereqs for a course
        String [] temp = {a.trim()};
        return getPrereqs(master, temp).size();
    }

    void orderPrereqs(ArrayList<String>prereqs, ArrayList<ArrayList<String>> master){
        //Bubble sorts prerequisites in terms of course weigth
        LinkedList<String> to_return = new LinkedList<>();
        to_return.add(prereqs.get(0).trim());

        int n = prereqs.size();
        String temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (weight(prereqs.get(j - 1), master) > weight(prereqs.get(j), master)) {
                    //swap elements
                    temp = prereqs.get(j - 1);
                    prereqs.set(j-1, prereqs.get(j));
                    prereqs.set(j, temp);
                }
            }
        }

    }
}
