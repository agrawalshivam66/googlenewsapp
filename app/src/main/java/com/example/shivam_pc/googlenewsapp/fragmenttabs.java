package com.example.shivam_pc.googlenewsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Shivam-PC on 23-03-2018.
 */

public class fragmenttabs extends FragmentPagerAdapter {

    private Context mContext;

    public fragmenttabs(Context context,FragmentManager fm) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new topstoriesfragments();
        }
        else if(position==1) {
            return new WorldFragment();

        }
        else if(position==2) {
            return new TechnologyFragment();
        }
        else if(position==3)
        {
            return new Entertairmentfragment();
        }
        else
        {
            return new SportsFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Top Story";
        }
        else if(position==1){
            return "World";
        }
        else if(position==2) {
            return "TECH";
        }
        else if (position==3)
        {
            return "TV";
        }
        else
        {
            return "Sports";
        }
    }
}
