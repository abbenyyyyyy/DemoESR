package com.abben.mvvmsample.network;

import android.util.Log;

import com.abben.mvvmsample.constant.GlobalConstants;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by abben on 2017/5/8.
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request compressedRequest = request.newBuilder()
                .header("cookie", "")
                .header("User-Agent", "")
                .build();
        if (GlobalConstants.SHOW_DIALOG) {
            RequestBody requestBody = compressedRequest.body();
            String body = null;
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(Charset.forName("UTF-8"));
                }
                body = buffer.readString(charset);
            }
            Log.i("MvvmSample", "\n请求method:"
                    + String.format(Locale.getDefault(), "%s\n请求url：%s\n请求headers: %s\n请求body：%s",
                    compressedRequest.method(), compressedRequest.url(), compressedRequest.headers(), body));
        }
        return chain.proceed(compressedRequest);
    }
}
