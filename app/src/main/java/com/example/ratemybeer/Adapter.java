package com.example.ratemybeer;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter implements Filterable {
    private activity_favoris con ;

    private List<Biere> listeBiere;
    private LayoutInflater inflater;


    private List<Biere> originalBeers = null;
    private List<Biere> filteredBeers = null;


    //constructeur
    public Adapter(activity_favoris con , List<Biere> listeBiere) {
        this.con = con;
        this.listeBiere = listeBiere;
        this.inflater = LayoutInflater.from(con);
        FirebaseAuth mAuth;

        this.filteredBeers = listeBiere;
        this.originalBeers = listeBiere;

    }
    @Override
    public int getCount() {
        return filteredBeers.size();
    }

    @Override
    public Biere getItem(int position) {
        return filteredBeers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.beer_element, null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Biere currentBeer = getItem(position);
        String urlImg = currentBeer.getImg();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        DatabaseReference isAdmin = database.child("Users").child(firebaseUser.getUid()).child("isAdmin");

        TextView beerNameView = view.findViewById(R.id.beer_info);
        TextView beerAlcoholView = view.findViewById(R.id.textView);
        ImageView beerPicture = view.findViewById(R.id.imageView4);
        TextView beerOrigin = view.findViewById(R.id.textView8);
        TextView global_rating = view.findViewById(R.id.Grating);
        Button delButton = view.findViewById(R.id.deletebutton);
        Button editButton = view.findViewById(R.id.editButton);



        beerNameView.setText(currentBeer.getName());
        beerAlcoholView.setText(currentBeer.getDegree()+"°");
        beerOrigin.setText("Origine : "+currentBeer.getOrigin());
        global_rating.setText("Note globale : "+currentBeer.getGlobal_rating());

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("Users").child(mAuth.getUid()).child("Favoris").child(currentBeer.getName()).removeValue();

                Toast.makeText(con.getApplicationContext(),"Vous avez supprimé " +currentBeer.getName()+" de votre liste favoris !", Toast.LENGTH_SHORT).show();
            }

        });

        isAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals("true")){
                    delButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.GONE);

                }
                else{
                    delButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Glide.with(beerPicture.getContext()).load(urlImg).placeholder(R.drawable.bieresimple).into(beerPicture);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("main activity", "beer clicked");
                String TempListViewClickedName = currentBeer.getName();
                String TempListViewClickedRegion = currentBeer.getOrigin();
                String TempListViewClickedDesc = currentBeer.getDescription();
                String img = currentBeer.getImg() ;

                Intent intent = new Intent(con.getApplicationContext(), Activity_Beer.class);


                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedName", TempListViewClickedName);
                intent.putExtra("ListViewClickedDesc", TempListViewClickedDesc);
                intent.putExtra("url",urlImg) ;
                //intent.putExtra("ListViewClickedRegion",TempListViewClickedRegion);

                con.startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = originalBeers.size();
                    filterResults.values = originalBeers;
                } else {
                    List<Biere> resultBiere = new ArrayList<Biere>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Biere beer : originalBeers) {
                        if (beer.getName().toLowerCase().contains(searchStr) || beer.getOrigin().toLowerCase().contains(searchStr) || beer.getAlcohol().toLowerCase().contains(searchStr)) {
                            resultBiere.add(beer);
                        }
                        filterResults.count = resultBiere.size();
                        filterResults.values = resultBiere;
                    }
                }

                return filterResults;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredBeers = (List<Biere>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}


