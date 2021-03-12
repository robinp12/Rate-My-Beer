package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStructure;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ratemybeer.ui.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    ListView listView;
    Biere beer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Button and ListView
        final Button retour = findViewById(R.id.button3);
        listView = (ListView) findViewById(R.id.listView);
        beer = new Biere();

        //ArrayAdapter
        ArrayList<String> list;
        ArrayAdapter<String> adapter;

        //Query
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataBeers = database.child("Beers");

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.beer_element, R.id.beer_info, list);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), Activity_home.class);
                startActivity(otherActivity);
                finish();
            }
        });

        dataBeers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    beer = ds.getValue(Biere.class);
                    list.add(beer.getName());
                }
                listView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    }
