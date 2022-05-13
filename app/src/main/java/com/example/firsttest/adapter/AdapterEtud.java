package com.example.firsttest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firsttest.R;
import com.example.firsttest.interfaces.RecycleViewOnItemClick;
import com.example.firsttest.models.Etudiant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterEtud extends RecyclerView.Adapter<AdapterEtud.myViewHolder> {

    Context context;
    ArrayList<Etudiant> etudiantArrayList;
    static RecycleViewOnItemClick recycleViewOnItemClick;
    //EditText editnom,editprenom,editdep,editemail,edittel;
    FirebaseFirestore db;



    public AdapterEtud(Context context, ArrayList<Etudiant> etudiantArrayList, RecycleViewOnItemClick recycleViewOnItemClick) {
        this.context = context;
        this.etudiantArrayList = etudiantArrayList;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    @NonNull
    @Override
    public AdapterEtud.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_etud,parent,false);

        return new AdapterEtud.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEtud.myViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Etudiant etud = etudiantArrayList.get(position);


        holder.etud_nom.setText("Pr."+" "+etud.getNom()+" "+etud.getPrenom());

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(etudiantArrayList.get(position).getPhoto());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)


        Glide.with(context)
                .load(storageReference)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.etud_photo);
//EDIT ETUDIANT
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {


                                                  final DialogPlus dialogPlus=DialogPlus.newDialog(holder.etud_photo.getContext())
                                                          .setContentHolder(new ViewHolder(R.layout.edit_etud))
                                                          .setExpanded(true,1100)
                                                          .create();

                                                  View myview=dialogPlus.getHolderView();
                                                  final CircleImageView editImage=myview.findViewById(R.id.editphoto);
                                                  final EditText editNom=myview.findViewById(R.id.editnom);
                                                  final EditText editPrenom=myview.findViewById(R.id.editprenom);
                                                  final EditText editTel=myview.findViewById(R.id.edittel);
                                                  Button update=myview.findViewById(R.id.btnUpdate);


                                                  editNom.setText(etud.getNom());
                                                  editPrenom.setText(etud.getPrenom());
                                                  editTel.setText(etud.getTel());

                                                  StorageReference storage= FirebaseStorage.getInstance().getReference(etudiantArrayList.get(position).getPhoto());

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

                                                          String nom =editNom.getText().toString();
                                                          //System.out.println(nom);



                                                          db=FirebaseFirestore.getInstance();

                                                          db.collection("etudiant").whereEqualTo("nom",nom)
                                                                  .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                              @Override
                                                              public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                                  if(task.isSuccessful() && !task.getResult().isEmpty()){
                                                                      DocumentSnapshot documentSnapshot =task.getResult().getDocuments().get(0);
                                                                      //String us = db.collection("etudiant").document().getId();
                                                                      String docId =documentSnapshot.getId();

                                                                      // String test =db.document(prof.getNom()).getId();
                                                                      // System.out.println(us);
                                                                      db.collection("etudiant").document(docId).update(map)
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


                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.etud_photo.getContext())
                        .setContentHolder(new ViewHolder(R.layout.delete_etud))
                        .setExpanded(true, 1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final CircleImageView editImage = myview.findViewById(R.id.editphoto);
                final EditText editNom = myview.findViewById(R.id.editnom);
                final EditText editPrenom = myview.findViewById(R.id.editprenom);
                final EditText editTel = myview.findViewById(R.id.edittel);
                Button delete = myview.findViewById(R.id.btnDelete);


                editNom.setText(etud.getNom());
                editPrenom.setText(etud.getPrenom());
                editTel.setText(etud.getTel());

                StorageReference storage = FirebaseStorage.getInstance().getReference(etudiantArrayList.get(position).getPhoto());

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
                        db.collection("etudiant").whereEqualTo("nom",nom)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if(task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot =task.getResult().getDocuments().get(0);
                                    String docId =documentSnapshot.getId();


                                    db.collection("etudiant").document(docId).delete()
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
        return etudiantArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        ImageView etud_photo;
        TextView etud_nom,etud_prenom,etud_email,etud_tel;
        FloatingActionButton btnEdit,btnDel;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            etud_nom=itemView.findViewById(R.id.etud_nom);
            // etud_prenom=itemView.findViewById(R.id.etud_prenom);
            etud_photo=itemView.findViewById(R.id.etud_photo);
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
