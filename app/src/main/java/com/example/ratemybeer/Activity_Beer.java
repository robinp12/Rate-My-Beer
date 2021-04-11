package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.util.HashMap;

public class Activity_Beer extends AppCompatActivity  {
    TextView ratingText;
    TextView beerName ;
    TextView beerDesc ;
    TextView origin;

    RatingBar ratingStar;
    TextView gbrate;
    Rating rate ;
    TextView ve ; // la ou on stock l url
    ImageView b ; /// L'image
    BottomNavigationView bottomNavigationView;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        mAuth = FirebaseAuth.getInstance();

        ratingText = findViewById(R.id.vs) ;
        ratingStar = findViewById(R.id.rb) ;
        gbrate = findViewById(R.id.gr);
        beerName = findViewById(R.id.textView3) ;
        beerDesc = findViewById(R.id.textView7) ;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        origin = findViewById(R.id.region);

        b = findViewById(R.id.img) ;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference current_user = database.getReference("Users").child(mAuth.getUid());
        String username = current_user.child("fullName").toString();

        // Receiving value into activity using intent.
        String name = getIntent().getStringExtra("ListViewClickedName");
        String desc = getIntent().getStringExtra("ListViewClickedDesc");
        String gr = getIntent().getStringExtra("ListViewClickedGr");
        String v = getIntent().getStringExtra("url"); // variable contient l'url
        //String region = getIntent().getStringExtra("ListViewClickedRegion");


        // Setting up received value into EditText.
        beerName.setText(name);
        beerDesc.setText(desc);
        gbrate.setText(gr);
        //ve.setText(v) ;
        Picasso.with(this).load(String.valueOf(v)).into(b); // Convert the Url to image
        rate = new Rating();

        // check if beer already rated
        current_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("user_rated_beers").hasChild(name)){
                    String  rate_beer = snapshot.child("user_rated_beers").child(name).getValue().toString();
                    ratingStar.setRating(Float.parseFloat(rate_beer));
                    }
                else{
                    //Toast.makeText(getApplicationContext(),"Tu n'as pas encore voté cette bière !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText("Your rating is : "+rating);

                HashMap<String,Object> new_rate = new HashMap<>();
                new_rate.put(name,Float.toString(rating));

                //if beer
                current_user.child("user_rated_beers").updateChildren(new_rate);

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mRatingBarCh = rootRef.child("ratings");

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Rating rating1 = new Rating(rating,name) ;
                DatabaseReference newrate = mRatingBarCh.push();
                newrate.setValue(rating1) ;

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
                        //intent = new Intent(getApplicationContext(), Activity_Informations.class);
                        return true;
                }
                startActivity(intent);
                finish();
                return true;
            }
        });

    }
}