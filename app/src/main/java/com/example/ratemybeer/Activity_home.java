package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ImageView addNewBeer=findViewById(R.id.addNewBeer);
        final ImageView homeButton=findViewById(R.id.homeButton);
        final ImageView favorisButton=findViewById(R.id.favoris);
        addNewBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),AddBeerActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),TestActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

        favorisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),BeerActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }
}