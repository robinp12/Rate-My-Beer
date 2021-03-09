package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);
        final Button retour=findViewById(R.id.buttonRetourFromBeerActivity);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),TimelineActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }

}