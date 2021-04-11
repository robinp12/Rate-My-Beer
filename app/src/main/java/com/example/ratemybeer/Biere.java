package com.example.ratemybeer;

public class Biere {
    private String image;
    private String name;
    private String origin ;
    private float alcohol ;
    private int rate_user;
    private String description;

    //constructeur par défaut
    public Biere(){}

    //constructeur liste de bière
    public Biere(String name, int rate_user, float alcohol){
        this.name = name;
        this.rate_user = rate_user;
        this.alcohol = alcohol;
    }
    //constructeur ajout de bière
    public Biere(String name, String origin, float alcohol, String description, String image){
        this.name=name;
        this.description=description;
        this.origin = origin;
        this.alcohol=alcohol ;
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
        return String.valueOf(alcohol);
    }
    public Float getDegree(){
        return alcohol;
    }
    public int getRate_user(){return this.rate_user; }
    public String getImg(){return this.image;}

    //setter
    public String getImage() {
        return image;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGlobal_rating(float global_rating) {
        this.origin = origin;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
