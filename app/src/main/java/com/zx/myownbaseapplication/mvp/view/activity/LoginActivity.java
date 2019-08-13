package com.zx.myownbaseapplication.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.myownbaseapplication.R;
import com.zx.myownbaseapplication.base_mvp.BaseMvpActivity;
import com.zx.myownbaseapplication.bean.UserBean;
import com.zx.myownbaseapplication.manager.SharedPreferencesManager;
import com.zx.myownbaseapplication.manager.UIManager;
import com.zx.myownbaseapplication.mvp.contract.MyContract;
import com.zx.myownbaseapplication.mvp.presenter.Login_Presenter_Impl;
import com.zx.myownbaseapplication.utils.MyLog;
import com.zx.myownbaseapplication.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseMvpActivity<MyContract.Login_Presenter> implements MyContract.I_Login_View {
    private static final String TAG = "LoginActivity";

    EditText et_login_username;
    EditText et_login_password;
    TextView tv_login_ok;
    int state = -1;//状态：-1未点击或点击后，0登陆中

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

        initView();
        getNetData();
    }

    private void initView() {
        et_login_username = findViewById(R.id.et_login_username);
        et_login_password = findViewById(R.id.et_login_password);
        tv_login_ok = findViewById(R.id.tv_login_ok);

        SharedPreferencesManager spm = new SharedPreferencesManager(LoginActivity.this,"login");
        String username = spm.getSharedPreference("username","").toString().trim();
        MyLog.d(TAG,"预加载的username = "+username);
        if(null!=username && !username.equals("")){//不是第一次
            et_login_username.setText(username);
        }

        tv_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state==-1){//未点击
                    //保存username
                    SharedPreferencesManager spm = new SharedPreferencesManager(LoginActivity.this,"login");
                    String username = et_login_username.getText().toString();
                    spm.put("username",username);

                    state=0;

                    UIManager.toMain(LoginActivity.this);//TODO 将来要改为登录接口
                }else if(state==0){//登陆中
                    ToastUtil.ShowCenterShortToast(LoginActivity.this,"登录中");
                }
            }
        });
    }

    private void getNetData() {
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
        state=-1;
        //保存token
        SharedPreferencesManager spm = new SharedPreferencesManager(LoginActivity.this,"login");
        spm.put("token","xxxxx");

        UIManager.toMain(LoginActivity.this);
    }

    @Override
    public void loginFailed(int i, String info) {
        state=-1;
        ToastUtil.ShowCenterShortToast(LoginActivity.this,"登录失败");
    }

}
