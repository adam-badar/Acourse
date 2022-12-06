package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private Contract.Presenter presenter;


    public void createToast(Contract.View context, String text) {
        Toast.makeText((Context) context, text, Toast.LENGTH_SHORT).show();
    }

    public String getUsername() {
        EditText editTextUser = findViewById(R.id.email);
        return editTextUser.getText().toString();
    }

    public String getPassword() {
        EditText editPass = findViewById(R.id.password);
        return editPass.getText().toString();
    }
    /*
    public void displayMessage(String message) {
        TextView textView = findViewById(R.id.Toasty);
        textView.setText(message);
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }*/

    public void handleClick(View view) {presenter.checkUsername();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in_click = (Button) findViewById(R.id.Signin);
        //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        presenter = new Presenter(new Model(), this);

        sign_in_click.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    handleClick(view);
            }
        });



        //handleClick(this);
    }

    public void sendUserToNextActivity() {

    }

    @Override
    public void sendUserToNextStudentPage() {

    }

    @Override
    public void sendUsertoNextAdminPage() {

    }
}
