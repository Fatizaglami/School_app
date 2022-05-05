package com.example.firsttest.adminUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.firsttest.R;
import com.example.firsttest.adapter.MainAdapter;

import java.util.ArrayList;

public class NavAdmin extends AppCompatActivity {
DrawerLayout drawerLayout;
ImageView btmenu;
RecyclerView recyclerView;
public static ArrayList<String> arrayList = new ArrayList<>();
MainAdapter adapter;

    public static void closeDrawer(DrawerLayout drawerLayout) {
   if(drawerLayout.isDrawerOpen(GravityCompat.START)){
       drawerLayout.closeDrawer(GravityCompat.START);
   }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_admin);

        drawerLayout=findViewById(R.id.drawer_layout);
        btmenu=findViewById(R.id.bt_menu);
        recyclerView=findViewById(R.id.recycler_view);

        arrayList.clear();
        //add menu item in arraylist
        arrayList.add("Profile");
        arrayList.add("Professeurs");
        arrayList.add("Etudiants");
        arrayList.add("Logout");

        //initialise adapter
        adapter = new MainAdapter(this,arrayList);
        //set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set adapter
        recyclerView.setAdapter(adapter);

        btmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}