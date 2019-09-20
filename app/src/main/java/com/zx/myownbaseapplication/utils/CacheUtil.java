package com.zx.myownbaseapplication.utils;

import android.content.Context;
import android.os.Environment;

/**
 * @author zx 20190920 获得可用缓存目录
 * */
public class CacheUtil {

    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
