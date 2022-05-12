package com.example.firsttest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;


public class show_item_prof extends AppCompatActivity implements View.OnClickListener{

    private CircleImageView prof_photo;
    private TextView prof_nom, prof_dep,prof_prenom;
    FloatingActionButton button_appel,button_wtsp,button_email,button_sms;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professeur_item_layout);

        button_appel=findViewById(R.id.button_appel);
        button_appel.setOnClickListener(this);
        button_sms=findViewById(R.id.button_sms);
        button_sms.setOnClickListener(this);
        button_wtsp=findViewById(R.id.button_wtsp);
        button_wtsp.setOnClickListener(this);
        button_email=findViewById(R.id.button_email);
        button_email.setOnClickListener(this);

        prof_dep=findViewById(R.id.prof_dep);
        prof_nom=findViewById(R.id.prof_nom);
       // prof_photo=findViewById(R.id.prof_photo);
       // prof_prenom=findViewById(R.id.prof_prenom);
//share data between two activities
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String nom = extras.getString("prof_nom");
            String dep = extras.getString("prof_dep");
            String prenom = extras.getString("prof_prenom");
           // int img = extras.getInt("prof_photo");
            prof_nom.setText("Pr."+" "+nom+" "+prenom);
            prof_dep.setText("Departement"+" "+dep);
           // prof_photo.setImageResource(img);

        }
    }
    @Override
    public void onClick(View view) {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String tel = extras.getString("prof_tel");
            String email = extras.getString("prof_email");


        switch(view.getId()){
            case R.id.button_appel:
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                startActivity(callIntent);

                break;
            case R.id.button_sms:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + tel));
                startActivity(smsIntent);
                break;
            case R.id.button_wtsp:
               //to whatsapp
                break;

            case R.id.button_email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("email:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL,  new String[] {email});
                startActivity(emailIntent);
                break;
        }}
    }

}