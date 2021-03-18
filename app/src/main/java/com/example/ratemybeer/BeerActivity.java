package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class BeerActivity extends AppCompatActivity {
    TextView vs ;
    TextView beerName ;
    TextView beerDesc ;
    RatingBar rb ;
    double accum =0 ;
    int sumUser =0;
    double global_rating = 0 ;
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
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mRatingBarCh = rootRef.child("ratings");

                rating= ratingBar.getRating();
                accum+=rating ;
                sumUser++ ;
                global_rating = accum/sumUser ;

                mRatingBarCh.child("rating").child(name).setValue(String.valueOf(accum));
                mRatingBarCh.child("rating").child(name).child("Nombre user").setValue(String.valueOf(sumUser));
                mRatingBarCh.child("rating").child(name).child("Globale Rating").setValue(String.valueOf(global_rating));

            }
        });



    }

}