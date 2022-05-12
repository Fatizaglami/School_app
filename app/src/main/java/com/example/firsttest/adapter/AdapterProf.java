package com.example.firsttest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;


import com.example.firsttest.DashboardProf;
import com.example.firsttest.ProfileProf;
import com.example.firsttest.dataAccess.MyAppGlideModule;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firsttest.R;
import com.example.firsttest.adminUI.ListProfUpd;
import com.example.firsttest.interfaces.RecycleViewOnItemClick;
import com.example.firsttest.models.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.example.firsttest.adminUI.ListProfUpd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProf extends RecyclerView.Adapter<AdapterProf.myViewHolder> {

   Context context;
   ArrayList<Professeur> professeurArrayList;
  static RecycleViewOnItemClick recycleViewOnItemClick;
    //EditText editnom,editprenom,editdep,editemail,edittel;
    FirebaseFirestore db;


  /*  public AdapterProf(Context context, ArrayList<Professeur> professeurArrayList) {
        this.context = context;
        this.professeurArrayList = professeurArrayList;
    }*/

    public AdapterProf(Context context, ArrayList<Professeur> professeurArrayList, RecycleViewOnItemClick recycleViewOnItemClick) {
        this.context = context;
        this.professeurArrayList = professeurArrayList;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    @NonNull
    @Override
    public AdapterProf.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new AdapterProf.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProf.myViewHolder holder, @SuppressLint("RecyclerView") int position) {


      Professeur prof = professeurArrayList.get(position);

      holder.prof_dep.setText("Departement"+" "+prof.getDepartement());

        holder.prof_nom.setText("Pr."+" "+prof.getNom()+" "+prof.getPrenom());
       // holder.prof_prenom.setText(prof.getPrenom());
       // holder.prof_dep.setText(prof.getDepartement());
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(professeurArrayList.get(position).getPhoto());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)


        Glide.with(context)
                .load(storageReference)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.prof_photo);
//EDIT PROF
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.prof_photo.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_prof))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final CircleImageView editImage=myview.findViewById(R.id.editphoto);
                final EditText editNom=myview.findViewById(R.id.editnom);
                final EditText editPrenom=myview.findViewById(R.id.editprenom);
                final EditText editDep=myview.findViewById(R.id.editdep);
                final EditText editTel=myview.findViewById(R.id.edittel);
                Button update=myview.findViewById(R.id.btnUpdate);


                editNom.setText(prof.getNom());
                editDep.setText(prof.getDepartement());
                editPrenom.setText(prof.getPrenom());
                editTel.setText(prof.getTel());

                StorageReference storage= FirebaseStorage.getInstance().getReference(professeurArrayList.get(position).getPhoto());

                //editImage.setImageResource(prof.getPhoto());
                Glide.with(context)
                        .load(storage)
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .into(editImage);

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("nom",editNom.getText().toString());
                        map.put("prenom",editPrenom.getText().toString());
                        map.put("tel",editTel.getText().toString());
                        map.put("departement",editDep.getText().toString());

                        String nom =editNom.getText().toString();
                        //System.out.println(nom);

                      // Query us = db.collection("professeur").whereEqualTo("nom",nom);

                      //DocumentReference dbref=FirebaseFirestore.getInstance().document("us");
                        //System.out.println(us);

                        db=FirebaseFirestore.getInstance();

                        db.collection("professeur").whereEqualTo("nom",nom)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if(task.isSuccessful() && !task.getResult().isEmpty()){
                                   DocumentSnapshot documentSnapshot =task.getResult().getDocuments().get(0);
                                  //String us = db.collection("professeur").document().getId();
                                    String docId =documentSnapshot.getId();

                                   // String test =db.document(prof.getNom()).getId();
                                   // System.out.println(us);
                                    db.collection("professeur").document(docId).update(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    System.out.println("okkk");
                                                    dialogPlus.dismiss();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.println("echeeeec");
                                                    dialogPlus.dismiss();
                                                }
                                            });
                                }
                            }
                        });
                    }
                });


            }
        }
        );
        //delete
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.prof_photo.getContext())
                        .setContentHolder(new ViewHolder(R.layout.delete_prof))
                        .setExpanded(true, 1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final CircleImageView editImage = myview.findViewById(R.id.editphoto);
                final EditText editNom = myview.findViewById(R.id.editnom);
                final EditText editPrenom = myview.findViewById(R.id.editprenom);
                final EditText editDep = myview.findViewById(R.id.editdep);
                final EditText editTel = myview.findViewById(R.id.edittel);
                Button delete = myview.findViewById(R.id.btnDelete);


                editNom.setText(prof.getNom());
                editDep.setText(prof.getDepartement());
                editPrenom.setText(prof.getPrenom());
                editTel.setText(prof.getTel());

                StorageReference storage = FirebaseStorage.getInstance().getReference(professeurArrayList.get(position).getPhoto());

                //editImage.setImageResource(prof.getPhoto());
                Glide.with(context)
                        .load(storage)
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .into(editImage);

                dialogPlus.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nom =editNom.getText().toString();
                        db=FirebaseFirestore.getInstance();
                        db.collection("professeur").whereEqualTo("nom",nom)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if(task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot =task.getResult().getDocuments().get(0);
                                    //String us = db.collection("professeur").document().getId();
                                    String docId =documentSnapshot.getId();

                                    // String test =db.document(prof.getNom()).getId();
                                    // System.out.println(us);
                                    db.collection("professeur").document(docId).delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    System.out.println("okkk");
                                                    dialogPlus.dismiss();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.println("echeeeec");
                                                    dialogPlus.dismiss();
                                                }
                                            });
                                }
                            }
                        });


                    }
                });

            }});

    }



    @Override
    public int getItemCount() {
        return professeurArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        ImageView prof_photo;
        TextView prof_nom,prof_prenom,prof_dep,prof_email,prof_tel;
        FloatingActionButton btnEdit,btnDel;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            prof_dep=itemView.findViewById(R.id.prof_dep);
            prof_nom=itemView.findViewById(R.id.prof_nom);
           // prof_prenom=itemView.findViewById(R.id.prof_prenom);
            prof_photo=itemView.findViewById(R.id.prof_photo);
            btnEdit=itemView.findViewById(R.id.btnEdit);
            btnDel=itemView.findViewById(R.id.btnDel);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recycleViewOnItemClick.onItemClick(getBindingAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    recycleViewOnItemClick.onLongItemClick(getBindingAdapterPosition());
                    return true;
                }
            });
        }
    }

}
