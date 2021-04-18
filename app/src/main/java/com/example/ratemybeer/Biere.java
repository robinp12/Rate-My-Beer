package com.example.ratemybeer;

import com.google.firebase.database.ServerValue;

public class Biere {
    private String image;
    private String name;
    private String origin ;
    private float degree ;
    private String description;
    private float global_rating;
    private String urlFirebase;
    private String postedBy;
    private Object timestamp;


    //constructeur par défaut
    public Biere(){}

    //constructeur liste de bière
    /*public Biere(String name, int rate_user, float alcohol){
        this.name = name;
        this.rate_user = rate_user;
        this.degree = alcohol;
    }*/
    //constructeur ajout de bière
    public Biere(String name, String origin, float alcohol, String description, String image, String postedBy){
        this.name=name;
        this.description=description;
        this.origin = origin;
        this.degree=alcohol ;
        this.image = image ;
        this.postedBy = postedBy ;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    //getter
    public String getDescription() {
        return description;
    }
    public String getOrigin(){
        return origin;
    }
    public String getAlcohol(){
        return String.valueOf(degree);
    }
    public Float getDegree(){
        return degree;
    }
    public String getImage() {
        return image;
    }
    public String getName() {
        return this.name;
    }
    public String getImg(){return this.image;}
    public String getUrlFirebase(){return this.urlFirebase;}
    public float getGlobal_rating(){return this.global_rating;}
    public String getPostedBy() {
        return postedBy;
    }
    public Object getTimestamp() { return timestamp; }

    //setter

    public void setName(String name) {
        this.name = name;
    }
    public void setGlobal_rating(float global_rating) {
        this.global_rating = global_rating;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
    public void setTimestamp(Object timestamp) { this.timestamp = timestamp; }
}
