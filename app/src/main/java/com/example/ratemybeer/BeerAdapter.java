package com.example.ratemybeer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ratemybeer.Biere;
import com.example.ratemybeer.R;

import java.util.List;

public class BeerAdapter extends BaseAdapter {

    private Activity_Timeline context;
    private List<Biere> listeBiere;
    private LayoutInflater inflater;

    //constructeur
    public BeerAdapter(Activity_Timeline context, List<Biere> listeBiere){
        this.context = context;
        this.listeBiere = listeBiere;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listeBiere.size();
    }

    @Override
    public Biere getItem(int position) {
        return listeBiere.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.beer_element, null);
        Biere currentBeer = getItem(position);
        String beerName = currentBeer.getName();
        String beerAlcohol = currentBeer.getAlcohol();

        TextView beerNameView = view.findViewById(R.id.beer_info);
        beerNameView.setText(beerName);

        TextView beerAlcoholView = view.findViewById(R.id.textView);
        beerAlcoholView.setText(beerAlcohol);
        return view;
    }
}
