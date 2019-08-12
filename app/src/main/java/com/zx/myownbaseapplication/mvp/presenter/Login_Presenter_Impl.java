package com.zx.myownbaseapplication.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.zx.myownbaseapplication.AppManifest;
import com.zx.myownbaseapplication.bean.UserBean;
import com.zx.myownbaseapplication.mvp.contract.MyContract;
import com.zx.myownbaseapplication.mvp.listener.OnRequestResultListener;
import com.zx.myownbaseapplication.mvp.model.Login_Model_Impl;
import com.zx.myownbaseapplication.utils.MyLog;

import java.io.IOException;

import okhttp3.Response;

public class Login_Presenter_Impl extends MyContract.Login_Presenter {
    private static final String TAG = "Login_Presenter_Impl";
    Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void login(String jsonString) {
        final MyContract.I_Login_View mView = getView();
        if (mView == null) {
            MyLog.e(TAG,"请求失败 mView==null");
            return;
        }
        mView.showLoading();

        mIModel.requestLogin(AppManifest.host + AppManifest.user_login, jsonString, new OnRequestResultListener() {
            @Override
            public void requestSuccess(Response response) {
                String data = null;
                try {
                    data = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MyLog.d(TAG, "Login接口通了data="+data);

                GsonBuilder builder;
                Gson gson;
                builder = new GsonBuilder();
                gson = builder.create();
                try{
                    final UserBean userBean = gson.fromJson(data, UserBean.class);
                    AppManifest.userBean = userBean;
                    if(mView != null) {
                        if(userBean.getCode()!=null){//说明出错了
                            MyLog.d(TAG, "Login接口失败 userEntity.getCode()!=0");
                            mView.hideLoading();
                            mView.loginFailed(AppManifest.ERROR_VALUE,"对象值错误，getCode()!=0");
                        }else{
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mView.hideLoading();
                                    mView.loginSuccess(userBean);
                                }
                            });
                        }
                    }
                }catch (JsonSyntaxException e){
                    MyLog.d(TAG,"requestLogin接口返回类型gson解析失败！e="+e.getMessage());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.hideLoading();
                            mView.loginFailed(AppManifest.ERROR_SERVER,"requestLogin接口返回类型gson解析失败");
                        }
                    });
                }
            }
            @Override
            public void requestFailed(final Exception e) {
                if (mView != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.hideLoading();
                            mView.loginFailed(AppManifest.ERROR_RESULT,"Exception e="+e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    protected MyContract.I_Login_Model getModel() {
        return new Login_Model_Impl();
    }

    @Override
    public void afterAttach() {

    }
}
