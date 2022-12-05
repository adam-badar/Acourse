package com.example.test;

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

public class Presenter implements Contract.Presenter {

    private Model model;
    private MainActivity view;
    private TextView signup_tag_click;
    private Button sign_in_click;
    private TextView forgotPassword;
    private EditText email;
    private EditText password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences sp;

    @Override
    public void checkUsername() {

    }
    /*
    public void checkUsername() {
        String txt_email = email.getText().toString().trim();
        String txt_password = password.getText().toString().trim();

        //Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(txt_email) ||
                TextUtils.isEmpty(txt_password)) {
            Toast.makeText(SignInActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
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
                        //sendUserToNextActivity();
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    }*/
}
