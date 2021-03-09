package com.example.ratemybeer;

public class Biere {
    private int image;
    private String name;
    private String description;

    public Biere(){};

    public Biere(String name, String description){

        this.name=name;
        this.description=description;
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

}
