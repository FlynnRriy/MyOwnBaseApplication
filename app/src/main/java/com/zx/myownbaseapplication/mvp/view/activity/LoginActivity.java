package com.zx.myownbaseapplication.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zx.myownbaseapplication.R;
import com.zx.myownbaseapplication.base_mvp.BaseMvpActivity;
import com.zx.myownbaseapplication.bean.UserBean;
import com.zx.myownbaseapplication.mvp.contract.MyContract;
import com.zx.myownbaseapplication.mvp.presenter.Login_Presenter_Impl;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseMvpActivity<MyContract.Login_Presenter> implements MyContract.I_Login_View {
    @Override
    protected int getlayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void setPresenter() {
        mPresenter = new Login_Presenter_Impl();
    }

    @Override
    public void TokenError() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        //登陆
//        String username = et_login_phonenum.getText().toString();
//        String password = et_login_password.getText().toString();
//
//        JSONObject object = new JSONObject();
//        try {
//            object.put("username", username);
//            object.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String jsonString = object.toString();
//        mPresenter.login(jsonString);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loginSuccess(UserBean userBean) {

    }

    @Override
    public void loginFailed(int i, String info) {

    }

}
