package com.aisautocare.aisautocare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aisautocare.aisautocare.R;
import com.aisautocare.aisautocare.adapter.ServiceRecyclerViewAdapter;
import com.aisautocare.aisautocare.model.Service;

import java.util.ArrayList;

/**
 * Created by Michael on 1/14/2017.
 */

public class RepairFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ServiceRecyclerViewAdapter adapter;

    public RepairFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        ArrayList<Service> repairs = new ArrayList<Service>();
        repairs.add(new Service(R.drawable.ic_engine, "Tune Up", "Rp 180.000"));
        repairs.add(new Service(R.drawable.ic_engine, "Ganti Oli", "Rp 45.000"));
        repairs.add(new Service(R.drawable.ic_engine, "Ganti Aki", "Gratis Ongkos Kirim"));

        adapter = new ServiceRecyclerViewAdapter(getActivity(), repairs);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}
