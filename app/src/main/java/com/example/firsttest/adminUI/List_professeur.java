package com.example.firsttest.adminUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.firsttest.Professeur_activity;
import com.example.firsttest.R;
import com.example.firsttest.models.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;

public class List_professeur extends AppCompatActivity {
    FloatingActionButton FabAdd;
    FirebaseFirestore db;
    LinkedList<Professeur> profs;
    //RecyclerView.LayoutManager layoutManager;

    EditText search;
    FrameLayout frame_layout;
    ListView list_view;
    public ProgressDialog mProgressDialog;

    //RecyclerView myrecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_prpfesseur);
        FabAdd=findViewById(R.id.add_prof);
        list_view =findViewById(R.id.list_prof);

        profs= new LinkedList<Professeur>();

        db = FirebaseFirestore.getInstance();



    }
    protected void onResume() {

        super.onResume();
        getAllProfesseurs();
    }
    protected void onStart() {

        super.onStart();

    }

    void getAllProfesseurs() {
        showProgressDialog();
        LinkedList<Professeur> profs= new LinkedList<Professeur>();
        db.collection("professeur")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Professeur prof = new Professeur(document.getString("Nom"),document.getString("Prenom").toString(),document.getString("Departement"),document.getString("Tel"),document.getString("Photo") );
                                profs.add(prof);
                            }

                        }
                        else
                            System.out.println("Erreur!!!!!");
                        ArrayList<String> noms_prof = new ArrayList<String>();
                        for (Professeur prof: profs){
                            noms_prof.add(prof.getNom());
                        }
                        ArrayAdapter<String> arrayAdapter = new
                                ArrayAdapter<String>(List_professeur.this,android.R.layout.simple_list_item_1,noms_prof );
                        list_view.setAdapter(arrayAdapter);
                        hideProgressDialog();
                    }



                });
        System.out.println("La liste des profs :"+profs);
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }




}