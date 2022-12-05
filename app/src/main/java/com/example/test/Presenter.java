package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Presenter extends AppCompatActivity implements Contract.Presenter {

    ArrayList<String> courseList;
    Set<String> taskSet;
    Contract.Model model;
    Contract.View view;
    boolean success;
    //private EditText email;
    //private EditText password;
   // SharedPreferences sp;
    public Presenter(Contract.Model model, Contract.View view) {
        this.model = model;
        this.view = view;
    }

    /*
    public Presenter() {
        this.courseList = new ArrayList<String>();
        this.taskSet = new HashSet<>();
        this.model = new Model();
        this.view = new SignInView();
//        this.email = findViewById(R.id.email);
//        this.password = findViewById(R.id.password);
//        this.sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);


    }*/

    public boolean checkIfEmpty(String txt_email, String txt_password) {

        return (TextUtils.isEmpty(txt_email) ||
                TextUtils.isEmpty(txt_password));

    }

    public boolean checkPasswordLength(String txt_password) {

        return ((txt_password.length() < 6));

    }

    public boolean checkEmailFormat(String txt_email) {

        String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        return ((!txt_email.matches(pattern)));

    }

    //public checkUsername
    /*
    public void actualLogin(String txt_email, String txt_password, SignInView context){
        model.mAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    view.createToast(context, "Login successful!");
                }
                else {
                    view.createToast(context, "Login failed");
                }
            }
        });
    }

    private void updateSuccess(boolean value) {
        this.success = value;
    }*/
 //

    /*
    public void createCourseList(DataSnapshot dataSnapshot) {

        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
            this.courseList.add(admincourse.courseCode);
        }
        this.taskSet = new HashSet<>(this.courseList);
    }

    public void updateStudentCourse(DataSnapshot snapshot, String txt_email, SharedPreferences.Editor editor ) {

        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
            FetchUser user = snapshot.getValue(FetchUser.class);
            if (txt_email.equals(user.email)) {
                model.putDataStudent(editor, user);
            }
        }
        editor.commit();

    }

    public void updateAdminCourse(DataSnapshot snapshot, String txt_email, SharedPreferences.Editor editor) {

        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
            FetchUser user = snapshot.getValue(FetchUser.class);
            if (txt_email.equals(user.email)) {
                model.putDataAdmin(editor, user);
            }
        }
        editor.commit();
    }*/



    public boolean isStudentEmail (String email) {
        int ind = email.indexOf("@");
        return email.substring(ind+1, ind+8).equals("student");
    }

    public boolean isAdminEmail (String email) {
        int ind = email.indexOf("@");
        return email.substring(ind+1, ind+6).equals("admin");
    }

    public void checkUsername() {
        String userEmail = view.getUsername();
        String password = view.getPassword();
        if (checkIfEmpty(userEmail, password)) {
            view.createToast(view,"Fields cannot be empty");
        }
        else if (checkEmailFormat(userEmail)) {
            view.createToast(view,"Incorrect email format");
        }
        else if (checkPasswordLength(password)) {
            view.createToast(view, "Password too short");
        }
        else if (!model.isFound(userEmail)) {
            view.createToast(view, "Account doesn't exist");
            System.out.println("after");
        }
        else if (model.isFound(userEmail)) {
            view.createToast(view, "Account exists");
        }


    }


    //public void sendUserToNextActivity() {
        /*email = findViewById(R.id.email);
        String txt_email = email.getText().toString().trim();*/
/*
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", txt_email);

        //Model
        FirebaseDatabase.getInstance().getReference().child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                createCourseList(dataSnapshot);

//move to model
                editor.putStringSet("courses", taskSet);

                if (isStudentEmail(txt_email)) {

                    //move to model
                    FirebaseDatabase.getInstance().getReference().child("Users").child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            updateStudentCourse(dataSnapshot, txt_email, editor);

                            startActivity(new Intent(getApplicationContext(), StudentWelcomePage.class));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else if(isAdminEmail(txt_email)){


                    FirebaseDatabase.getInstance().getReference().child("Users").child("Admins").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            updateAdminCourse(dataSnapshot, txt_email, editor);

                            startActivity(new Intent(getApplicationContext(), AdminWelcomePage.class));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/
    //}



}
