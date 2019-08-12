package com.zx.myownbaseapplication.mvp.contract;
//通过一个协议接口MyContract来定义ILoginView和ILoginModel，
// 这样也确定了LoginPresenter的IModel和IView的具体类型。
// 由于Presenter、IView和IModel都定义在MyContract，
// 这样一来，presenter方法、view接口方法、model接口方法全部都一目了然。
// 后面如果有功能拓展的话，直接按照这个模式往后面添加就可以了。
//---------------------

import com.zx.myownbaseapplication.base_mvp.BasePresenter;
import com.zx.myownbaseapplication.base_mvp.IBaseModel;
import com.zx.myownbaseapplication.base_mvp.IBaseView;
import com.zx.myownbaseapplication.bean.UserBean;
import com.zx.myownbaseapplication.mvp.listener.OnRequestResultListener;

import java.util.ArrayList;
import java.util.List;

public interface MyContract {

    //////登录相关///////////////
    abstract class Login_Presenter extends BasePresenter<I_Login_Model, I_Login_View> {
        //activity调用的方法
        public abstract void login(String json);
    }
    interface I_Login_Model extends IBaseModel {
        void requestLogin(String host, String json, OnRequestResultListener onRequestResultListener);
    }
    interface I_Login_View extends IBaseView {
        void showLoading();//去请求
        void hideLoading();//拿到结果
        void loginSuccess(UserBean userBean);
        void loginFailed(int i, String info);
    }

}
