package com.runtips.ricardo.runtipsmx.Classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.runtips.ricardo.runtipsmx.Fragments.FreeStartFragment;
import com.runtips.ricardo.runtipsmx.Fragments.PaidStartFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public PagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FreeStartFragment();
            case 1:
                return new PaidStartFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
