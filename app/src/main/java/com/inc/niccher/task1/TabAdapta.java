package com.inc.niccher.task1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by niccher on 8/22/19.
 */

public class TabAdapta extends FragmentStatePagerAdapter{

    String[] posttype=new String[]{"Vehicles","Real Estates"};

    public TabAdapta(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return posttype[position];
        //return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
