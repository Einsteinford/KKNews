package com.einsteinford.kknews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.einsteinford.kknews.ui.NewsListFragment;

import java.util.List;

/**
 * Created by KK on 2016-10-27.
 */

public class NewsPagerAdapter extends FragmentStatePagerAdapter {
    private List<NewsListFragment> fragments;
    private List<String> titles;


    public NewsPagerAdapter(FragmentManager fm, List<NewsListFragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
