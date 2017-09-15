package com.example.yiyao.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2016-6-16.
 */
public class OneAdapter extends FragmentPagerAdapter {
    private final List<String> titles = new ArrayList<>();
    private final List<Fragment> fragments = new ArrayList<>();

    public OneAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public void addHomeFragment(String title, Fragment fragment) {
        titles.add(title);
        fragments.add(fragment);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
