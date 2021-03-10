package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class Activity_informations extends AppCompatActivity {
     TextView tv,tv1 ;
     RatingBar rb ;
     SeekBar sb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);
        tv = findViewById(R.id.tv) ;
        tv1 = findViewById(R.id.tv1) ;
        rb = findViewById(R.id.rb) ;
        sb = findViewById(R.id.sb) ;
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText("Value is :" +progress);
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
                tv1.setText("rating is : "+rating);
            }
        });

    }
}