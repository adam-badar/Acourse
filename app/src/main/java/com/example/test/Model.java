package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

public class Model extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public Model() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();

    }

    public void putDataStudent (SharedPreferences.Editor editor, FetchUser user) {
        editor.putString("courses_taken", user.coursesTaken);
        System.out.println("1;"+user.coursesTaken);
        editor.putString("id", user.id);
        editor.putString("first_name", user.first_name);

    }

    public void putDataAdmin (SharedPreferences.Editor editor, FetchUser user) {
        editor.putString("id", user.id);
        editor.putString("first_name", user.first_name);
    }
}
