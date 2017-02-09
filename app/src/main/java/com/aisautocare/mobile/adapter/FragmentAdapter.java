package com.aisautocare.mobile.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.androidhive.firebasenotifications.R;
import com.aisautocare.mobile.fragment.CareFragment;
import com.aisautocare.mobile.fragment.EmergencyFragment;
import com.aisautocare.mobile.fragment.RepairFragment;

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
        }
//        else if (position == 1){
//            return new RepairFragment();
//        } else {
//            return new EmergencyFragment();
//        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.fragment_service);
        }
//        else if (position == 1){
//            return context.getString(R.string.fragment_repair);
//        } else {
//            return context.getString(R.string.fragment_emergency);
//        }
        return null;
    }
}
