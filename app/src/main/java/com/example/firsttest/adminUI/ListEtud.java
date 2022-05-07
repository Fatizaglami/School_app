package com.example.firsttest.adminUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firsttest.R;
import com.example.firsttest.models.Etudiant;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListEtud extends AppCompatActivity {
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etud);
        //progress dialog
        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        db =FirebaseFirestore.getInstance();
        CollectionReference collRef =db.collection("etudiant");
        Query query=collRef.orderBy("nom");
        FirestoreRecyclerOptions<Etudiant> options =new FirestoreRecyclerOptions.Builder<Etudiant>()
                .setQuery(query, Etudiant.class).build();
        adapter =new FirestoreRecyclerAdapter<Etudiant,EtudiantVH>(options) {

            @NonNull
            @Override
            public EtudiantVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View layout= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_etud, parent, false);
                return new EtudiantVH(layout);
            }

            @Override
            protected void onBindViewHolder(@NonNull EtudiantVH holder, int position, @NonNull Etudiant model) {
                holder.etud_nom.setText(model.getNom());
                holder.etud_tel.setText(model.getTel());
                holder.etud_prenom.setText(model.getPrenom());
                Glide.with(ListEtud.this)
                        .load(model.getPhoto())
                        .placeholder(R.drawable.profile)
                        .centerCrop()
                        .into(holder.etud_photo);
            }

        };
        recyclerView.setAdapter(adapter);
        if(progressDialog.isShowing())
            progressDialog.dismiss();

            }

            /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_add:
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    class EtudiantVH extends RecyclerView.ViewHolder{
        TextView etud_prenom,etud_tel, etud_nom;
        CircleImageView etud_photo;
        public EtudiantVH(@NonNull View itemView) {
            super(itemView);
            etud_nom = itemView.findViewById(R.id.etud_nom);
            etud_prenom= itemView.findViewById(R.id.etud_prenom);
            etud_tel= itemView.findViewById(R.id.etud_tel);
            etud_photo=itemView.findViewById(R.id.etud_photo);
        }
    }

}