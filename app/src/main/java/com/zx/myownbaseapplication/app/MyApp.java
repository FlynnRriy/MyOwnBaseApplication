package com.zx.myownbaseapplication.app;

import android.app.Activity;
import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.zx.myownbaseapplication.manager.CrashManager;
import com.zx.myownbaseapplication.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

//切记不要用 instance = new MyApp() 一类的赋值去获取实例，这样你得到的只是一个普通的 Java 类，不会具备任何 Application 的功能！
public class MyApp extends Application {
    private static final String TAG = "MyApp";
    private static MyApp instance;
    public static List<Activity> activities;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        MyLog.d(TAG,"MyApp onCreate");
        super.onCreate();

        //DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
//      FlowManager.init(this);//这句也可以初始化

        //采集崩溃日志
        CrashManager.getInstance(this);

        instance = this;
    }

    @Override
    public void onTerminate() {
        MyLog.d(TAG,"MyApp onTerminate");
//        DBOperator.releaseDB();
        super.onTerminate();
    }
    //-----------------------------------用于存放我们所有activity的数组--start--------------------
    //向集合中添加一个activity
    public static void addActivity(Activity activity){
        //如果集合为空  则初始化
        if(activities == null){
            activities = new ArrayList<>();
        }
        //将activity加入到集合中
        activities.add(activity);
    }
    //移除已经销毁的Activity
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    //结束掉所有的Activity
    public static void finishAll(){ //循环集合,  将所有的activity finish
        for(Activity activity : activities){
            if(! activity.isFinishing()){
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    //-----------------------------------用于存放我们所有activity的数组--over--------------------
}