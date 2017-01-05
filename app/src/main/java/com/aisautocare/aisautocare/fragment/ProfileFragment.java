package com.aisautocare.aisautocare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aisautocare.aisautocare.R;

/**
 * Created by Michael on 1/5/2017.
 */

public class ProfileFragment extends Fragment {

    private View rootView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        return rootView;
    }
}
