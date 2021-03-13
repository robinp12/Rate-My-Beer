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
    TextView beerName ;
    TextView beerDesc ;
    RatingBar rb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        vs= findViewById(R.id.vs) ;
        rb = findViewById(R.id.rb) ;
        beerName = findViewById(R.id.textView3) ;
        beerDesc = findViewById(R.id.textView7) ;

        final Button retour=findViewById(R.id.buttonRetourFromBeerActivity);

        // Receiving value into activity using intent.
        String name = getIntent().getStringExtra("ListViewClickedName");
        String desc = getIntent().getStringExtra("ListViewClickedDesc");

        // Setting up received value into EditText.
        beerName.setText(name);
        beerDesc.setText(desc);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),TestActivity.class);
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