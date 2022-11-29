package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentSearchCourse extends AppCompatActivity {

    TextView test_dropdown;
    Dialog dialog;
    ArrayList<String> courses;
    String courseTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_courses2);

        test_dropdown = findViewById(R.id.courseSearchBar);

        courses = new ArrayList<>();

        courses.add("CSCA08");
        courses.add("MATA31");
        courses.add("MATA37");
        courses.add("MATA22");
        courses.add("ENGA01");
        courses.add("CSCB07");
        courses.add("CSCB20");

        test_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                dialog=new Dialog(StudentSearchCourse.this);

                // set custom dialog
                dialog.setContentView(R.layout.search_spinner_dialogue);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter=new ArrayAdapter<>(StudentSearchCourse.this, android.R.layout.simple_list_item_1,courses);

                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        test_dropdown.setText(adapter.getItem(position));
                         courseTitle= adapter.getItem(position);
                        startActivity(new Intent(StudentSearchCourse.this, StudentPopUpMenu.class));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}