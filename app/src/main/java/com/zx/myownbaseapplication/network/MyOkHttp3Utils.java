package com.zx.myownbaseapplication.network;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.zx.myownbaseapplication.app.MyApp;
import com.zx.myownbaseapplication.base_mvp.IBaseView;
import com.zx.myownbaseapplication.manager.MyOkHttpManager;
import com.zx.myownbaseapplication.utils.MyLog;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
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

//新封装的 OKhttp
public class MyOkHttp3Utils {
    private static final String TAG = "MyOkHttp3Utils";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static MyOkHttp3Utils mInstance;
    private OkHttpClient mOkHttpClient;
    //设置缓存目录
    private File cacheDirectory = new File(MyApp.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
    private Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);


    public static MyOkHttp3Utils getInstance() {
        if (mInstance == null){
            synchronized (MyOkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new MyOkHttp3Utils();
                }
            }
        }
        return mInstance;
    }

    private MyOkHttp3Utils () {
        if (null == mOkHttpClient) {
            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //添加拦截器
//                    .addInterceptor(new MyIntercepter())
                    //设置一个自动管理cookies的管理器
                    .cookieJar(new CookiesManager())
                    //添加网络连接器
                    //.addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .writeTimeout(6, TimeUnit.SECONDS)
                    .readTimeout(6, TimeUnit.SECONDS)
//                    .cache(cache)//设置缓存
                    .retryOnConnectionFailure(true)//自动重试(TODO 要是想重新请求上一个接口，这里设置成true)
                    .build();
        }
    }

//    /**
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

    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(MyApp.getInstance().getApplicationContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 自动管理Cookies
     */
    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApp.getInstance().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }


    ///////////////////////////////////////////以下是具体接口///////////////////////////////////////
    // 登陆
    public static void login(String url, String json, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }
}
