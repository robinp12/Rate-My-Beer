package com.example.ratemybeer;

public class Biere {
    private String image;
    private String name;
    private String origin ;
    private float degree ;
    private int rate_user;
    private String description;
    private float global_rating;
    private String urlFirebase;

    //constructeur par défaut
    public Biere(){}

    //constructeur liste de bière
    public Biere(String name, int rate_user, float alcohol){
        this.name = name;
        this.rate_user = rate_user;
        this.degree = alcohol;
    }
    //constructeur ajout de bière
    public Biere(String name, String origin, float alcohol, String description, String image){
        this.name=name;
        this.description=description;
        this.origin = origin;
        this.degree=alcohol ;
        this.image = image ;
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
    public int getRate_user(){return this.rate_user; }
    public String getImg(){return this.image;}
    public String getUrlFirebase(){return this.urlFirebase;}
    public float getGlobal_rating(){return this.global_rating;}

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
}
