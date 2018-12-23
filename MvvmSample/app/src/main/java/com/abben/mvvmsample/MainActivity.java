package com.abben.mvvmsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abben.mvvmsample.databinding.ActivityMainBinding;
import com.abben.mvvmsample.ui.movies.MoviesFragment;
import com.abben.mvvmsample.vo.TypeMovies;

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
        fragments.add(MoviesFragment.create(TypeMovies.TYPE_ALL_MOVIES_ZH));
        fragments.add(MoviesFragment.create(TypeMovies.TYPE_EURAMERICAN_MOVIES_ZH));
        fragments.add(MoviesFragment.create(TypeMovies.TYPE_JANPAN_AND_KOREA_MOVIES_ZH));
        fragments.add(MoviesFragment.create(TypeMovies.TYPE_CHINESE_MOVIES_ZH));

        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager()
                , fragments);
        activityMainBinding.mainViewpager.setAdapter(customFragmentPagerAdapter);

        activityMainBinding.slidingTabs.setupWithViewPager(activityMainBinding.mainViewpager);
        activityMainBinding.slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        activityMainBinding.slidingTabs.setVisibility(View.VISIBLE);
    }

}
