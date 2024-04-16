package com.example.fyp_calisthenics_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    // Creating variables
    private EditText email;
    private EditText password;
    private Button regBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Connecting variables with activity_sign_up xml file and related elements in it
        email = findViewById(R.id.SignUpEmail);
        password = findViewById(R.id.SignUpPassword);
        regBtn = findViewById(R.id.SignUpButton);



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if(TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)){
                    Toast.makeText(SignUp.this, "Email or Password is empty", Toast.LENGTH_SHORT).show();
                }
                else if(userPassword.length() < 6){
                    Toast.makeText(SignUp.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(userEmail, userPassword);
                }
            }
        });
    }
    // Method for registering the user, takes email and password as arguments
    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}