package com.example.firsttest;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("Zaglami's app");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
               // .setImage(R.drawable.ic_login_hero)
                .setImage(R.drawable.iconapp)
                .setDescription("L’application a pour objectif la \tgestion\tde la\t\n" +
                        "communication\t et\t l’échange\t entre\t les\tprofesseurs et les etudiants\t de\t la\t filière\t\n" +
                        "MIOLA.\t\n" +
                        "Les\tutilisateurs\tde\tl’application\tsont\tles\tprofesseurs\tet\tles\tétudiants\tqui\tpeuvent\t\n" +
                        "telecharger\tleurs emplois du temps.\t\n ")
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(adsElement)
                .addGroup("Contactez nous")
                .addEmail("fatimazahra.zaglami@um5r.ac.ma")
                .addGitHub("https://github.com/Fatizaglami")
                .addFacebook("fatima zahra zaglami")
                .addItem(createCopyRight())
                .create();
        setContentView(aboutPage);

    }

    private Element createCopyRight() {
        Element copyright = new Element();
        String copyrightString = String.format("Copyright %d by Zaglami", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setGravity(Gravity.CENTER);
        return copyright;
    }
}

