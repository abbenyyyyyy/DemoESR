package com.abben.mvvmsamplejetpack.network

import android.util.Log
import com.abben.mvvmsamplejetpack.constant.GlobalConstants
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.*

/**
 *  @author abben
 *  on 2019/1/18
 */
class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val compressedRequest = request.newBuilder()
            .header("cookie", "")
            .header("User-Agent", "")
            .build()

        if (GlobalConstants.SHOW_DIALOG) {
            val requestBody = compressedRequest.body()
            var body: String? = null
            if (requestBody != null) {
                var buffer = Buffer()
                requestBody.writeTo(buffer)
                var charset = Charset.forName("UTF-8")
                var contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(Charset.forName("UTF-8"))
                }
                body = buffer.readString(charset)
            }
            Log.i(
                "MvvmSample", "\n请求method:"
                        + String.format(
                    Locale.getDefault(), "%s\n请求url：%s\n请求headers: %s\n请求body：%s",
                    compressedRequest.method(), compressedRequest.url(), compressedRequest.headers(), body
                )
            );
        }
        return chain.proceed(compressedRequest)
    }
}