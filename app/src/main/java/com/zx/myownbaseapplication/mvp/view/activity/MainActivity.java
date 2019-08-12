package com.zx.myownbaseapplication.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zx.myownbaseapplication.R;
import com.zx.myownbaseapplication.app.MyApp;
import com.zx.myownbaseapplication.base_mvp.BaseMvpActivity;
import com.zx.myownbaseapplication.utils.ToastUtil;

public class MainActivity extends BaseMvpActivity {


    @Override
    protected int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if(!checkBackAction()){
            finish();
            MyApp.getInstance().finishAll();
        }
    }

    //双击退出相关
    private boolean mFlag = false;
    private long mTimeout = -1;
    private boolean checkBackAction() {
        long time = 3000L;//判定时间设为3秒
        boolean flag = mFlag;
        mFlag = true;
        boolean timeout = (mTimeout == -1 || (System.currentTimeMillis() - mTimeout) > time);
        if (mFlag && (mFlag != flag || timeout)) {
            mTimeout = System.currentTimeMillis();
            ToastUtil.ShowShortToast(getApplicationContext(),"再点击一次回到桌面");
            return true;
        }
        return !mFlag;
    }


}
