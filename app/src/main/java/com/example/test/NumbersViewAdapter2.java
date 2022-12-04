package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NumbersViewAdapter2 extends ArrayAdapter<AdminCourse> {
    public static AdminCourse courseClicked;
    public static Button pastCourseButton;
    public static Button timelineButton;
    public static Context context;
    public ArrayList<String> coursesTakenList;
    public String coursesTaken;
    // invoke the suitable constructor of the ArrayAdapter class
    public NumbersViewAdapter2(@NonNull Context context, ArrayList<AdminCourse> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SharedPreferences sp = getContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String studentID = sp.getString("id",null);

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_list_view_search, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        AdminCourse currentNumberPosition = getItem(position);
        courseClicked = currentNumberPosition;
        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        System.out.println("code:"+currentNumberPosition.courseCode);
        textView1.setText(currentNumberPosition.courseCode);

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        System.out.println("name:"+currentNumberPosition.courseName);
        textView2.setText("Course Name: "+currentNumberPosition.courseName);

        // then return the recyclable view
        TextView textView3 = currentItemView.findViewById(R.id.textView3);
        System.out.println("sessions:"+currentNumberPosition.sessionOfferings);
        textView3.setText("Sessions Offered: "+currentNumberPosition.sessionOfferings);

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView4 = currentItemView.findViewById(R.id.textView4);
        System.out.println("prereq:"+currentNumberPosition.prerequisites);
        textView4.setText("Prerequisites: "+currentNumberPosition.prerequisites);
        //String coursesTaken = sp.getString("courses_taken", null);
        pastCourseButton = currentItemView.findViewById(R.id.pastCourseButton);
        timelineButton = currentItemView.findViewById(R.id.timelineButton);
        coursesTaken = sp.getString("courses_taken", null);
        coursesTakenList = new ArrayList<String>(Arrays.asList(coursesTaken.split(",")));
        pastCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(coursesTakenList);
                ArrayList<String> prereqList = new ArrayList<String>(Arrays.asList(currentNumberPosition.prerequisites.split(",")));
                if (coursesTakenList.contains(currentNumberPosition.courseCode)) {
                    Toast.makeText(getContext(), "Course Already Taken" , Toast.LENGTH_SHORT).show();
                }
                else if (coursesTakenList.containsAll(prereqList)) {
                    coursesTakenList.add(currentNumberPosition.courseCode);
                    coursesTakenList.removeAll(Arrays.asList("", null));
                    String newCoursesTaken = String.join(",", coursesTakenList);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("courses_taken", newCoursesTaken);
                    editor.commit();
                    FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(studentID).child("coursesTaken").setValue(newCoursesTaken);
                    Toast.makeText(getContext(), "Course Added To Past Courses" , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Does Not Have Prerequisites" , Toast.LENGTH_SHORT).show();
                }
            }
        });
        return currentItemView;
    }
}

