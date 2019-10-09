package com.zx.myownbaseapplication.mvp.model;

import com.zx.myownbaseapplication.base_mvp.BaseModel;
import com.zx.myownbaseapplication.manager.ZxOkHttpManager;
import com.zx.myownbaseapplication.mvp.contract.MyContract;
import com.zx.myownbaseapplication.mvp.listener.OnRequestResultListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Token_Model_Impl  extends BaseModel implements MyContract.I_Token_Model  {
    private static final String TAG = "Token_Model_Impl";

    @Override
    public void requestToken(String host, String json, final OnRequestResultListener onRequestResultListener) {
        ZxOkHttpManager.getInstance().login(host, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                onRequestResultListener.requestFailed(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onRequestResultListener.requestSuccess(response);
            }
        });
    }
}
