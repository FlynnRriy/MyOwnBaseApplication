package com.zx.myownbaseapplication.mvp.model;

import com.zx.myownbaseapplication.base_mvp.BaseModel;
import com.zx.myownbaseapplication.manager.MyOkHttpManager;
import com.zx.myownbaseapplication.mvp.contract.MyContract;
import com.zx.myownbaseapplication.mvp.listener.OnRequestResultListener;
import com.zx.myownbaseapplication.utils.MyLog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Login_Model_Impl extends BaseModel implements MyContract.I_Login_Model  {
    private static final String TAG = "Activity_Login_Model_Impl";
    int serversLoadTimes = 0;
    @Override
    public void requestLogin(String host, String json, final OnRequestResultListener onRequestResultListener) {
        MyOkHttpManager.getInstance().login(host, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e.getClass().equals(SocketTimeoutException.class) && serversLoadTimes<MyOkHttpManager.MAXLOADTIMES)//如果超时并未超过指定次数，则重新连接
                {
                    serversLoadTimes++;
                    MyLog.d(TAG,"requestLogin请求超时，重新发起请求第"+serversLoadTimes+"次请求");
                    MyOkHttpManager.getRegetClient().newCall(call.request()).enqueue(this);
                }else {
                    MyLog.d(TAG,"requestLogin请求失败");
                    serversLoadTimes = 0;
                    e.printStackTrace();
                    onRequestResultListener.requestFailed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                serversLoadTimes = 0;
                onRequestResultListener.requestSuccess(response);
            }
        });
    }
}
