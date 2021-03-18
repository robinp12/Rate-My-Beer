package com.example.ratemybeer;

import android.widget.TextView;

public class Rating {
    private double rate ;
    private double global_rating ;
    private TextView beer ;
    private String name ;
    private int sumUser ;

    public Rating(){

    }

    public Rating( String name ,float rate, double global_rating, int sumUser ){
        this.rate = rate ;
        this.name = name ;
        this.global_rating = global_rating ;

    }

    public double getRate(){
        return this.rate ;
    }
    public String getName(){
        return this.name;
    }

    public double getGlobal_rating(){
        return this.global_rating;
    }

    public int getSumUser(){return this.sumUser;}
}
