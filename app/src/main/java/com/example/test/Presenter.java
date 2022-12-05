package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Presenter extends AppCompatActivity {

    ArrayList<String> courseList;
    Set<String> taskSet;
    Model model;
    SignInView view;
    boolean success;


    public Presenter() {
        this.courseList = new ArrayList<String>();
        this.taskSet = new HashSet<>();
        this.model = new Model();
        this.view = new SignInView();

    }

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

    public void actualLogin(String txt_email, String txt_password, SignInActivity context){
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
    }

    public void createCourseList(DataSnapshot dataSnapshot) {

        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            AdminCourse admincourse = snapshot.getValue(AdminCourse.class);
            this.courseList.add(admincourse.courseCode);
        }
        this.taskSet = new HashSet<>(this.courseList);
    }

    public void updateStudentCourse(DataSnapshot snapshot, String txt_email,SharedPreferences.Editor editor ) {

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
    }



    public boolean isStudentEmail (String email) {
        int ind = email.indexOf("@");
        return email.substring(ind+1, ind+8).equals("student");
    }

    public boolean isAdminEmail (String email) {
        int ind = email.indexOf("@");
        return email.substring(ind+1, ind+6).equals("admin");
    }



}
