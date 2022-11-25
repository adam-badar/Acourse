package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView signup_tag_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        signup_tag_click = (TextView) findViewById(R.id.signup);
        signup_tag_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }




}