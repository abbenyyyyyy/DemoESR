package com.abben.yunziyuanesr.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/5/8.
 */

public class RetrofitHelper {
    //http://abben-version.oss-cn-shenzhen.aliyuncs.com/Movies.json
    //http://abben-version.oss-cn-shenzhen.aliyuncs.com/ChineseMovies.json
    //http://abben-version.oss-cn-shenzhen.aliyuncs.com/EuramericanMovies.json
    //http://abben-version.oss-cn-shenzhen.aliyuncs.com/JanpanAndKoreaMovies.json
    private static final String baseUrl = "http://abben-version.oss-cn-shenzhen.aliyuncs.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(OkHttpHelper.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
