package com.aisautocare.aisautocare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aisautocare.aisautocare.adapter.HistoryRecyclerViewAdapter;
import com.aisautocare.aisautocare.model.History;
import com.aisautocare.aisautocare.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Michael on 1/5/2017.
 */

public class HistoryFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private HistoryRecyclerViewAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // Create a list of histories
        ArrayList<History> histories = new ArrayList<History>();
        histories.add(new History(0, "New Tires", "Changing all 4 tires", "2-2-16", "11:11"));
        histories.add(new History(0, "Battery", "Battery replacement", "2-2-16", "11:11"));
        histories.add(new History(0, "Oil Change", "Oil replacement", "2-2-16", "11:11"));

        // Create an {@link HistoryRecyclerViewAdapter}, whose data source is a list of {@link Application}s. The
        // adapter knows how to create list items for each item in the list.
        adapter = new HistoryRecyclerViewAdapter(getActivity(), histories);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
