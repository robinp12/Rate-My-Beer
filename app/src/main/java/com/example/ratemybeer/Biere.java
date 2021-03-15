package com.example.ratemybeer;

import android.media.Image;

public class Biere {
   // private int image;
    private String name;
    private String origin ;
    private String alcohol ;
    //private float global_rating;
    private String description;

    public Biere(){};

    public Biere(String name, String origin,String alcohol, String description){

        this.name=name;
        this.description=description;
        this.origin = origin;
        this.alcohol=alcohol ;

    }

    public String getDescription() {
        return description;
    }
    public String getOrigin(){
        return origin;
    }
    public String getAlcohol(){
        return alcohol;
    }

    /*public int getImage() {
        return image;
    }*/

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