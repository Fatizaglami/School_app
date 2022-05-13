package com.example.firsttest.adminUI;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttest.R;
import com.example.firsttest.adapter.AdapterProf;
import com.example.firsttest.interfaces.RecycleViewOnItemClick;
import com.example.firsttest.models.Professeur;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListProfUpd extends AppCompatActivity implements RecycleViewOnItemClick, View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<Professeur> professeurArrayList;
    AdapterProf myAdater;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    TextView prof_nom,prof_dep;
    CircleImageView prof_photo;
    Button btnAdd,btnUpdate;


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
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);




        db = FirebaseFirestore.getInstance();
       // dbr= FirebaseDatabase.getInstance().getReference("professeur");
        professeurArrayList = new ArrayList<Professeur>();
        myAdater = new AdapterProf(ListProfUpd.this,professeurArrayList,this);

        recyclerView.setAdapter(myAdater);
       // getListProfs();


    }


    @Override
    protected void onResume() {
        super.onResume();
        getListProfs();
    }

     public void getListProfs() {
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

    private void refresh() {
        getListProfs();
    }

    @Override
    public void onItemClick(int position) {
       String us = db.collection("professeur").getId();
        Intent intent=new Intent(ListProfUpd.this, show_item_prof.class);
       // intent.putExtra("prof_photo",professeurArrayList.get(position).getPhoto());
        intent.putExtra("prof_nom",professeurArrayList.get(position).getNom());
        intent.putExtra("prof_dep",professeurArrayList.get(position).getDepartement());
        intent.putExtra("prof_prenom",professeurArrayList.get(position).getPrenom());
        intent.putExtra("prof_tel",professeurArrayList.get(position).getTel());
        intent.putExtra("prof_email",us);

        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
     //to delete an item
        professeurArrayList.remove(position);
        myAdater.notifyItemRemoved(position);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnAdd){
            startActivity(new Intent(this, AjouterProf.class));
            /*Intent intent=new Intent(ListProfUpd.this, AjouterProf.class);
            startActivity(intent);*/


        }

    }
}