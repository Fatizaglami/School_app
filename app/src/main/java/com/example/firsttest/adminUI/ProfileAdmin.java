package com.example.firsttest.adminUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.firsttest.R;

public class ProfileAdmin extends AppCompatActivity implements View.OnClickListener {
private Button btnListProf,btnListEtud,btnprofile,btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        btnListEtud=findViewById(R.id.btnListEtud);
        btnListEtud.setOnClickListener(this);

        btnListProf=findViewById(R.id.btnListProf);
        btnListProf.setOnClickListener(this);

        btnprofile=findViewById(R.id.btnprofile);
        btnprofile.setOnClickListener(this);

        btnlogout=findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnListEtud:
                break;
            case R.id.btnListProf:
                startActivity(new Intent(this,List_professeur.class));
                break;
            case R.id.btnprofile:
                //profile
                break;
            case R.id.btnlogout:
                //quitter
                break;
        }
    }
    private void showMenu(View v){
        PopupMenu popupMenu = new PopupMenu(ProfileAdmin.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.activity_main_drawer,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.item_profile){
                    Intent myIntent= new Intent(ProfileAdmin.this, ProfileAdmin.class);
                    startActivity(myIntent);
                }
                else if (item.getItemId()==R.id.item_prof){
                    Intent myIntent= new Intent(ProfileAdmin.this, List_professeur.class);
                    startActivity(myIntent);
                }
                else if (item.getItemId()==R.id.item_etud){

                }


                return false;
            }
        });
        popupMenu.show();
    }

}