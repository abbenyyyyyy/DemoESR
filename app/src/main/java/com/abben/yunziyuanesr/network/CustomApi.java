package com.abben.yunziyuanesr.network;

import com.abben.yunziyuanesr.bean.UpdateBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by abben on 2017/5/12.
 */

public interface CustomApi {

    @GET
    Observable<UpdateBean> fetchStringFromUrl(@Url String url);
}
