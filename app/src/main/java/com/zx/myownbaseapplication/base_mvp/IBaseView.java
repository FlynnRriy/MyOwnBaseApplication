package com.zx.myownbaseapplication.base_mvp;

public interface IBaseView {
    void showToast(String msg);//BaseMvpActivity实现了，当想显示一段话时使用
    void setPresenter();//要由具体的Activity实现
    void TokenError();//当token失效时的处理
}
