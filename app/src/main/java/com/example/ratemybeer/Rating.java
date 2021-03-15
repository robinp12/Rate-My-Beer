package com.example.ratemybeer;

import android.widget.TextView;

public class Rating {
    private double rate ;
    private double global_rating ;
    private TextView beer ;


    public Rating(){

    }

    public Rating( float rate ){
        this.rate = rate ;

    }

    public double getRate(){
        return this.rate ;
    }

    public double getGlobal_rating(){
        return this.global_rating;
    }
}
