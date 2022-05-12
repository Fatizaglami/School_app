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
        adsElement.setTitle("MIOLA APP");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_login_hero)
                .setDescription("L’objectif est\tde\tdévelopper\tune\tapplication\tmobile\tqui\tpermet principalement de\tgérer\tla\t\n" +
                        "communication\t et\t l’échange\t de\t données\t et\t de\t documents\t entre\t les\tmembres\t de\t la\t filière\t\n" +
                        "MIOLA.\t\n" +
                        "Les\téventuels\tutilisateurs\tde\tl’application\tsont\tles\tprofesseurs\tet\tles\tétudiants\tqui\tpeuvent\t\n" +
                        "consulter\tles\tmessages\tet\tles\tdocuments\treçus\tdans\tleurs boites de\tmessagerie et\tpeuvent\tà\t\n" +
                        "leurs\t tours\t envoyer\t des\t messages\t et\t des\t documents\t aux\t autres membres de la\t filière.\t\n" +
                        "L’envoi\tde\tdocuments\tet\tde\tmessages peut concerner un\tou\tplusieurs\tprofesseurs\tet/ou\tun\t\n" +
                        "ou\tplusieurs\tétudiants.")
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(adsElement)
                .addGroup("Connect Us")
                .addEmail("nouhaila.elfahsi20@gmail.com")
                .addGitHub("https://github.com/Fatizaglami")
                .addFacebook("nouhaila elfahsi")
                .addItem(createCopyRight())
                .create();
        setContentView(aboutPage);

    }

    private Element createCopyRight() {
        Element copyright = new Element();
        String copyrightString = String.format("Copyright %d by MiolaAPP", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setGravity(Gravity.CENTER);
        return copyright;
    }
}

