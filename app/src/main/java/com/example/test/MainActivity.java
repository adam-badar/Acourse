package com.example.test;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements Contract.View {

    private TextView signup_tag_click;
    private Button sign_in_click;
    private TextView forgotPassword;
    private EditText email;
    private EditText password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences sp;
    private Presenter presenter;

    public void getUsername() {
        EditText editTextUser = findViewById(R.id.email);
    }

    public void getPassword() {
        EditText editPass = findViewById(R.id.password);
    }

    public void displayMessage(String message) {
        TextView textView = findViewById(R.id.Toasty);
        textView.setText(message);
        //Toast.makeText(, message, Toast.LENGTH_SHORT).show();
    }

    public void handleClick(View view) {presenter.checkUsername();}



    public void sendUserToNextActivity() {

    }

    @Override
    public void sendUserToNextStudentPage() {

    }

    @Override
    public void sendUsertoNextAdminPage() {

    }
}
