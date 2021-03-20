package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class Activity_Informations extends AppCompatActivity {
     TextView ratingValue, ratingText;
     RatingBar rb ;
     SeekBar sb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);
        ratingValue = findViewById(R.id.tv) ;
        ratingText = findViewById(R.id.tv1) ;
        rb = findViewById(R.id.rb) ;
        sb = findViewById(R.id.sb) ;

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratingValue.setText("Value is :" +progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText("rating is : "+rating);
            }
        });

    }
}