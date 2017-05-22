package com.abben.yunziyuanesr;

import android.content.Context;

import com.abben.yunziyuanesr.bean.UpdateBean;

/**
 * Created by Shaolin on 2017/5/16.
 */

public interface MainActivityView {
    void setPaesenter(MainActivityPresenter paesenter);

    Context getContext();

    void initView();

    /**开始通知栏通知下载进度*/
    void startNotification();

    /**更新通知栏下载进度*/
    void notification(int progress);

    /**移除通知栏*/
    void cancelNotification();

    void showDialog(UpdateBean updateBean);

    void showTip(String msg);
}
