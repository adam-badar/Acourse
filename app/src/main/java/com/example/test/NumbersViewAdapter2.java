package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class NumbersViewAdapter2 extends ArrayAdapter<AdminCourse> implements Filterable {

    // invoke the suitable constructor of the ArrayAdapter class
    public NumbersViewAdapter2(@NonNull Context context, ArrayList<AdminCourse> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_list_view_search, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        AdminCourse currentNumberPosition = getItem(position);

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


        return currentItemView;
    }
}

