package com.example.firsttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
private EditText inputEmail;
private Button btnReset;
FirebaseAuth auth;
//private ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail=findViewById(R.id.inputEmail);
        inputEmail.setOnClickListener(this);

        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
       // progressbar=findViewById(R.id.progressBar);
       // progressbar.setOnClickListener(this);

        auth=FirebaseAuth.getInstance();
       


    }

    @Override
    public void onClick(View view) {
        resetPassword();
    }

    private void resetPassword() {
        String email=inputEmail.getText().toString().trim();

        if(email.isEmpty()){
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Please provide valid email");
            inputEmail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email to reset your password", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPassword.this,"Try again! Something wrong happened", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}