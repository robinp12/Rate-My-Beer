package com.example.ratemybeer;

public class Biere {
    int Image;
    String Title;
    String Des;

    public Biere(int image,String title, String des){
        Image=image;
        Title=title;
        Des=des;
    }

    public String getDes() {
        return Des;
    }

    public int getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

}
