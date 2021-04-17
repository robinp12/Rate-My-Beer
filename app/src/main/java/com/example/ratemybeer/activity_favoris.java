package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class activity_favoris extends AppCompatActivity {
    ListView allBeerlistView1;
    ;
    SearchView searchView1;
    BottomNavigationView bottomNavigationView1;
    Biere beer;
    Spinner spinner1;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Button and ListView
        allBeerlistView1 = findViewById(R.id.listView1);
        searchView1 = findViewById(R.id.search1);
        bottomNavigationView1 = findViewById(R.id.bottom_navigation1);
        //beer = new Biere();

        //ArrayAdapter
        ArrayList<Biere> beerList = new ArrayList<>();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        //Query
        DatabaseReference ref = firebaseDatabase.getReference("Users").child(mAuth.getUid()).child("Favoris");
        Query onNameFilter1 = ref.orderByChild("name");
        Query onDegFilter1 = ref.orderByChild("degree");
        Query onRegionFilter1 = ref.orderByChild("origin");
        Query onGrating1 = ref.orderByChild("global_rating");

        Adapter customAdapter = new Adapter(activity_favoris.this, beerList);

        /*
         Dropdown menu filter beer
        */
        spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.beer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        final boolean[] isNameFilterDescending = {false};
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object selection = parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), selection.toString(), Toast.LENGTH_LONG).show();

                if(selection.toString().equals("Nom")){
                    //Toast.makeText(getApplicationContext(), "Nom clicked", Toast.LENGTH_LONG).show();
                    onNameFilter1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                beerList.add(beer);
                            }
                            allBeerlistView1.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                else if (selection.toString().equals("Degré")){
                    onDegFilter1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                beerList.add(beer);
                            }
                            allBeerlistView1.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if (selection.toString().equals("Region")){
                    onRegionFilter1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                beerList.add(beer);
                            }
                            allBeerlistView1.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if (selection.toString().equals("Note")){
                    onGrating1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            beerList.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                beer = ds.getValue(Biere.class);
                                //beerName.add(beer.getName());
                                beerList.add(beer);
                            }
                            Collections.reverse(beerList);
                            allBeerlistView1.setAdapter(customAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        beerList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            beer = ds.getValue(Biere.class);
                            //beerName.add(beer.getName());
                            beerList.add(beer);
                        }
                        allBeerlistView1.setAdapter(customAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                allBeerlistView1.setAdapter(customAdapter);

                return false;
            }
        });

        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), Activity_Timeline.class);
                        break;
                    case R.id.add:
                        intent = new Intent(getApplicationContext(), Activity_AddBeer.class);
                        break;
                    case R.id.favorite:
                        intent = new Intent(getApplicationContext(), activity_favoris.class);
                        break;
                }
                startActivity(intent);
                finish();
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    }
