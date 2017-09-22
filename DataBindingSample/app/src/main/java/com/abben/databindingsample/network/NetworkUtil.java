package com.abben.databindingsample.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abben on 2017/5/9.
 */
public class NetworkUtil {
    private static final String TAG = "NetworkUtil";
    public static int NET_CNNT_BAIDU_OK = 1; // NetworkAvailable
    public static int NET_CNNT_BAIDU_TIMEOUT = 2; // no NetworkAvailable
    public static int NET_NOT_PREPARE = 3; // Net no ready
    public static int NET_ERROR = 4; //net error
    private static int TIMEOUT = 3000; // TIMEOUT
    /**
     * 返回当前网络状态
     *
     * @param context
     * @return
     */
    public static int getNetState(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
                if (networkinfo != null) {
                    if (networkinfo.isAvailable() && networkinfo.isConnected()) {
                        if (!connectionNetwork())
                            return NET_CNNT_BAIDU_TIMEOUT;
                        else
                            return NET_CNNT_BAIDU_OK;
                    } else {
                        return NET_NOT_PREPARE;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NET_ERROR;
    }

    /**
     *ping "http://www.baidu.com"
     * @return
     */
    static private boolean connectionNetwork() {
        boolean result = false;
        HttpURLConnection httpUrl = null;
        try {
            httpUrl = (HttpURLConnection) new URL("http://www.baidu.com")
                    .openConnection();
            httpUrl.setConnectTimeout(TIMEOUT);
            httpUrl.connect();
            result = true;
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        } finally {
            if (null != httpUrl) {
                httpUrl.disconnect();
            }
            httpUrl = null;
        }
        return result;
    }
}
