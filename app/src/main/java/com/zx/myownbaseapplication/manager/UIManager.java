package com.zx.myownbaseapplication.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zx.myownbaseapplication.mvp.view.activity.MainActivity;
import com.zx.myownbaseapplication.mvp.view.activity.WelcomeActivity;

/**
 * Created by ZhangXu on 2019/4/3.
 */
public class UIManager {

    private static Intent getIntent(Context context, Class<?> clazz) {
        Intent it = new Intent(context, clazz);
        return it;
    }

    private static void startActivityNecessary(Context context, Intent intent) {
        context.startActivity(intent);
    }

    //第一次启动，去欢迎页
    public  static Intent toWelcome(Context context){
        Intent intent = getIntent(context, WelcomeActivity.class);
        startActivityNecessary(context, intent);
        return intent;
    }

    //去主页
    public  static Intent toMain(Context context){
        Intent intent = getIntent(context, MainActivity.class);
        startActivityNecessary(context, intent);
        return intent;
    }

}
