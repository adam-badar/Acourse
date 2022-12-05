package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Model implements Contract.Model {

    private TextView signup_tag_click;
    private Button sign_in_click;
    private TextView forgotPassword;
    private EditText email;
    private EditText password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences sp;
    int x = 0;
    //ArrayList<String> courseList = new ArrayList<>();
    //SharedPreferences.Editor editor = sp.edit();
        //editor.putString("email", txt_email);
    /*
    public void isFound(String username) {
        String txt_email = email.getText().toString().trim();
        String txt_password = password.getText().toString().trim();

        //Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(txt_email) ||
                TextUtils.isEmpty(txt_password)) {
            Toast.makeText(SignInActivity, "Empty Credentials", Toast.LENGTH_SHORT).show();
        } else if (txt_password.length() < 6) {
            Toast.makeText(SignInActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
        }  else if (!txt_email.matches(pattern)) {
            Toast.makeText(SignInActivity.this, "Incorrect email format", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    }*/
    public boolean isFound(String emailiano) {
        ArrayList<String> courseList = new ArrayList<>();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", emailiano);
        FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
                    courseList.add(admincourse.courseCode);
                }
                Set<String> taskSet = new HashSet<>(courseList);
                editor.putStringSet("courses", taskSet);*/
                int ind = emailiano.indexOf("@");
                if (emailiano.substring(ind+1, ind+8).equals("student")) {
                    FirebaseDatabase.getInstance().getReference().child("Users").child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                FetchUser user = snapshot.getValue(FetchUser.class);
                                if (emailiano.equals(user.email)) {
                                    /*
                                    editor.putString("courses_taken", user.coursesTaken);
                                    System.out.println("1;"+user.coursesTaken);
                                    editor.putString("id", user.id);
                                    editor.putString("first_name", user.first_name);*/
                                    x++;
                                    break;
                                }
                            }
                            //editor.commit();
                            //startActivity(new Intent(getApplicationContext(), StudentWelcomePage.class));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else if(emailiano.substring(ind+1, ind+6).equals("admin")){
                    FirebaseDatabase.getInstance().getReference().child("Users").child("Admins").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                FetchUser user = snapshot.getValue(FetchUser.class);
                                if (emailiano.equals(user.email)) {
                                    /*
                                    editor.putString("id", user.id);
                                    editor.putString("first_name", user.first_name);*/
                                    x++;
                                }
                            }
                            //editor.commit();
                            //startActivity(new Intent(getApplicationContext(), AdminWelcomePage.class));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else {
                    x--;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        if (x == 0) return false;
        return true;
    }

}
