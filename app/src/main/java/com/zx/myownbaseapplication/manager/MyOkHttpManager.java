package com.zx.myownbaseapplication.manager;

import com.zx.myownbaseapplication.AppManifest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkHttpManager {
    private static final String TAG = "MyOkHttpManager";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static MyOkHttpManager mInstance;
    private OkHttpClient mOkHttpClient;
    private static OkHttpClient mRegetClient;//申请失败时 ，重复申请的client

    public static int MAXLOADTIMES = 3;//重复申请次数

    private MyOkHttpManager() {
        //设置超时，取代mOkHttpClient = new OkHttpClient();
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
//                .addInterceptor(new TokenInterceptor())
                .build();
    }

    public static MyOkHttpManager getInstance() {
        if (mInstance == null){
            synchronized (MyOkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new MyOkHttpManager();
                }
            }
        }
        return mInstance;
    }

    //另一个请求对象
    public  static OkHttpClient getRegetClient(){
        if(mRegetClient == null){
            mRegetClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();
        }
        return mRegetClient;
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


    //选题
    //选题列表
    public static void request_job_topic_page(String url,String status,int page,int per_page, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"?status="+status+"&&page="+page+"&&per_page="+per_page)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //选题列表
    public static void request_job_topic_search_page(String url,String name,String status,int page,int per_page, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"?name="+name+"&&status="+status+"&&page="+page+"&&per_page="+per_page)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    //任务
    /**
     * 任务列表
     * @param status 状态（0：未开始 1：已开始 2：已完成 -1：已放弃） 强制查找全部
     * @param topic_id 选题id
     * @param scrop 在选题内的查询范围（all 所有/own 属于自己）Default value : own
     * @param page 当前页码Default value : 1
     * @param per_page 每页条目数 Default value : 10
     * */
    public static void request_job_page(String url,String status,int topic_id, String scrop,int page,int per_page, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"?status="+status+"&&topic_id="+topic_id+"&&scrop="+scrop+"&&page="+page+"&&per_page="+per_page)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //创建选题任务
    public static void request_create_job(String url,String jsonString, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonString);

        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .post(body)
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



    //选题
    //选题详情
    public static void request_topic_detail(String url, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //选题列表
    public static void request_topic_list_page(String url,String status,int page,int per_page, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"?status="+status+"&&page="+page+"&&per_page="+per_page)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //创建选题
    public static void request_topic_create(String url,String jsonString, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonString);

        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .post(body)
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

    //文稿
    //文稿列表
    public static void request_article_list_page(String url, String keyword, int job_id, int deploy_start_time, int deploy_end_time, String status, int page, int per_page, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        if(job_id==-1){
            Request request = new Request.Builder()
                    .url(url+"?keyword="+keyword+"&deploy_start_time="+deploy_start_time+"&deploy_end_time="+deploy_end_time+"&status="+status+"&&page="+page+"&&per_page="+per_page)
                    .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                    .get()
                    .addHeader("Accept", "application/json")
                    .build();
            mOkHttpClient.newCall(request).enqueue(callback);
        }else{
            Request request = new Request.Builder()
                    .url(url+"?keyword="+keyword+"&job_id="+job_id+"&deploy_start_time="+deploy_start_time+"&deploy_end_time="+deploy_end_time+"&status="+status+"&&page="+page+"&&per_page="+per_page)
                    .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                    .get()
                    .addHeader("Accept", "application/json")
                    .build();
            mOkHttpClient.newCall(request).enqueue(callback);
        }
    }
    //创建文稿
    public static void request_article_create(String url,String jsonString, okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonString);

        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .post(body)
                .addHeader("Accept", "application/json")
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //文稿详情
    public static void request_article_detail(String url,okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    ////删除/放弃/提交文稿 delete|cancel|commit
    public static void request_article_del(String url,String action,okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"?action="+action)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .delete()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //更新文稿
    public static void request_article_update(String url,String jsonString, okhttp3.Callback callback) {
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
    //检查文稿版本
    public static void request_article_version(String url,okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    //获取审核文稿
    public static void request_task_list_page(String url,int page,int per_page,okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"?page="+page+"&per_page="+per_page)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    //审核文稿---通过+打回
    public static void request_task_update(String url,int task_id ,String action ,okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, action);

        Request request = new Request.Builder()
                .url(url+"/"+task_id)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .put(body)
                .addHeader("Accept", "application/json")
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }

    //发布渠道信息
    public static void request_channel(String url ,okhttp3.Callback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "sso_ticket_cookie="+AppManifest.userBean.getTicketInfo().getTicket())
                .get()
                .addHeader("Accept", "application/json")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
}
