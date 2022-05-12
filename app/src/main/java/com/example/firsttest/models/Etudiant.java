package com.example.firsttest.models;

public class Etudiant {
    private String nom;
    private String prenom;
    private String tel;
    private String photo;
    private String email;

    public String getEmail() {
        return email;
    }

    public Etudiant(String nom, String prenom, String tel, String photo, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.photo = photo;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Etudiant(){}

    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Etudiant(String nom, String prenom, String tel,String photo){
        nom= new String(nom);
        prenom= new String(prenom);
        tel= new String(tel);
        photo = new String(photo);
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
