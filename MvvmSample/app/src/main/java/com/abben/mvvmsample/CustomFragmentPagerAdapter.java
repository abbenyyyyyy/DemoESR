package com.abben.mvvmsample;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by abben on 2017/5/3.
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private String[] pageTitles = {"所有电影", "欧美电影", "日韩电影", "国产电影"};

    public CustomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
