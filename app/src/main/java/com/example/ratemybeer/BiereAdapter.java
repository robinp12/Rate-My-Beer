package com.example.ratemybeer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BiereAdapter extends ArrayAdapter<Biere> {
    private Context mContext;
    private int mRessource;

    public BiereAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Biere> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mRessource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        convertView=layoutInflater.inflate(mRessource,parent,false);
        ImageView imageView=convertView.findViewById(R.id.image);
        TextView txtTitle=convertView.findViewById(R.id.title);
        TextView description=convertView.findViewById(R.id.description);
        imageView.setImageResource(getItem(position).getImage());
        txtTitle.setText(getItem(position).getName());
        description.setText(getItem(position).getDescription());

        return convertView;

    }
}
