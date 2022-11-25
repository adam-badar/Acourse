package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class admin_welcome extends AppCompatActivity {

    int auth;
    String firstName;
    String lastName;
    String emailAddress;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_welcome);
        //auth = userServices.getCurrentUserAuth();
    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent login = new Intent(admin_welcome.this, LoginActivity.class);
        startActivity(login);
        Intent tempor = getIntent();
        tempor.putExtra("auth", -1);
        tempor.putExtra("id", -1);
        finish();
    }



    public void deleteAllCourses(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Courses");



        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();
                    }


                }
            }
        });
    }

    public void deleteAllUsers(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");



        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();
                    }
                }
            }
        });

    }


    public void logOut(View view){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }



}