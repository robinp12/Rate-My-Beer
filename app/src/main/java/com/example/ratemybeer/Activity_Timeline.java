package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;

public class Activity_Timeline extends AppCompatActivity {
    ListView allBeerlistView;;
    SearchView searchView;
    BottomNavigationView bottomNavigationView;
    Biere beer;
    Spinner spinner;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Button and ListView
        allBeerlistView = findViewById(R.id.listView);
        searchView = findViewById(R.id.search);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //beer = new Biere();

        //ArrayAdapter
        ArrayList<Biere> beerList = new ArrayList<>();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        //Query
        DatabaseReference dataBeers = database.child("Beers");
        Query onNameFilter = dataBeers.orderByChild("name");
        Query onDegFilter = dataBeers.orderByChild("degree");
        Query onRegionFilter = dataBeers.orderByChild("origin");
        Query onGrating = dataBeers.orderByChild("global_rating");

        BeerAdapter customAdapter = new BeerAdapter(Activity_Timeline.this, beerList);

        /*
         Dropdown menu filter beer
        */
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.beer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final boolean[] isNameFilterDescending = {false};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object selection = parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), selection.toString(), Toast.LENGTH_LONG).show();

                if(selection.toString().equals("Nom")){
                    //Toast.makeText(getApplicationContext(), "Nom clicked", Toast.LENGTH_LONG).show();
                    onNameFilter.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                if(beer.getName()!=null){
                                    beerList.add(beer);
                                }

                            }
                            allBeerlistView.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                else if (selection.toString().equals("Degré")){
                    onDegFilter.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                if(beer.getName()!=null){
                                    beerList.add(beer);
                                }
                            }
                            allBeerlistView.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if (selection.toString().equals("Region")){
                    onRegionFilter.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                if(beer.getName()!=null){
                                    beerList.add(beer);
                                }
                            }
                            allBeerlistView.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if (selection.toString().equals("Note")){
                    onGrating.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                if(beer.getName()!=null){
                                    beerList.add(beer);
                                }
                            }
                            Collections.reverse(beerList);
                            allBeerlistView.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dataBeers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        beerList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            beer = ds.getValue(Biere.class);
                            //beerName.add(beer.getName());
                            if(beer.getName()!=null){
                                beerList.add(beer);
                            }
                        }
                        allBeerlistView.setAdapter(customAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                allBeerlistView.setAdapter(customAdapter);

                return false;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        break;

                    case R.id.add:
                            intent = new Intent(getApplicationContext(), Activity_AddBeer.class);
                            startActivity(intent);
                            finish();
                        break;

                    case R.id.favorite:
                            intent = new Intent(getApplicationContext(), activity_favoris.class);
                            startActivity(intent);
                            finish();
                        break;

                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
