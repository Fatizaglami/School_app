package com.example.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AjouterProf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_prof);
        // code pour ajouter un professeur avec un id qui n'est pas genere automatiquement
        //le meme code peut etre utilisé pour la maj
        /*
        db.collection("cities").document("ID:email")
        .set(city)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });
         */
    }
}