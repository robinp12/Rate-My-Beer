package com.example.ratemybeer;

public class Biere {
    private String image;
    private String name;
    private String origin ;
    private String alcohol ;
    ///private int sumUser ;
    //private double sumRate ;
    //private float global_rating;
    private String description;

    public Biere(){};

    public Biere(String name, String origin, String alcohol, String description, String image){

        this.name=name;
        this.description=description;
        this.origin = origin;
        this.alcohol=alcohol ;
        this.image = image ;
    }
   /* public double getSumRate(){
        return getSumRate() ;
    }
    public int getSumUser(){
        return getSumUser() ;
    }*/
    public String getDescription() {
        return description;
    }
    public String getOrigin(){
        return origin;
    }
    public String getAlcohol(){
        return alcohol;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return this.name;
    }

    /*public void setImage(int image) {
        this.image = image;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    //public float getGlobal_rating() {
       // return global_rating;
    //}

    public void setGlobal_rating(float global_rating) {
        this.origin = origin;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}