

        package com.example.ratemybeer;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.format.DateFormat;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
        import com.squareup.picasso.Picasso;
        import com.squareup.picasso.Request;

        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Locale;

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

    // Variable for comment
    EditText com ;
    Button add ;
    ImageView imgU ;
    RecyclerView RvComment ;
    CommentAdapter commentAdapter;
    List<Comment> listComment;


    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

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
        String name = getIntent().getStringExtra("ListViewClickedName");
        b = findViewById(R.id.img) ;
// comment
        RvComment = findViewById(R.id.rv);
        add = findViewById(R.id.Add);
        com = findViewById(R.id.comment) ;
        imgU = findViewById(R.id.imageU) ;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add.setVisibility(View.INVISIBLE);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child("Comment").child(name).push();
                String comment_content = com.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
               //String uimg = firebaseUser.getPhotoUrl().toString();
               // Query current_Beer = database.getReference("Beers").orderByChild("name").equalTo(name).limitToFirst(1).getRef();
                Comment comment = new Comment(comment_content,uid,null,uname);

                ref.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added");
                        com.setText("");
                        add.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : "+e.getMessage());
                    }
                });

                DatabaseReference commentRef = firebaseDatabase.getReference().child("Comment").child(name).child(uid);
                commentRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listComment = new ArrayList<>();
                        for (DataSnapshot snap:dataSnapshot.getChildren()) {

                            Comment comment = snap.getValue(Comment.class);
                            listComment.add(comment) ;

                        }

                        commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                        RvComment.setAdapter(commentAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });



            RvComment.setLayoutManager(new LinearLayoutManager(this));

            DatabaseReference commentRef = firebaseDatabase.getReference("Comment").child(name);
            commentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listComment = new ArrayList<>();
                    for (DataSnapshot snap:dataSnapshot.getChildren()) {

                        Comment comment = snap.getValue(Comment.class);
                        listComment.add(comment) ;

                    }

                    commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                    RvComment.setAdapter(commentAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });







       //RvComment.setLayoutManager(new LinearLayoutManager(this));









        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference current_user = database.getReference("Users").child(mAuth.getUid());
        String username = current_user.child("fullName").toString();
        DatabaseReference users=database.getReference("Users");

        // Receiving value into activity using intent.

        String desc = getIntent().getStringExtra("ListViewClickedDesc");
        String gr = getIntent().getStringExtra("ListViewClickedGr");
        String v = getIntent().getStringExtra("url"); // variable contient l'url
        //String region = getIntent().getStringExtra("ListViewClickedRegion");

        Query current_Beer = database.getReference("Beers").orderByChild("name").equalTo(name).limitToFirst(1).getRef();
        //Toast.makeText(getApplicationContext(), current_Beer.g, Toast.LENGTH_LONG).show();


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

                //ratingText.setText("Your rating is : "+rating);

                HashMap<String,Object> new_rate = new HashMap<>();
                new_rate.put(name,Float.toString(rating));

                //if beer
                current_user.child("user_rated_beers").updateChildren(new_rate);

                //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                //DatabaseReference mRatingBarCh = rootRef.child("ratings");

                //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Rating rating1 = new Rating(rating,name) ;
                //DatabaseReference newrate = mRatingBarCh.push();
                //newrate.setValue(rating1) ;

            }
        });
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Float>globalRating=new ArrayList<>();
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("user_rated_beers").hasChild(name)){
                        String rate_beer=ds.child("user_rated_beers").child(name).getValue().toString();
                        globalRating.add(Float.parseFloat(rate_beer));

                    }
                }
                float moyenne=0;
                for(Float runner:globalRating){
                    moyenne+=runner;
                }
                moyenne=moyenne/globalRating.size();
                if(Float.isNaN(moyenne)){
                    ratingText.setText("Personne n'a encore voté cette bière !");
                }
                else {
                    ratingText.setText("La note moyenne est " + moyenne);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        current_Beer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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
                        //intent = new Intent(getApplicationContext(), Activity_Informations.class);
                        return true;
                }
                startActivity(intent);
                finish();
                return true;
            }
        });

    }
            private void showMessage(String message) {

                Toast.makeText(this,message,Toast.LENGTH_LONG).show();

            }

            private String timestampToString(long time) {

                Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                calendar.setTimeInMillis(time);
                String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
                return date;


            }





}
