package com.abben.mvvmsample.util;

import android.os.SystemClock;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.TimeUnit;

/**
 * 决定是否应该获取一些数据的Utility类
 * 根据现在离上一次获取数据的时间来决定
 * Created by abben on 2017/10/25.
 */
public class RateLimiter<KEY> {
    private ArrayMap<KEY, Long> timestamps = new ArrayMap<>();
    private final long timeout;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch(KEY key) {
        Long lastFetched = timestamps.get(key);
        long now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    private long now() {
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(KEY key) {
        timestamps.remove(key);
    }
}
