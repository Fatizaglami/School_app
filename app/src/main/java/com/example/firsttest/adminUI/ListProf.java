package com.example.firsttest.adminUI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firsttest.R;
import com.example.firsttest.models.Professeur;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListProf extends AppCompatActivity {
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    RecyclerView recyclerView;
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
        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        db =FirebaseFirestore.getInstance();
        CollectionReference collRef =db.collection("professeur");
        Query query=collRef.orderBy("nom");
        FirestoreRecyclerOptions<Professeur> options =new FirestoreRecyclerOptions.Builder<Professeur>()
                .setQuery(query, Professeur.class).build();
        adapter =new FirestoreRecyclerAdapter<Professeur, ListProf.ProfVH>(options) {

            @NonNull
            @Override
            public ListProf.ProfVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View layout= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item, parent, false);
                return new ListProf.ProfVH(layout);
            }

            @Override
            protected void onBindViewHolder(@NonNull ListProf.ProfVH holder, int position, @NonNull Professeur model) {
                holder.prof_nom.setText(model.getNom());
                holder.prof_dep.setText(model.getTel());
                holder.prof_prenom.setText(model.getPrenom());
                Glide.with(ListProf.this)
                        .load(model.getPhoto())
                        .placeholder(R.drawable.profile)
                        .centerCrop()
                        .into(holder.prof_photo);
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

    class ProfVH extends RecyclerView.ViewHolder{
        TextView prof_prenom,prof_dep, prof_nom;
        CircleImageView prof_photo;
        public ProfVH(@NonNull View itemView) {
            super(itemView);
            prof_nom = itemView.findViewById(R.id.prof_nom);
            prof_prenom= itemView.findViewById(R.id.prof_prenom);
            prof_dep= itemView.findViewById(R.id.prof_dep);
            prof_photo=itemView.findViewById(R.id.prof_photo);
        }
    }}
