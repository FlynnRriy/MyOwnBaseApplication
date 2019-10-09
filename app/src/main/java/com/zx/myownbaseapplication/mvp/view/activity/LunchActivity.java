package com.zx.myownbaseapplication.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.zx.myownbaseapplication.app.MyApp;
import com.zx.myownbaseapplication.base_mvp.BaseMvpActivity;
import com.zx.myownbaseapplication.bean.UserBean;
import com.zx.myownbaseapplication.manager.SharedPreferencesManager;
import com.zx.myownbaseapplication.manager.UIManager;
import com.zx.myownbaseapplication.mvp.contract.MyContract;
import com.zx.myownbaseapplication.mvp.presenter.Token_Presenter_Impl;
import com.zx.myownbaseapplication.utils.MyLog;
import com.zx.myownbaseapplication.utils.ToastUtil;

import java.security.spec.ECField;

/**
 * 判断是否第一次启动
 * */

public class LunchActivity extends BaseMvpActivity<MyContract.Token_Presenter> implements MyContract.I_Token_View  {
    private static final String TAG = "LunchActivity";

    @Override
    protected int getlayoutId() {
        return 0;
    }
    @Override
    public void setPresenter() {
        mPresenter = new Token_Presenter_Impl();
    }

    @Override
    public void TokenError() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                //耗时任务，比如加载网络数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 这里可以睡几秒钟，如果要放广告的话
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        SharedPreferencesManager spm = new SharedPreferencesManager(LunchActivity.this,"login");
                        String firstlogin = spm.getSharedPreference("firstlogin","").toString().trim();
                        MyLog.d(TAG,"firstlogin = "+firstlogin);
                        if(null!=firstlogin && firstlogin.equals("true")){//不是第一次
                            //这里应该用保存的账号密码去登陆
                            //用保存的token 去调用token是否失效的接口

                            String token = spm.getSharedPreference("token","").toString().trim();
                            if(null==token||token.equals("")){
                                UIManager.toLogin(LunchActivity.this);
                            }else {
                                mPresenter.refresh_token(token);
                            }
                        }else{//是第一次
                            spm.put("firstlogin","true");
                            UIManager.toWelcome(LunchActivity.this);
                        }
                        LunchActivity.this.finish();
                    }
                });
            }
        } ).start();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void tokenSuccess(UserBean userBean) {
        MyLog.d(TAG,"token 登陆成功");
        UIManager.toMain(LunchActivity.this);
    }

    @Override
    public void tokenFailed(int i, String info) {
        MyLog.d(TAG,"token 登陆失败");
        ToastUtil.ShowCenterShortToast(LunchActivity.this,"登陆失败");
        UIManager.toLogin(LunchActivity.this);
    }
}
