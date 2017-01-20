package com.aisautocare.aisautocare.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aisautocare.aisautocare.R;
import com.aisautocare.aisautocare.fragment.CareFragment;
import com.aisautocare.aisautocare.fragment.EmergencyFragment;
import com.aisautocare.aisautocare.fragment.RepairFragment;

/**
 * Created by Michael on 1/5/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context context;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CareFragment();
        } else if (position == 1){
            return new RepairFragment();
        } else {
            return new EmergencyFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.fragment_care);
        } else if (position == 1){
            return context.getString(R.string.fragment_repair);
        } else {
            return context.getString(R.string.fragment_emergency);
        }
    }
}
