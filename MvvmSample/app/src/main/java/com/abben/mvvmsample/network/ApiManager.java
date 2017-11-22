package com.abben.mvvmsample.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abben on 2017/5/8.
 */

public class ApiManager {
    private static final String BASE_URL = "http://abben-version.oss-cn-shenzhen.aliyuncs.com/";
    private static Retrofit retrofit;

    private static MoviesApi moviesApi;


    public static Retrofit getRetrofit(){
        if(retrofit == null){
            synchronized (Retrofit.class) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(OkHttpHelper.getOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                        .build();
            }
        }
        return retrofit;
    }

    public static MoviesApi getMoviesApi(){
        if(moviesApi == null){
            synchronized (MoviesApi.class){
                moviesApi = getRetrofit().create(MoviesApi.class);
            }
        }
        return moviesApi;
    }
}
