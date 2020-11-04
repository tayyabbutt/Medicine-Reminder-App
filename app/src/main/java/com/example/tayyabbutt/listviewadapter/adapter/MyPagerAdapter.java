package com.example.tayyabbutt.listviewadapter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tayyabbutt.listviewadapter.fragment.NotTakenMedicineListFragment;
import com.example.tayyabbutt.listviewadapter.fragment.RecyclerFragment;
import com.example.tayyabbutt.listviewadapter.fragment.UpcomingAlarmListing;

/**
 * Created by Tayyab Butt on 2/12/2018.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private final String[] PAGE_TITLES = new String[]{
            "Listing",
            "Upcoming",
            "Not Taken",

    };


    private final Fragment[] PAGES = new Fragment[]{
            new RecyclerFragment(),
            new UpcomingAlarmListing(),
            new NotTakenMedicineListFragment()

    };

    public MyPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return PAGES[position];
    }

    @Override
    public int getCount() {
        return PAGES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }

}
