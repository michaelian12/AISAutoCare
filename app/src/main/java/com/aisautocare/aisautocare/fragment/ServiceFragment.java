package com.aisautocare.aisautocare.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aisautocare.aisautocare.R;
import com.aisautocare.aisautocare.adapter.ServiceRecyclerViewAdapter;
import com.aisautocare.aisautocare.model.Service;

import java.util.ArrayList;

/**
 * Created by Michael on 1/5/2017.
 */

public class ServiceFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ServiceRecyclerViewAdapter adapter;

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_service, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);



        // Create a list of vehicles
        ArrayList<Service> services = new ArrayList<Service>();
        services.add(new Service(0, "Air Conditioning"));
        services.add(new Service(0, "Battery"));
        services.add(new Service(0, "Brake Fluid"));
        services.add(new Service(0, "Engine Repair"));
        services.add(new Service(0, "Exhaust System"));
        services.add(new Service(0, "Fuel Filter"));
        services.add(new Service(0, "New Tires"));
        services.add(new Service(0, "Oil Change"));
        services.add(new Service(0, "Radiator"));
        services.add(new Service(0, "Steering System"));
        services.add(new Service(0, "Suspension System"));
        services.add(new Service(0, "Wheel Alignment"));

        // Create an {@link ServiceRecyclerViewAdapter}, whose data source is a list of {@link Application}s. The
        // adapter knows how to create list items for each item in the list.
        adapter = new ServiceRecyclerViewAdapter(getActivity(), services);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}
