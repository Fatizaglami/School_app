package com.example.firsttest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firsttest.R;
import com.example.firsttest.adminUI.ListProfUpd;
import com.example.firsttest.models.Professeur;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterProf extends RecyclerView.Adapter<AdapterProf.myViewHolder> {

   Context context;
   ArrayList<Professeur> professeurArrayList;

    public AdapterProf(Context context, ArrayList<Professeur> professeurArrayList) {
      this.context = context;
        this.professeurArrayList = professeurArrayList;
    }

    @NonNull
    @Override
    public AdapterProf.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new AdapterProf.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProf.myViewHolder holder, int position) {


      Professeur prof = professeurArrayList.get(position);

      holder.prof_dep.setText("Departement"+" "+prof.getDepartement());

        holder.prof_nom.setText(prof.getNom());
        holder.prof_prenom.setText(prof.getPrenom());
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

    }

    @Override
    public int getItemCount() {
        return professeurArrayList.size();
    }
    public static class myViewHolder extends RecyclerView.ViewHolder{

        ImageView prof_photo;
        TextView prof_nom,prof_prenom,prof_dep;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            prof_dep=itemView.findViewById(R.id.prof_dep);
            prof_nom=itemView.findViewById(R.id.prof_nom);
            prof_prenom=itemView.findViewById(R.id.prof_prenom);
            prof_photo=itemView.findViewById(R.id.prof_photo);
        }
    }

}
