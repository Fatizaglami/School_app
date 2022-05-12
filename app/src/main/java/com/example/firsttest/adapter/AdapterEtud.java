package com.example.firsttest.adapter;


import android.content.Context;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firsttest.R;
import com.example.firsttest.interfaces.RecycleViewOnItemClick;
import com.example.firsttest.models.Etudiant;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterEtud extends RecyclerView.Adapter<AdapterEtud.myViewHolder> {

    Context context;
    ArrayList<Etudiant> etudiantArrayList;
    static RecycleViewOnItemClick recycleViewOnItemClick;



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
    public void onBindViewHolder(@NonNull AdapterEtud.myViewHolder holder, int position) {
        Etudiant etudiant = etudiantArrayList.get(position);


        holder.etud_nom.setText(etudiant.getNom()+" "+etudiant.getPrenom());

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(etudiantArrayList.get(position).getPhoto());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)


        Glide.with(context)
                .load(storageReference)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.etud_photo);

    }



    @Override
    public int getItemCount() {
        return etudiantArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        ImageView etud_photo;
        TextView etud_nom,etud_prenom,etud_email,etud_tel;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            etud_nom=itemView.findViewById(R.id.etud_nom);
            etud_photo=itemView.findViewById(R.id.etud_photo);


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

