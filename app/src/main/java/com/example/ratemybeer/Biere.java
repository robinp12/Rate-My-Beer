package com.example.ratemybeer;

public class Biere {
    private int image;
    private String name;
    private float global_rating;
    private String description;

    public Biere(){};

    public Biere(String name, String description, float global_rating){

        this.name=name;
        this.description=description;
        this.global_rating = global_rating;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGlobal_rating() {
        return global_rating;
    }

    public void setGlobal_rating(float global_rating) {
        this.global_rating = global_rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}