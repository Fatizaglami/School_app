package com.example.firsttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.firsttest.adminUI.ListEtud;
import com.example.firsttest.adminUI.ListProf;
import com.google.android.material.navigation.NavigationView;

public class DashboardProf extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_prof);



        /*-------------hooks-------------*/
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        toolbar= findViewById(R.id.toolbar);
        /*---------- Tool Bar---------------*/
        setSupportActionBar(toolbar);

        /*------------------Navigation Drawer Menu----------------*/

        //hide or show items

        Menu menu =navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);


        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

    }
    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{super.onBackPressed();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home:
                break; //because we already in Home screen

            case R.id.nav_profile:
                Intent intent = new Intent(DashboardProf.this,ProfileProf.class);
                startActivity(intent);break;

            case R.id.nav_share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();break;

            case R.id.nav_prof:
                startActivity(new Intent(DashboardProf.this, ListProf.class));
                break;
            case R.id.nav_etud:
                startActivity(new Intent(DashboardProf.this, ListEtud.class));
                break;
            case R.id.nav_emp:
               // startActivity(new Intent(DashboardProf.this, ListProf.class));
                //to emploi activity
                break;

        }
         drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}