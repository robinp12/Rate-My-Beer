package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStructure;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
        listView = findViewById(R.id.listView);
        beer = new Biere();

        //ArrayAdapter
        ArrayList<Biere> beerList = new ArrayList<>();
        ArrayList<String> beerName = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.beer_element, R.id.beer_info, beerName);

        //Query
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataBeers = database.child("Beers");

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
                beerList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    beer = ds.getValue(Biere.class);
                    beerName.add(beer.getName());
                    beerList.add(beer);
                }
                listView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                // Getting listview click value into String variable.
                String TempListViewClickedName = beerList.get(position).getName();
                String TempListViewClickedDesc = beerList.get(position).getDescription();

                Intent intent = new Intent(getApplicationContext(), BeerActivity.class);

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
