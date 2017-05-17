package com.abben.yunziyuanesr;

import com.abben.yunziyuanesr.bean.UpdateBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by abben on 2017/5/12.
 */

public interface MainActivityApi {

    @GET
    Observable<UpdateBean> fetchStringFromUrl(@Url String url);
}
