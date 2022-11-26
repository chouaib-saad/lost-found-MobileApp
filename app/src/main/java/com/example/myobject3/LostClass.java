package com.example.myobject3;

import com.google.firebase.database.Exclude;

public class LostClass {

    String nom,categ,nomObj,emplac,description;
    String mImageUrl;
    private String mKey;

    public LostClass() {
        //empty Constructor needed for fbdb
    }

    public LostClass(String nom, String categ, String nomObj, String emplac, String description,String mImageUrl) {
        this.nom = nom;
        this.categ = categ;
        this.nomObj = nomObj;
        this.emplac = emplac;
        this.description = description;
        this.mImageUrl = mImageUrl;
    }

    public LostClass(String nom, String categ, String nomObj, String emplac, String description) {
        this.nom = nom;
        this.categ = categ;
        this.nomObj = nomObj;
        this.emplac = emplac;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public String getCateg() {
        return categ;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public void setNomObj(String nomObj) {
        this.nomObj = nomObj;
    }

    public void setEmplac(String emplac) {
        this.emplac = emplac;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomObj() {
        return nomObj;
    }

    public String getEmplac() {
        return emplac;
    }

    public String getDescription() {
        return description;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
} //end class
