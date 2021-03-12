package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class BeerActivity extends AppCompatActivity {
    TextView vs ;
    RatingBar rb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);
        vs= findViewById(R.id.vs) ;
        rb = findViewById(R.id.rb) ;
        final Button retour=findViewById(R.id.buttonRetourFromBeerActivity);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),Activity_home.class);
                startActivity(otherActivity);
                finish();
            }
        });
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vs.setText("Your rating is : "+rating);
            }
        });
    }

}