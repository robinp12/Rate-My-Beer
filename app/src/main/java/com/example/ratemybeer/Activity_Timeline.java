package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Timeline extends AppCompatActivity {
    ListView allBeerlistView;;
    SearchView searchView;
    BottomNavigationView bottomNavigationView;
    Biere beer;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Button and ListView
        allBeerlistView = findViewById(R.id.listView);
        searchView = findViewById(R.id.search);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        beer = new Biere();

        /*
         Dropdown menu filter beer
        */
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.beer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //ArrayAdapter
        ArrayList<Biere> beerList = new ArrayList<>();

        //Query
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataBeers = database.child("Beers");

        BeerAdapter customAdapter = new BeerAdapter(Activity_Timeline.this, beerList);

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

        dataBeers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                beerList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    beer = ds.getValue(Biere.class);
                    //beerName.add(beer.getName());
                    beerList.add(beer);
                }
                allBeerlistView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

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
                     //   intent = new Intent(getApplicationContext(), Activity_Informations.class);
                        return true;
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
