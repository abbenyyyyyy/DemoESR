package com.abben.yunziyuanesr;

/**
 * Created by Administrator on 2017/5/8.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();

    void showTip(String msg);
}
