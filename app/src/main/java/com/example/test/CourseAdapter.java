package com.example.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter<CourseListAdapter> {
    private
    Context mContext;
    int mResource;
    public CourseAdapter(Context context, int resource, ArrayList<CourseListAdapter> objects){
        super(context, resource, objects);
        mContext = context;
    }

//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent){
//
//        String name = getItem(position).getCourse_name();
//        String state = getItem(position).getState();
//        String check = getItem(position).getCheck();
//
//        CourseListAdapter courses  = new CourseListAdapter (name, state,check);
//
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        convertView = inflater.inflate(mResource, parent, false);
//
//        TextView Lname = (TextView) convertView.findViewById(R.id.viewCoursesList);
//        TextView Lstate = (TextView) convertView.findViewById(R.id.stateOfCourse);
//        TextView Lcheck = (TextView) convertView.findViewById(R.id.tab2);
//
//        Lname.setText(name);
//        Lstate.setText(name);
//        Lcheck.setText(name);
//        return convertView;
//    }
}
