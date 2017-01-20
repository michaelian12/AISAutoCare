package com.aisautocare.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.firebasenotifications.R;
import com.aisautocare.mobile.adapter.ServiceRecyclerViewAdapter;
import com.aisautocare.mobile.model.Service;

import java.util.ArrayList;

/**
 * Created by Michael on 1/14/2017.
 */

public class EmergencyFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ServiceRecyclerViewAdapter adapter;

    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        ArrayList<Service> emergencies = new ArrayList<Service>();
        emergencies.add(new Service(R.drawable.ic_engine, "Towing", "Rp 450.000"));
        emergencies.add(new Service(R.drawable.ic_engine, "Battery Jumper", "Rp 70.000"));
        emergencies.add(new Service(R.drawable.ic_engine, "Spare Tire Change", "Rp 70.000"));

        adapter = new ServiceRecyclerViewAdapter(getActivity(), emergencies);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}
