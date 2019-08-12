package com.zx.myownbaseapplication;

import com.zx.myownbaseapplication.bean.UserBean;

/**
 * Created by Zhangxu on 2019/4/3.
 *
 * 保存常量和运行期间的变量
 */
public final class AppManifest {

    public static int per_page = 10;//通用列表，每页的数量
    //接口地址
    public static String host="http://218.240.131.138:9088/cps/api/v2";//外网

    public static String user_login="/user/login";//登录

    public static UserBean userBean;//保存登陆后的用户信息


    //字符串公钥，保存在客户端
    public static final String PUBLIC_KEY_STR = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOZxOuZfkBnqXxeDeo" +
            "DpQxSF9gnQ9Wy2fSndk/39OZfLgeSm34JkrkTa0+mMWW6LD1aYqtl78hIAJe8KLYoGKgMCAwEAAQ==";

    public static int ERROR_VALUE =-1;//接口传递的值不对；
    public static int ERROR_RESULT =-2;//接口返回值失败；
    public static int ERROR_SERVER =-5;//远程服务返回失败；
}