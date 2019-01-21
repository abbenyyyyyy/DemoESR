package com.abben.mvvmsamplejetpack.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 *  @author abben
 *  on 2019/1/18
 */
object OkHttpHelper {
    /**
     * 连接超时
     */
    private const val CONNECT_TIMEOUT: Long = 15
    /**
     * 读取超时
     */
    private const val READ_TIMEOUT: Long = 25
    /**
     * 写入超时
     */
    private const val WRITE_TIMEOUT: Long = 25

    private var okHttpClient: OkHttpClient? = null

    fun getOkHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new HeaderInterceptor()) //添加 header
//                .sslSocketFactory(customHttpsTrust.sSLSocketFactory, customHttpsTrust.x509TrustManager)// https 配置
                .build()
        }
        return okHttpClient!!
    }
}