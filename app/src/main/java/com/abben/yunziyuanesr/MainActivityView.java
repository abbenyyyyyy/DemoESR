package com.abben.yunziyuanesr;

import android.content.Context;

/**
 * Created by Shaolin on 2017/5/16.
 */

public interface MainActivityView {
    void setPaesenter(MainActivityPresenter paesenter);

    Context getContext();

    void initView();

    void showDialog();
}
