package com.zx.myownbaseapplication.mvp.view.activity;

import android.util.Log;

/**
 * Created by Zhangxu on 2018/4/12.
 */

public class MyLog {
    private static boolean isMyLogOpen = true;
    private MyLog() {}

    private static MyLog Instance;

    public static synchronized MyLog MyLog(){
        if(Instance == null){
            synchronized (MyLog.class){
                if(Instance == null){
                    Instance = new MyLog();
                }
            }
        }
        return Instance;
    }

    public static void d(String tag, String str){
        if(isMyLogOpen){
            Log.d(tag,str);
        }
    }

    public static void e(String tag, String str){
        if(isMyLogOpen){
            Log.e(tag,str);
        }
    }
    public static void i(String tag, String str){
        if(isMyLogOpen){
            Log.i(tag,str);
        }
    }
    //测试用，最后都要删掉此处的引用
    public static void z(String str){
        if(isMyLogOpen){
            Log.i("zx",str);
        }
    }
}
