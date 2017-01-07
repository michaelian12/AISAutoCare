package com.aisautocare.aisautocare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aisautocare.aisautocare.activity.AddVehicleActivity;
import com.aisautocare.aisautocare.adapter.VehicleRecyclerViewAdapter;
import com.aisautocare.aisautocare.model.Vehicle;
import com.aisautocare.aisautocare.R;

import java.util.ArrayList;

/**
 * Created by Michael on 1/5/2017.
 */

public class VehicleFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private VehicleRecyclerViewAdapter adapter;
    private FloatingActionButton fab;

    public VehicleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_vehicle, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // Floating action button
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        // Create a list of vehicles
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(new Vehicle(0, "Ford", "Mustang", "MT", "1000 cc", "2016"));
        vehicles.add(new Vehicle(0, "Honda", "Civic", "AT", "1000 cc",  "2015"));
        vehicles.add(new Vehicle(0, "Toyota", "Fortuner", "MT", "1000 cc",  "2016"));

        // Create an {@link VehicleRecyclerViewAdapter}, whose data source is a list of {@link Application}s. The
        // adapter knows how to create list items for each item in the list.
        adapter = new VehicleRecyclerViewAdapter(getActivity(), vehicles);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}
