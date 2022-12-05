package com.example.test;


import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;

public class SignInView extends AppCompatActivity {


    private TextView signup_tag_click;
    private Button sign_in_click;
    private TextView forgotPassword;


    private EditText email;
    private EditText password;

    String txt_email;
    String txt_password;


    public SignInView() {

    }

    public void createToast(SignInActivity context, String text) {

            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }

}
