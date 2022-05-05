package com.example.firsttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firsttest.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private RadioGroup rgProfiles;
    private EditText editTextNom,editTextPrenom,editTextEmail,editTextMobile,editTextPassword;
    private TextView editLogin;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mAuth = FirebaseAuth.getInstance();

        editTextNom= findViewById(R.id.editTextName);
        editTextNom.setOnClickListener(this);


        editTextPrenom=findViewById(R.id.editTextPrenom) ;
        editTextPrenom.setOnClickListener(this);


        editTextEmail= findViewById(R.id.editTextEmail);
        editTextEmail.setOnClickListener(this);

        editTextPassword= findViewById(R.id.editTextPassword);
        editTextPassword.setOnClickListener(this);

        editTextMobile= findViewById(R.id.editTextMobile);
        editTextMobile.setOnClickListener(this);

        editLogin= findViewById(R.id.editTextLogin);
        editLogin.setOnClickListener(this);
        btnRegister= findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnRegister:
                Register();break;
            case R.id.editTextLogin:
                startActivity(new Intent(this,Login.class));break;
        }

    }
    private void Register(){
        String nom =editTextNom.getText().toString().trim();
        String prenom =editTextPrenom.getText().toString().trim();
        String email =editTextEmail.getText().toString().trim();
        String phoneNumber=editTextMobile.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();
        if(nom.isEmpty()){
            editTextNom.setError("Second name is required");
            editTextNom.requestFocus();
            return;
        }
        if(prenom.isEmpty()){
            editTextPrenom.setError(" First name is required");
            editTextPrenom.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(phoneNumber.isEmpty()){
            editTextMobile.setError("numberPhone is required");
            editTextMobile.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("ok");
                    User user= new User(nom,prenom,email,phoneNumber,password);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                System.out.println("okkkkkkk");
                                Toast.makeText(Register.this, "User has been registred sucessfully", Toast.LENGTH_LONG).show();


                            }else{
                                System.out.println("errrrrrrrrr");
                                Toast.makeText(Register.this,"Failed to register ! try again!", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }else{
                    System.out.println("hhhhhhhhh");
                    Toast.makeText(Register.this,"Failed to register ! try again!", Toast.LENGTH_LONG).show();


                }
            }
        });



    }
}

