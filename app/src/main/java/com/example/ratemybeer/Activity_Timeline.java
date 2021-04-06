package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Timeline extends AppCompatActivity {
    ListView allBeerlistView;;
    SearchView searchView;
    Biere beer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Button and ListView
        final Button retour = findViewById(R.id.button3);
        final Button searchButton = findViewById(R.id.button);
        allBeerlistView = findViewById(R.id.listView);
        searchView = findViewById(R.id.search);

        beer = new Biere();

        //ArrayAdapter
        ArrayList<Biere> beerList = new ArrayList<>();
        // ArrayAdapter<Biere> adapter = new ArrayAdapter<>(this, R.layout.beer_element, R.id.beer_info, beerList);

        //Query
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataBeers = database.child("Beers");

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), Activity_Home.class);
                startActivity(otherActivity);
                finish();
            }
        });
        BeerAdapter customAdapter = new BeerAdapter(Activity_Timeline.this, beerList);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {

            public boolean onClose() {
                searchView.setVisibility(SearchView.GONE);
                searchButton.setVisibility(View.VISIBLE);

                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Coder ici pour la recherche
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
                allBeerlistView.setAdapter(new BeerAdapter(Activity_Timeline.this, beerList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        allBeerlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                // Getting listview click value into String variable.
                String TempListViewClickedName = beerList.get(position).getName();
                String TempListViewClickedDesc = beerList.get(position).getDescription();

                Intent intent = new Intent(getApplicationContext(), Activity_Beer.class);

                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedName", TempListViewClickedName);
                intent.putExtra("ListViewClickedDesc", TempListViewClickedDesc);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
