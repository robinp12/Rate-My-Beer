package com.example.ratemybeer;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Rating {
    private double rate ;
    private double global_rating ;
    private TextView beer ;
    private String name ;
    private String mAuth;
    private int sumUser ;

    public Rating(){

    }

    public Rating( float rate,String name ){
        this.rate = rate ;
        this.name = name ;


    }

    public double getRate(){
        return this.rate ;
    }
    public String getName(){
        return this.name;
    }
   /*  public String getmAuth(){
        return this.mAuth ;
    }

   public double getGlobal_rating(){
        return this.global_rating;
    }

    public int getSumUser(){return this.sumUser;}*/
}
