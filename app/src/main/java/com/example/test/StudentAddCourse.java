package com.example.test;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentAddCourse extends AppCompatActivity {
    private Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_courses2)
    }
    private ArrayList<String> tickedCourses = new ArrayList<>();
    final ArrayList<String> list = new ArrayList<>();
    final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout., list);
    listView.setAdapter(adapter);
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Courses");
    DatabaseReference referencia = FirebaseDatabase.getInstance().getReference().child("Students");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            list.clear();
            for (String x: tickedCourses) {
                referencia.child(x).setValue.(dataSnapshot.child(x).getValue(Course.class));
            }
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                list.add(snapshot.getValue().toString());
            }
            adapter.notifyDataSetChanged();
        }
    }


}
