package com.abben.mvp_sample;

import android.content.Context;

import com.abben.mvp_sample.bean.UpdateBean;

/**
 * Created by abben on 2017/5/16.
 */

public interface MainActivityView {
    void setPaesenter(MainActivityPresenter paesenter);

    Context getContext();

    void initView();

    /**
     * 开始通知栏通知下载进度
     */
    void startNotification();

    /**
     * 更新通知栏下载进度
     * @param progress 下载进度
     */
    void notification(int progress);

    /**
     * 移除通知栏
     */
    void cancelNotification();

    void showDialog(UpdateBean updateBean);

    void showTip(String msg);
}
