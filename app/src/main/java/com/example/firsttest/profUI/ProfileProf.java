package com.example.firsttest.profUI;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.firsttest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProfileProf extends AppCompatActivity implements View.OnClickListener {
    private RoundedImageView profileImageView;
    private Button editButton;
    AppCompatImageView backImage;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;
    private String email;
    private String userid;
    private TextView textviewnomPrenom;
    private TextView editNom, editPrenom, editEmail, editPhoneNumber, editDepartement;
    private final String TAG = this.getClass().getName().toUpperCase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_prof);

        backImage = findViewById(R.id.imageBack);
        backImage.setOnClickListener(this);


        ////int
        textviewnomPrenom = findViewById(R.id.nom_prenom);
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        editEmail = findViewById(R.id.editEmail);
        editDepartement = findViewById(R.id.editDepartement);
        editPhoneNumber = findViewById(R.id.editphone);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Photo");
        Log.v("USERID", databaseReference.getKey());

        profileImageView = findViewById(R.id.imageProfile);
        // Read from the database
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {


            String nom, prenom, email, tel, departement;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    nom = dataSnapshot.child("nom").getValue(String.class);
                    prenom = dataSnapshot.child("prenom").getValue(String.class);
                    email = dataSnapshot.child("email").getValue(String.class);
                    tel = dataSnapshot.child("tel").getValue(String.class);
                    //   departement = dataSnapshot.child("departement").getValue(String.class);


                }
                String nom_prenom = nom.concat(" " + prenom);
                editNom.setText(nom);
                editPrenom.setText(prenom);
                editEmail.setText(email);
                // editDepartement.setText(departement);
                editPhoneNumber.setText(tel);
                textviewnomPrenom.setText(nom_prenom);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, DashboardProf.class));
    }
}