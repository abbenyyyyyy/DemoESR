package com.abben.mvvmsamplejetpack.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  @author abben
 *  on 2019/1/18
 */
object ApiManager {

    private const val BASE_URL = "http://abben-version.oss-cn-shenzhen.aliyuncs.com/"
    private var retrofit: Retrofit? = null
    private var moviesApi: MoviesApi? = null

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHelper.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
        }
        return retrofit!!
    }

    fun getMoviesApi(): MoviesApi {
        if (moviesApi == null) {
            moviesApi = getRetrofit().create(MoviesApi::class.java)
        }
        return moviesApi!!
    }
}