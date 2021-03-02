package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ratemybeer.ui.login.LoginActivity;

public class Inscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        final Button previousButton = findViewById(R.id.button2);
        final Button confirmedButton=findViewById(R.id.button);
        confirmedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),TimelineActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

    }
}