package com.example.ratemybeer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        listView=findViewById(R.id.listView);

        //Create Data
        ArrayList<Biere>arrayList=new ArrayList<>();
        arrayList.add(new Biere(R.drawable.jupiler,"Jupiler","Ceci est une Jupiler"));
        arrayList.add(new Biere(R.drawable.maes,"Maes","Ceci est une Maes"));
        arrayList.add(new Biere(R.drawable.leffe,"Leffe","Ceci est une Leffe"));
        BiereAdapter biereAdapter=new BiereAdapter(this,R.layout.list_row,arrayList);
        listView.setAdapter(biereAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 
            }
        });

    }
}