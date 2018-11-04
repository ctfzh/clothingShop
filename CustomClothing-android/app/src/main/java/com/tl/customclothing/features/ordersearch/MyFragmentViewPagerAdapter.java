package com.tl.customclothing.features.ordersearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tl.customclothing.features.ordersearch.pager.OrderPageFragment;

import java.util.List;

/**
 * Created by tianlin on 2017/4/12.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter
{
    List<OrderPageFragment> fragments;

    List<String> titles;

    public MyFragmentViewPagerAdapter(FragmentManager fm, List<OrderPageFragment> fragments, List<String> titles)
    {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles.get(position);
    }
}
