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
    @Volatile
    private var retrofit: Retrofit? = null
    @Volatile
    private var moviesApi: MoviesApi? = null

    private fun getRetrofit(): Retrofit {
        return retrofit ?: synchronized(this) {
            retrofit ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHelper.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
        }
    }

    fun getMoviesApi(): MoviesApi {
        return moviesApi ?: synchronized(this) {
            moviesApi ?: getRetrofit().create(MoviesApi::class.java)
        }
    }
}