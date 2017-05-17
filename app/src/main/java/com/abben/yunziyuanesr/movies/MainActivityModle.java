package com.abben.yunziyuanesr.movies;

import com.abben.yunziyuanesr.MainActivityApi;
import com.abben.yunziyuanesr.bean.UpdateBean;
import com.abben.yunziyuanesr.network.RetrofitHelper;

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
