package com.abben.mvvmsample;

import android.os.Bundle;
import android.view.View;

import com.abben.mvvmsample.databinding.ActivityMainBinding;
import com.abben.mvvmsample.ui.movies.MoviesFragment;
import com.abben.mvvmsample.vo.TypeMovies;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    public final static String INTENT_MOVIE_FALG = "INTENT_MOVIE_FALG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
