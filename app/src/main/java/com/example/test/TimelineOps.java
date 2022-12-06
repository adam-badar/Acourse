package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TimelineOps {

//    static public ArrayList<String> getAllCourses(){
//        ArrayList<String> allCourses = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Courses");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    allCourses.add(ds.child("courseCode").getValue(String.class).trim());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return allCourses;
//    }
//
//    static public ArrayList<String> getCoursesTaken(String userId){
//        ArrayList<String> takenCourses = new ArrayList<>();
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("Students").child(userId);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Overridegmgm
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String taken = snapshot.child("coursesTaken").getValue(String.class);
//                String str[] = taken.split(",");
//                for(String i:str) takenCourses.add(i.trim());
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return takenCourses;
//    }


    static public ArrayList<String> getPrereqs(ArrayList<ArrayList<String>> am, String[] targets){
        //Creates Queue
        Queue<String> last = new LinkedList<>();
        ArrayList <String> to_return = new ArrayList<>();

        for(int a = 0; a < targets.length; a++){
            String target = targets[a].trim();
            System.out.println("Course is "+target);
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
        for (String i: targets){
            if(!to_return.contains(i.trim())) to_return.add(i.trim());
        }
        return to_return;
    }

    static void removeTakenCourses(ArrayList<String> prereqs, User user){ //Self-explanatory
        String [] takenCourses = user.coursesTaken.split(",");
        for(String i: takenCourses){
            if(prereqs.contains(i.trim())) prereqs.remove(i);
        }
    }

    static int weight(String a, ArrayList<ArrayList<String>> master){ //finds the number of prereqs for a course
        String [] temp = {a.trim()};
        return getPrereqs(master, temp).size();
    }

    static void orderPrereqs(ArrayList<String>prereqs, ArrayList<ArrayList<String>> master){
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

    static public AdminCourse turnToCourse(DatabaseReference rootRef, String coursename) {
        //turns the final list of prereqs required into a course class from firebase
        rootRef = FirebaseDatabase.getInstance().getReference("Courses").child(coursename);
        final AdminCourse[] now = {new AdminCourse()};
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("courseName").getValue(String.class);
                String code = snapshot.child("courseCode").getValue(String.class);
                String pre = snapshot.child("prerequisites").getValue(String.class);
                String sessions = snapshot.child("sessionOfferings").getValue(String.class);

                now[0] = new AdminCourse(name, code, pre, sessions);
            }

            //
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
        return now[0];
    }



}
