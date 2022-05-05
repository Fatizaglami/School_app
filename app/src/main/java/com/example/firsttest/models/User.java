package com.example.firsttest.models;

public class User {
    public String nom, prenom, tel, email,password;

    public User(){

    }
    public User(String nom, String prenom, String email,String tel,String password){
        this.nom=nom;
        this.email=email;
        this.prenom=prenom;
        this.password=password;
        this.tel=tel;
    }
}
