package com.abben.yunziyuanesr.util;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

/**
 * Created by Shaolin on 2017/5/18.
 */

public class FolderUtil {

    public static String getAppCacheDir() {

        String cacheDirectory = Environment.getExternalStorageDirectory() +"";
        long cacheSpace = getLeftSpace(cacheDirectory) / 1024 / 1024;
        if (cacheSpace > 30) {
            return cacheDirectory;
        }else{
            return null;
        }
    }

    /**
     * 获取指定目录剩余存储空间，返回单位为字节
     * @param directory
     * @return
     */
    private static long getLeftSpace(String directory) {
        if(TextUtils.isEmpty(directory)) return 0;

        long space = 0;
        try {
            StatFs sf = new StatFs(directory);
            space = (long)sf.getBlockSize() * sf.getAvailableBlocks();
        } catch (Exception ex) {
            return 0;
        }

        return space;
    }
}
