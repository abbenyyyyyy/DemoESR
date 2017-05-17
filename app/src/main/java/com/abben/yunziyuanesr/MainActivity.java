package com.abben.yunziyuanesr;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abben.yunziyuanesr.movies.fragment.AllMoviesFragment;
import com.abben.yunziyuanesr.movies.fragment.ChineseMoviesFragment;
import com.abben.yunziyuanesr.movies.fragment.EuramericanMoviesFragment;
import com.abben.yunziyuanesr.movies.fragment.JanpanAndKoreaMoviesFragment;
import com.abben.yunziyuanesr.movies.presenter.AllMoviesPresenter;
import com.abben.yunziyuanesr.movies.presenter.ChineseMoviesPresenter;
import com.abben.yunziyuanesr.movies.presenter.EuramericanMoviesPresenter;
import com.abben.yunziyuanesr.movies.presenter.JanpanAndKoreaMoviesPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityView{
    public final static String INTENT_MOVIE_FALG = "INTENT_MOVIE_FALG";

    private ViewPager main_viewpager;
    private TabLayout sliding_tabs;
    private MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActivityPresenter = new MainActivityPresenter(this);
        mMainActivityPresenter.checkUpdate(3, 15);
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainActivityPresenter.clearCompositeDisposable();
    }

    @Override
    public void setPaesenter(MainActivityPresenter paesenter) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void initView() {
        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        ArrayList<Fragment> fragments = new ArrayList<>();

        AllMoviesFragment allMoviesFragment = new AllMoviesFragment();
        new AllMoviesPresenter(allMoviesFragment);
        fragments.add(allMoviesFragment);
        EuramericanMoviesFragment euramericanMoviesFragment = new EuramericanMoviesFragment();
        new EuramericanMoviesPresenter(euramericanMoviesFragment);
        fragments.add(euramericanMoviesFragment);
        JanpanAndKoreaMoviesFragment janpanAndKoreaMoviesFragment = new JanpanAndKoreaMoviesFragment();
        new JanpanAndKoreaMoviesPresenter(janpanAndKoreaMoviesFragment);
        fragments.add(janpanAndKoreaMoviesFragment);
        ChineseMoviesFragment chineseMoviesFragment = new ChineseMoviesFragment();
        new ChineseMoviesPresenter(chineseMoviesFragment);
        fragments.add(chineseMoviesFragment);

        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        main_viewpager.setAdapter(customFragmentPagerAdapter);

        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        sliding_tabs.setupWithViewPager(main_viewpager);
        sliding_tabs.setTabMode(TabLayout.MODE_FIXED);
    }




    @Override
    public void showDialog() {

    }


}
