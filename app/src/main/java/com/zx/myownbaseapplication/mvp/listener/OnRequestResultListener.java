package com.zx.myownbaseapplication.mvp.listener;

import okhttp3.Response;

/**
 * 通用okhttp请求的listener
 * */

public interface OnRequestResultListener {
    void requestSuccess(Response response);//添加token失效时,要重新申请的call
    void requestFailed(Exception e);
}
