package com.zx.myownbaseapplication.manager;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.webkit.WebSettings;

import com.zx.myownbaseapplication.AppManifest;
import com.zx.myownbaseapplication.app.MyApp;
import com.zx.myownbaseapplication.utils.MyLog;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author  zx 2019/9/20
 *
 * 单例封装 okhttp
 *
 * */
public class ZxOkHttpManager {
    private static final String TAG = "ZxOkHttpManager";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static ZxOkHttpManager mInstance;
    private static OkHttpClient mOkHttpClient;

    //缓存类型
    /*** 强制使用网络请求（不缓存）* */
    public static final CacheControl FORCE_NETWORK = new CacheControl.Builder().noCache().build();
    /*** 强制性使用本地缓存，如果本地缓存不满足条件，则会返回code为504*/
    public static final CacheControl FORCE_CACHE = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS)
            .build();
    /**设置缓存时间为60秒*/  /// 测试可用！！！
    public static final  CacheControl FORCE_TIME = new CacheControl.Builder()
            .maxAge(60, TimeUnit.SECONDS)
            .build();

    private ZxOkHttpManager() { }

    public static ZxOkHttpManager getInstance() {
        if (mInstance == null){
            synchronized (ZxOkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new ZxOkHttpManager();
                }
            }
        }
        return mInstance;
    }

    private static OkHttpClient getOkHttpClient () {
        if (null == mOkHttpClient) {
            //设置缓存目录
            File cacheDirectory = new File(MyApp.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
            Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //添加拦截器
//                    .addInterceptor(new MyIntercepter())
                    //设置一个自动管理cookies的管理器
//                    .cookieJar(new CookiesManager())
                    //添加网络连接器
                    //.addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .writeTimeout(6, TimeUnit.SECONDS)
                    .readTimeout(6, TimeUnit.SECONDS)
                    .cache(cache)//设置缓存
                    .retryOnConnectionFailure(true)//自动重试(TODO 要是想重新请求上一个接口，这里设置成true)
                    .build();
        }
        return mOkHttpClient;
    }
    ///////////////////////////////////////////以下是具体接口///////////////////////////////////////
    // 登陆
    public static void login(String url, String json, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = getOkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .cacheControl(FORCE_NETWORK)
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }

    //用户列表
    public static void request_userinfo_all(String url, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //更新选题任务状态
    public static void request_refresh_job_state(String url,String jsonString, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonString);

        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .put(body)
                .addHeader("Accept", "application/json")
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //删除/放弃/提交选题
    public static void request_topic_delete(String url, int topic_id, String action,okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/"+topic_id+"?action="+action)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .delete()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }



    /////////////////////////////////////////////////下面的功能暂时没用到//////////////////////////
//    /**
//     * 自动管理Cookies
//     */
//    private class CookiesManager implements CookieJar {
//        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApp.getInstance().getApplicationContext());
//
//        @Override
//        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//            if (cookies != null && cookies.size() > 0) {
//                for (Cookie item : cookies) {
//                    cookieStore.add(url, item);
//                }
//            }
//        }
//
//        @Override
//        public List<Cookie> loadForRequest(HttpUrl url) {
//            List<Cookie> cookies = cookieStore.get(url);
//            return cookies;
//        }
//    }

//        /**
//     * 拦截器
//     */
//    private class MyIntercepter implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//
//            if (!isNetworkReachable(MyApp.getInstance().getApplicationContext())) {
////                updateHandler.sendEmptyMessage(300);
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
//                        .build();
//            }
//            Request.Builder RequestBuilder = request.newBuilder();
//            Request build;
//            /*String token = "";
//            String phoneIMEI="";
//            int permissionGranted = PackageManager.PERMISSION_GRANTED;
//            if (activity.getPackageManager().checkPermission(Manifest.permission.READ_PHONE_STATE, activity.getPackageName()) == permissionGranted) {
//                phoneIMEI = SystemToolUtils.getPhoneIMEI(AppManager.topActivity());//设备唯一识别标识
//            }*/
//
//            build = RequestBuilder
//                    .removeHeader("User-Agent")
//                    .addHeader("User-Agent", getUserAgent())
//                    .addHeader("Authorization", "")
//                    .build();
//
//            Response response = chain.proceed(build);
//
//            HttpUrl url = response.request().url();
//            MyLog.d(TAG,"我的网址"+url);
//
////            int code = response.code();
//            //对个别链接地址做处理（比如要对个别网络请求做特殊的拦截处理）
////            System.out.println("我的网址"+url);
////            updateHandler.sendEmptyMessage(code);
////            if (code == 401) {
////                //跳转到登录页面
////                updateHandler.sendEmptyMessage(401);
////            } else if (code == 402) {
////                //跳转到开户审核中界面
////                updateHandler.sendEmptyMessage(402);
////            } else if (code == 403) {
////                //跳转到开户界面
////                updateHandler.sendEmptyMessage(403);
////            }
//            return response;
//        }
//    }
//
//    private static String getUserAgent() {
//        String userAgent = "";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            try {
//                userAgent = WebSettings.getDefaultUserAgent(MyApp.getInstance().getApplicationContext());
//            } catch (Exception e) {
//                userAgent = System.getProperty("http.agent");
//            }
//        } else {
//            userAgent = System.getProperty("http.agent");
//        }
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0, length = userAgent.length(); i < length; i++) {
//            char c = userAgent.charAt(i);
//            if (c <= '\u001f' || c >= '\u007f') {
//                sb.append(String.format("\\u%04x", (int) c));
//            } else {
//                sb.append(c);
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 判断网络是否可用
//     *
//     * @param context Context对象
//     */
//    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//    public Boolean isNetworkReachable(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo current = cm.getActiveNetworkInfo();
//        if (current == null) {
//            return false;
//        }
//        return (current.isAvailable());
//    }

}
