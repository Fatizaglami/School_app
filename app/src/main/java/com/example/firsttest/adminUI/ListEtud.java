package com.example.firsttest.adminUI;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttest.R;
import com.example.firsttest.adapter.AdapterEtud;
import com.example.firsttest.adapter.AdapterProf;
import com.example.firsttest.interfaces.RecycleViewOnItemClick;
import com.example.firsttest.models.Etudiant;
import com.example.firsttest.show_item_etud;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListEtud extends AppCompatActivity implements RecycleViewOnItemClick {

    RecyclerView recyclerView;
    ArrayList<Etudiant> etudiantArrayList;
    AdapterEtud myAdater;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    TextView etud_nom,etud_prenom;
    CircleImageView etud_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etud);

        //progress dialog
        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        // dbr= FirebaseDatabase.getInstance().getReference("etudiant");
        etudiantArrayList = new ArrayList<Etudiant>();
        myAdater = new AdapterEtud(ListEtud.this,etudiantArrayList,this);

        recyclerView.setAdapter(myAdater);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getListEtuds();
    }

    private void getListEtuds() {
        etudiantArrayList.clear();

        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        db.collection("etudiant").orderBy("nom", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(task ->{
                    if(task.isSuccessful()){

                        for(QueryDocumentSnapshot dc : task.getResult()) {

                            etudiantArrayList.add(dc.toObject(Etudiant.class));

                        }

                    }else{
                        Log.w(TAG, "Error", task.getException());
                    }

                    myAdater.notifyDataSetChanged();



                    if(progressDialog.isShowing())
                        progressDialog.dismiss();



                });


    }

    @Override
    public void onItemClick(int position) {
        String us = db.collection("etudiant").getId();
        Intent intent=new Intent(ListEtud.this, show_item_etud.class);
        // intent.putExtra("etud_photo",etudiantArrayList.get(position).getPhoto());
        intent.putExtra("etud_nom",etudiantArrayList.get(position).getNom());
        intent.putExtra("etud_prenom",etudiantArrayList.get(position).getPrenom());
        intent.putExtra("etud_tel",etudiantArrayList.get(position).getTel());
        intent.putExtra("etud_email",us);

        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        //to delete an item
        etudiantArrayList.remove(position);
        myAdater.notifyItemRemoved(position);
    }
}