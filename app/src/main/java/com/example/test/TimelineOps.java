package com.example.test;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TimelineOps {

    static public ArrayList<ArrayList<String>> generateCourseList(DatabaseReference ref){
        //Creates Adjacency list structure
        ArrayList<ArrayList<String>> to_return = new ArrayList<ArrayList<String>>();
        ref = FirebaseDatabase.getInstance().getReference("Courses"); // Ref to firebase root
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> temp = new ArrayList<>(); //creates inner list
                for (DataSnapshot ds : snapshot.getChildren()) {
                    temp = new ArrayList<>();
                    String[] temp_array = ds.getValue(String.class).split(",");//splits prerequisites
                    for (String i : temp_array) temp.add(i.trim()); //add to
                }
                to_return.add(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return to_return;
    }

    static ArrayList<String> getPrereqs(ArrayList<ArrayList<String>> am, String[] targets){
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
            System.out.println("Head of queue:");
            for(int i = 0; i < am.size();i++){
                if(am.get(i).get(0).trim().equals(last.peek().trim())){
                    for(int j = 0; j < am.get(i).size();j++){
                        if(!last.contains(am.get(i).get(j).trim())) last.add(am.get(i).get(j).trim());
                        if(!to_return.contains(am.get(i).get(j).trim())) to_return.add(am.get(i).get(j).trim());
                    }
                }
            }
            System.out.println("Removed "+"\n");
            String t = last.poll();
        }
        for (String i: targets){
            if(!to_return.contains(i.trim())) to_return.add(i.trim());
        }
        return to_return;
    }

    static void removeTakenCourses(ArrayList<String> prereqs, User user){
        String [] takenCourses = user.coursesTaken.split(",");
        for(String i: takenCourses){
            if(prereqs.contains(i.trim())) prereqs.remove(i);
        }
    }

    static int weight(String a, ArrayList<ArrayList<String>> master){
        String [] temp = {a.trim()};
        return getPrereqs(master, temp).size();
    }

    static void orderPrereqs(ArrayList<String>prereqs, ArrayList<ArrayList<String>> master){
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
