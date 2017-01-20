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

public class CareFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ServiceRecyclerViewAdapter adapter;

    public CareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        ArrayList<Service> cares = new ArrayList<Service>();
        cares.add(new Service(R.drawable.ic_engine, "Wash", "Rp 40.000"));
        cares.add(new Service(R.drawable.ic_engine, "Wash & Wax", "Rp 60.000"));
        cares.add(new Service(R.drawable.ic_engine, "Wash, Wax & Window Treatment", "Rp 140.000"));

        adapter = new ServiceRecyclerViewAdapter(getActivity(), cares);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}
