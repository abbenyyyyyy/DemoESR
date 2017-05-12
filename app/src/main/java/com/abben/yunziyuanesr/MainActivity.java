package com.abben.yunziyuanesr;

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
import com.abben.yunziyuanesr.network.CustomApi;
import com.abben.yunziyuanesr.network.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ViewPager main_viewpager;
    private TabLayout sliding_tabs;
    public final static String INTENT_MOVIE_FALG = "INTENT_MOVIE_FALG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
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

        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        main_viewpager.setAdapter(customFragmentPagerAdapter);

        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        sliding_tabs.setupWithViewPager(main_viewpager);
        sliding_tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    private void checkUpdate(){

        String firImUpdateUrl = "http://api.fir.im/apps/latest/591563c5959d69373400003f?api_token=f5c0e4f1f9f642e9a5ab657903825660";
        RetrofitHelper.getRetrofit().create(CustomApi.class).fetchStringFromUrl(firImUpdateUrl)
        .subscribeOn(Schedulers.io())
        ;
    }
}
