package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Activity_Beer extends AppCompatActivity {
    TextView ratingText;
    TextView beerName ;
    TextView beerDesc ;

    RatingBar ratingStar;
    TextView gbrate;

    float accum =0 ;
    int sumUser =0;
    double global_rating = 0 ;
    Rating rate ;

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

        final Button retour=findViewById(R.id.buttonRetourFromBeerActivity);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference current_user = database.getReference("Users").child(mAuth.getUid());
        String username = current_user.child("fullName").toString();

        // Receiving value into activity using intent.
        String name = getIntent().getStringExtra("ListViewClickedName");
        String desc = getIntent().getStringExtra("ListViewClickedDesc");
        String gr = getIntent().getStringExtra("ListViewClickedGr");

        // Setting up received value into EditText.
        beerName.setText(name);
        beerDesc.setText(desc);
        gbrate.setText(gr);
        rate = new Rating();

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), Activity_Timeline.class);
                startActivity(otherActivity);
                finish();
            }
        });

        ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText("Your rating is : "+rating);

                HashMap<String,Object> new_rate = new HashMap<>();
                new_rate.put(name,Float.toString(rating));
                current_user.child("user_rated_beers").updateChildren(new_rate);

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mRatingBarCh = rootRef.child("ratings");

                rating= ratingBar.getRating();
                //accum+=rating ;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Rating rating1 = new Rating(rating,name) ;
                DatabaseReference newrate = mRatingBarCh.push();
                newrate.setValue(rating1) ;

                //DatabaseReference a = FirebaseDatabase.getInstance().getReference();
                //DatabaseReference b = rootRef.child("ratings_global");

               //b..child(name).child("Nombre user").setValue(String.valueOf(sumUser));
                //b.child("ratings_global").child(name).child("Globale Rating").setValue(String.valueOf(global_rating));
            }
        });
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRatingBarCh = rootRef.child("ratings");
        mRatingBarCh.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double somme = 0 ;
                for(DataSnapshot ds : snapshot.getChildren()){
                    rate = ds.getValue(Rating.class);
                    if(rate.getName()==name){
                        somme += rate.getRate();

                    }
                   /* DatabaseReference a = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference b = rootRef.child("ratings_global");
                    b.child(name).child("Nombre user").setValue(String.valueOf(sumUser));
                    b.child(name).child("Globale Rating").setValue(String.valueOf(global_rating));
                    sumUser =0 ;*/
                }
                double global = somme/snapshot.getChildrenCount();
                gbrate.setText("globale_rating is"+global);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}