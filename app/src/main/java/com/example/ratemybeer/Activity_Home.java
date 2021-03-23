package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Activity_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final CardView addNewBeer=findViewById(R.id.addNewBeer);
        final CardView homeButton=findViewById(R.id.homeButton);

        //final ImageView favorisButton=findViewById(R.id.favoris);

        addNewBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), Activity_AddBeer.class);
                startActivity(otherActivity);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), Activity_Timeline.class);
                startActivity(otherActivity);
                finish();
            }
        });

        /*favorisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),BeerActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
         */
    }
}