package com.aisautocare.aisautocare.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aisautocare.aisautocare.R;
import com.aisautocare.aisautocare.adapter.VehicleRecyclerViewAdapter;
import com.aisautocare.aisautocare.model.Vehicle;

import java.util.ArrayList;

public class GarageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private VehicleRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Floating action button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, AddVehicleActivity.class);
//                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(new Vehicle("Ford", "Mustang GT", "MT", "2015"));
        vehicles.add(new Vehicle("Toyota", "Yaris", "AT", "2016"));
        vehicles.add(new Vehicle("Kawasaki", "Ninja", "MT", "2016"));

        adapter = new VehicleRecyclerViewAdapter(this, vehicles);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }
}
