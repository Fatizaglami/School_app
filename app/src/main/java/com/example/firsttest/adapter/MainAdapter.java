package com.example.firsttest.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttest.adminUI.About;
import com.example.firsttest.adminUI.Dashboard;
import com.example.firsttest.adminUI.NavAdmin;
import com.example.firsttest.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
Activity activity;
ArrayList<String> arrayList;

//create constructor
    public MainAdapter(Activity activity,ArrayList<String> arrayList){
this.activity=activity;
this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drawer_main,parent,false);
        //return holder view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.textview.setText(arrayList.get(position));
   holder.textview.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           //get clicked item position
           int position = holder.getAdapterPosition();
           //check condition
           switch (position){
               case 0:
                   //when position is 0 redirect to profile
                   activity.startActivity(new Intent(activity, NavAdmin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
           break;
               case 1:
                   activity.startActivity(new Intent(activity, Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
           break;
               case 2:
                   activity.startActivity(new Intent(activity, About.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
break;
               case 3:
                   AlertDialog.Builder builder =new AlertDialog.Builder(activity);
                   builder.setTitle("Logout");
                   builder.setMessage("Are you sur you want to logout ? ");
                   builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           activity.finishAffinity();
                           System.exit(0);
                       }
                   });
                   builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int i) {
                           dialog.dismiss();
                       }
                   });
                   builder.show();
                   break;
           }
       }
   });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.text_view);
        }
    }
}
