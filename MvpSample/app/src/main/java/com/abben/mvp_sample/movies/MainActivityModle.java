package com.abben.mvp_sample.movies;

import com.abben.mvp_sample.MainActivityApi;
import com.abben.mvp_sample.bean.UpdateBean;
import com.abben.mvp_sample.network.RetrofitHelper;

import io.reactivex.Observable;

/**
 * Created by Shaolin on 2017/5/16.
 */

public class MainActivityModle {
    private MainActivityApi mainActivityApi;

    public MainActivityModle(){
        mainActivityApi = RetrofitHelper.getRetrofit().create(MainActivityApi.class);
    }

    public Observable<UpdateBean> fetchUpdateBeanFromUrl(String url){
        return mainActivityApi.fetchStringFromUrl(url);
    }
}
