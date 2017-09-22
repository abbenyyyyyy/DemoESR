package com.abben.databindingsample;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abben.databindingsample.databinding.ActivityMainBinding;
import com.abben.databindingsample.movies.AllMoviesFragment;
import com.abben.databindingsample.movies.ChineseMoviesFragment;
import com.abben.databindingsample.movies.EuramericanMoviesFragment;
import com.abben.databindingsample.movies.JanpanAndKoreaMoviesFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String INTENT_MOVIE_FALG = "INTENT_MOVIE_FALG";

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initView();
    }

    private void initView() {

        ArrayList<Fragment> fragments = new ArrayList<>();

        AllMoviesFragment allMoviesFragment = new AllMoviesFragment();
        fragments.add(allMoviesFragment);
        EuramericanMoviesFragment euramericanMoviesFragment = new EuramericanMoviesFragment();
        fragments.add(euramericanMoviesFragment);
        JanpanAndKoreaMoviesFragment janpanAndKoreaMoviesFragment = new JanpanAndKoreaMoviesFragment();
        fragments.add(janpanAndKoreaMoviesFragment);
        ChineseMoviesFragment chineseMoviesFragment = new ChineseMoviesFragment();
        fragments.add(chineseMoviesFragment);

        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        activityMainBinding.mainViewpager.setAdapter(customFragmentPagerAdapter);

        activityMainBinding.slidingTabs.setupWithViewPager(activityMainBinding.mainViewpager);
        activityMainBinding.slidingTabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
