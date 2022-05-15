package com.example.firsttest.adminUI;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.firsttest.R;
import com.example.firsttest.adminUI.ListEtud;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;


public class show_item_etud extends AppCompatActivity implements View.OnClickListener{

    private CircleImageView etud_photo;
    private TextView etud_nom,etud_prenom;
    FloatingActionButton button_appel,button_wtsp,button_email,button_sms;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item_etud);

        button_appel=findViewById(R.id.button_appel);
        button_appel.setOnClickListener(this);
        button_sms=findViewById(R.id.button_sms);
        button_sms.setOnClickListener(this);
        button_wtsp=findViewById(R.id.button_wtsp);
        button_wtsp.setOnClickListener(this);
        button_email=findViewById(R.id.button_email);
        button_email.setOnClickListener(this);

        etud_nom = findViewById(R.id.etud_nom);

//share data between two activities
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String nom = extras.getString("etud_nom");
            String prenom = extras.getString("etud_prenom");
            etud_nom.setText(nom+" "+prenom);


        }
    }
    @Override
    public void onClick(View view) {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String tel = extras.getString("etud_tel");
            String email = extras.getString("etud_email");


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
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                    emailIntent.putExtra(Intent.EXTRA_EMAIL,  new String[] {email});
                    startActivity(emailIntent);
                    break;
            }}
    }

}