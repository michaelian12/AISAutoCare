package com.aisautocare.aisautocare.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aisautocare.aisautocare.fragment.ProfileFragment;
import com.aisautocare.aisautocare.fragment.ServiceFragment;
import com.aisautocare.aisautocare.fragment.HistoryFragment;
import com.aisautocare.aisautocare.fragment.VehicleFragment;
import com.aisautocare.aisautocare.R;

/**
 * Created by Michael on 1/5/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ServiceFragment();
        } else if (position == 1){
            return new VehicleFragment();
        } else if (position == 2) {
            return new HistoryFragment();
        } else {
            return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.fragment_service);
        } else if (position == 1){
            return mContext.getString(R.string.fragment_vehicle);
        } else if (position == 2) {
            return mContext.getString(R.string.fragment_history);
        } else {
            return mContext.getString(R.string.fragment_profile);
        }
    }
}
