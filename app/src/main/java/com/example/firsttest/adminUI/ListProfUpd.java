package com.example.firsttest.adminUI;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttest.R;
import com.example.firsttest.adapter.AdapterProf;
import com.example.firsttest.models.Professeur;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListProfUpd extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Professeur> professeurArrayList;
    AdapterProf myAdater;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prof_upd);

        //progress dialog
        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
       // dbr= FirebaseDatabase.getInstance().getReference("professeur");
        professeurArrayList = new ArrayList<Professeur>();
        myAdater = new AdapterProf(ListProfUpd.this,professeurArrayList);

        recyclerView.setAdapter(myAdater);
       // getListProfs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListProfs();
    }

    private void getListProfs() {
        professeurArrayList.clear();

        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        db.collection("professeur").orderBy("nom", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(task ->{
                    if(task.isSuccessful()){

                        for(QueryDocumentSnapshot dc : task.getResult()) {

                            professeurArrayList.add(dc.toObject(Professeur.class));

                        }

                            }else{
                            Log.w(TAG, "Error", task.getException());
                        }

                            myAdater.notifyDataSetChanged();



                            if(progressDialog.isShowing())
                                progressDialog.dismiss();



                });


    }

}