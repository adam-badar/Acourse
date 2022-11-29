package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentPopUpMenu extends AppCompatActivity {

    Button courselist;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7), (int)(height*.6));
        TextView field = (TextView) findViewById(R.id.popupCourseTitle);
        field.setText("AddText");

        courselist = findViewById(R.id.addButton);
        courselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(StudentPopUpMenu.this, CourseListPopUp.class)));
            }
        });
    }
}
