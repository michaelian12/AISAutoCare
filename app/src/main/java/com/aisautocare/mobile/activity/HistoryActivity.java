package com.aisautocare.mobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.aisautocare.mobile.adapter.GarageRecyclerViewAdapter;
import com.aisautocare.mobile.adapter.HistoryRecyclerViewAdapter;
import com.aisautocare.mobile.model.Garage;
import com.aisautocare.mobile.model.History;

import java.util.ArrayList;

import info.androidhive.firebasenotifications.R;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private HistoryRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ArrayList<History> histories = new ArrayList<History>();
        histories.add(new History(R.drawable.ic_car, "2 Februari 2017", "Rp 100.000", "Ford Fiesta", "Derek"));
        histories.add(new History(R.drawable.ic_car, "26 Januari 2017", "Rp 80.000", "Ford Fiesta", "Ganti Aki"));
        histories.add(new History(R.drawable.ic_bike, "14 Januari 2017", "Rp 35.000", "Kawasaki Ninja", "Cuci / Salon"));

        adapter = new HistoryRecyclerViewAdapter(this, histories);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }
}
