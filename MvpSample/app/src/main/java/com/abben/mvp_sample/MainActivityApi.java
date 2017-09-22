package com.abben.mvp_sample;

import com.abben.mvp_sample.bean.UpdateBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by abben on 2017/5/12.
 */

public interface MainActivityApi {

    @GET
    Observable<UpdateBean> fetchStringFromUrl(@Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String apkName);
}
