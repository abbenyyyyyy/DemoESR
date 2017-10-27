package com.abben.mvvmsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abben.mvvmsample.databinding.ActivityMainBinding;
import com.abben.mvvmsample.movies.AllMoviesFragment;
import com.abben.mvvmsample.ui.CustomFragmentPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String INTENT_MOVIE_FALG = "INTENT_MOVIE_FALG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initView(activityMainBinding);
    }

    private void initView(ActivityMainBinding activityMainBinding) {

        ArrayList<Fragment> fragments = new ArrayList<>();

        AllMoviesFragment allMoviesFragment = new AllMoviesFragment();
        fragments.add(allMoviesFragment);

        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        activityMainBinding.mainViewpager.setAdapter(customFragmentPagerAdapter);

        activityMainBinding.slidingTabs.setupWithViewPager(activityMainBinding.mainViewpager);
        activityMainBinding.slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        activityMainBinding.slidingTabs.setVisibility(View.VISIBLE);

    }

}
