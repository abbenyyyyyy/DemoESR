package com.abben.mvvmsamplejetpack.util

import android.os.SystemClock
import androidx.collection.ArrayMap
import java.util.concurrent.TimeUnit

/**
 * 决定是否应该获取一些数据的Utility类
 * 根据现在离上一次获取数据的时间来决定
 *  @author abben
 *  on 2019/1/18
 */
class RateLimiter<KEY>(timeout: Int, timeUnit: TimeUnit) {
    private val timestamps: ArrayMap<KEY, Long> = ArrayMap()
    private val timeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = timestamps[key]
        val now = now()
        return if (lastFetched == null) {
            timestamps[key] = now
            true
        } else {
            if (now - lastFetched > timeout) {
                timestamps[key] = now
                true
            } else {
                false
            }
        }
    }

    private fun now(): Long {
        return SystemClock.uptimeMillis()
    }

    fun reset(key: KEY) {
        timestamps.remove(key)
    }

}