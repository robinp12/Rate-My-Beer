package com.example.ratemybeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
        import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
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
    TextView date;

    RatingBar ratingStar;
    Rating rate ;
    TextView ve ; // la ou on stock l url
    ImageView vueimg ; /// L'image
    BottomNavigationView bottomNavigationView;

    // Variable for comment
    EditText com ;
    Button add;
    ImageButton addFav ,del;
    Button modifyBeer ;
    EditText modifyBeerDesc ;
    ImageView imgU ;
    RecyclerView RvComment ;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    Biere n ;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    Long dateUpload;


    TextView org,deg ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        mAuth = FirebaseAuth.getInstance();


        ratingText = findViewById(R.id.vs) ;
        ratingStar = findViewById(R.id.rb) ;
        ratingStar = findViewById(R.id.rb) ;
        beerName = findViewById(R.id.textView3) ;
        beerDesc = findViewById(R.id.textView7) ;
        beerDesc.setMovementMethod(new ScrollingMovementMethod());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        origin = findViewById(R.id.region);
        date = findViewById(R.id.date);
        String name = getIntent().getStringExtra("ListViewClickedName");
        String desc = getIntent().getStringExtra("ListViewClickedDesc");
        Long dateAjout = getIntent().getLongExtra("ListViewClickedTimestamp", 0);
        String utlImg = getIntent().getStringExtra("url");
        String gbr = getIntent().getStringExtra("gb");
        String pstb = getIntent().getStringExtra("pst");


        vueimg = findViewById(R.id.img) ;
        modifyBeer = findViewById(R.id.button);
        modifyBeerDesc = findViewById(R.id.editTextTextMultiLine);
        addFav = findViewById(R.id.fav) ;
        del=findViewById(R.id.del);
        org= findViewById(R.id.org);
        deg=findViewById(R.id.deg);


        String or= getIntent().getStringExtra("ListViewClickedRegion");
        String de = getIntent().getStringExtra("deg");
        org.setText(or);
        deg.setText(de+"°");



        
        // comment
        RvComment = findViewById(R.id.rv);
        add = findViewById(R.id.Add);
        com = findViewById(R.id.comment) ;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference postedBy = firebaseDatabase.getReference().child("Beers").child(name).child("postedBy");
        DatabaseReference isAdmin = firebaseDatabase.getReference().child("Users").child(firebaseUser.getUid()).child("isAdmin");
        DatabaseReference info = firebaseDatabase.getReference().child("Beers").child(name).child("origin");



        if(!dateAjout.equals(new Long(0))){
            String dateToString = timestampToString(dateAjout);
            date.setText("Dernière modification "+ dateToString);
        }
        else {
            date.setText("");
        }


        isAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().equals(true)){
                    //Toast.makeText(getApplicationContext(),"Admin", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        modifyBeer.setVisibility(View.INVISIBLE);
        /*postedBy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals(firebaseUser.getUid())){
                    modifyBeer.setVisibility(View.VISIBLE);
                }
                else{
                    modifyBeer.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        modifyBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //beerDesc.setVisibility(View.GONE);
                //modifyBeerDesc.setVisibility(View.VISIBLE);
            }
        });*/



        //add beer to favoris
         DatabaseReference ref = firebaseDatabase.getReference("Users").child(mAuth.getUid()).child("Favoris").child(name);
         DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Beers");

       // del.setVisibility(View.VISIBLE);
        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                addFav.setImageResource((R.drawable.ic_staar));
                Toast.makeText(getApplicationContext(),"Vous avez ajouté la bière \""+name+"\" à votre liste favoris !", Toast.LENGTH_SHORT).show();

                addFav.setEnabled(true);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            n= new Biere() ;
                            n =  snapshot.child(name).getValue(Biere.class); // n est une biere
                            ref.setValue(n);

                        del.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Voulez-vous supprimer la bière \" "+name+"\" de vos favoris ?")
                .setPositiveButton("Oui", null)
                .setNegativeButton("Non", null).create();

        //Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseDatabase.getReference("Users").child(mAuth.getUid()).child("Favoris").child(name).removeValue();
                        Toast.makeText(getApplicationContext(),"Vous avez supprimé la bière \" "+name+"\" de vos favoris !", Toast.LENGTH_SHORT).show();
                        addFav.setImageResource((R.drawable.ic_starout));
                        del.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });




            }
        });

         //set button favoris
        DatabaseReference database = firebaseDatabase.getReference("Users").child(mAuth.getUid());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("Favoris").hasChild(name)){
                    addFav.setImageResource(R.drawable.ic_staar);
                    del.setVisibility(View.VISIBLE);
                }
                else{
                    del.setVisibility(View.GONE);
                    addFav.setImageResource((R.drawable.ic_starout));
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // add.setVisibility(View.INVISIBLE);
                DatabaseReference ref = firebaseDatabase.getReference().child("Comment").child(name);
                String comment_content = com.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                //String uimg = firebaseUser.getPhotoUrl().toString();
                String s = com.getText().toString().trim() ;
                if(s.isEmpty()){
                    com.setError("Vous n'avez rien écrit !");
                    com.requestFocus();

                    return;
                }
                String timestampComment = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
                String idComment = timestampComment+"_"+uname;
                Comment comment = new Comment(comment_content,uid,null,uname,name,timestampComment, firebaseUser.getUid());

                ref.child(idComment).setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //showMessage("comment added");
                        com.setText("");
                        add.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : "+e.getMessage());
                    }
                });

                DatabaseReference commentRef = firebaseDatabase.getReference().child("Comment").child(name);
                commentRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listComment = new ArrayList<>();
                        for (DataSnapshot snap:dataSnapshot.getChildren()) {
                            Comment commente = snap.getValue(Comment.class);
                            listComment.add(commente) ;
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

        DatabaseReference current_user = firebaseDatabase.getReference("Users").child(mAuth.getUid());
        String username = current_user.child("fullName").toString();
        DatabaseReference users = firebaseDatabase.getReference("Users");

        // Receiving value into activity using intent.
        // variable contient l'url

        // Setting up received value into EditText.
        beerName.setText(name);
        beerDesc.setText(desc);

        Glide.with(vueimg.getContext()).load(utlImg).placeholder(R.drawable.bieresimple).into(vueimg);

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
                HashMap<String,Object> new_rate = new HashMap<>();
                new_rate.put(name,Float.toString(rating));

                //if beer
                current_user.child("user_rated_beers").updateChildren(new_rate);
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
                    ratingText.setText("Aucun vote enregistré");


                }
                else {
                    String moy = String.format("%.1f",moyenne);
                    ratingText.setText("Note moyenne : " + moy);
                    firebaseDatabase.getReference().child("Beers").child(name).child("global_rating").setValue(moyenne);

                }if(!snapshot.child(mAuth.getUid()).child("Favoris").hasChild(name)){
                    firebaseDatabase.getReference("Users").child(mAuth.getUid()).child("Favoris").child(name).removeValue();

                }
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
                        intent = new Intent(getApplicationContext(), activity_favoris.class);
                        break;
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
        String date = DateFormat.format("'le' dd/MM/yyyy 'à' HH:mm",calendar).toString();
        return date;
    }
}
