package com.abben.mvp_sample;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.abben.mvp_sample.bean.UpdateBean;
import com.abben.mvp_sample.customview.CustomDialog;
import com.abben.mvp_sample.movies.fragment.AllMoviesFragment;
import com.abben.mvp_sample.movies.fragment.ChineseMoviesFragment;
import com.abben.mvp_sample.movies.fragment.EuramericanMoviesFragment;
import com.abben.mvp_sample.movies.fragment.JanpanAndKoreaMoviesFragment;
import com.abben.mvp_sample.movies.presenter.AllMoviesPresenter;
import com.abben.mvp_sample.movies.presenter.ChineseMoviesPresenter;
import com.abben.mvp_sample.movies.presenter.EuramericanMoviesPresenter;
import com.abben.mvp_sample.movies.presenter.JanpanAndKoreaMoviesPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityView{
    public final static String INTENT_MOVIE_FALG = "INTENT_MOVIE_FALG";

    private ViewPager main_viewpager;
    private TabLayout sliding_tabs;
    private MainActivityPresenter mMainActivityPresenter;
    private final int notifyId = 10086;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;

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
        mMainActivityPresenter = paesenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void initView() {
        main_viewpager = findViewById(R.id.main_viewpager);
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

        sliding_tabs = findViewById(R.id.sliding_tabs);
        sliding_tabs.setupWithViewPager(main_viewpager);
        sliding_tabs.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    public void showDialog(final UpdateBean updateBean) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        if(updateBean.getChangelog() != null && !updateBean.getChangelog().equals("")){
            builder.setMessage(updateBean.getChangelog() + "\n" + getString(R.string.update_message));
        }else{
            builder.setMessage(getString(R.string.update_message));
        }
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mMainActivityPresenter.startDownload(updateBean.getInstallUrl());
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }


    @Override
    public void startNotification() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(getString(R.string.downloading));
        builder.setSmallIcon(R.mipmap.app_icon);
        builder.setProgress(100, 0, false);
        mNotificationManager.notify(notifyId, builder.build());
    }

    @Override
    public void notification(int progress) {
        builder.setProgress(100, progress, false);
        mNotificationManager.notify(notifyId, builder.build());
    }

    @Override
    public void cancelNotification() {
        mNotificationManager.cancel(notifyId);
    }

    @Override
    public void showTip(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }
}
