package com.zx.myownbaseapplication.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.myownbaseapplication.R;
import com.zx.myownbaseapplication.base_mvp.BaseMvpActivity;
import com.zx.myownbaseapplication.base_mvp.BaseMvpFragment;
import com.zx.myownbaseapplication.manager.UIManager;
import com.zx.myownbaseapplication.mvp.view.adapter.WelcomePagerAdapter;
import com.zx.myownbaseapplication.utils.MyLog;

import java.util.ArrayList;
import java.util.List;


/**
 * 第一次启动进入此页，目前添加一个viewpager，作用是展现新版本特性
 * */
public class WelcomeActivity extends BaseMvpActivity {
    private static final String TAG = "WelcomeActivity";

    ViewPager mViewPager;
    LinearLayout ll_welcome;
    TextView tv_welcome;

    int old_position = 0;//保存当前是哪个亮着的
    @Override
    protected int getlayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void setPresenter() {

    }

    @Override
    public void TokenError() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        initViewPager();
        initDot();//小圆点
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vp_welcome);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add("第"+i+"个View");
        }
        mViewPager.setAdapter(new WelcomePagerAdapter(this,list));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                MyLog.d(TAG,"滑动中=====position:"+ position + "   positionOffset:"+ positionOffset + "   positionOffsetPixels:"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                MyLog.d(TAG,"显示页改变=====postion:"+ position);

                //new
                ImageView imageView = (ImageView) ll_welcome.findViewWithTag("" + position);
                imageView.setImageResource(R.drawable.shape_dot_on);
                //old
                ImageView old_imageView = (ImageView) ll_welcome.findViewWithTag("" + old_position);
                old_imageView.setImageResource(R.drawable.shape_dot_off);
                old_position = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        MyLog.d(TAG,"状态改变=====SCROLL_STATE_IDLE====静止状态");

                        if(old_position == 3){
                            tv_welcome.setVisibility(View.VISIBLE);
                        }else {
                            tv_welcome.setVisibility(View.GONE);
                        }

                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        MyLog.d(TAG,"状态改变=====SCROLL_STATE_DRAGGING==滑动状态");
                        tv_welcome.setVisibility(View.GONE);
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        MyLog.d(TAG,"状态改变=====SCROLL_STATE_SETTLING==滑翔状态");
                        break;
                }
            }
        });
    }


    private void initDot() {
        ll_welcome = (LinearLayout) findViewById(R.id.ll_welcome);

        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.shape_dot_off);
            imageView.setTag("" + i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 60);
            params.setMargins(15, 15, 15, 60);
            imageView.setLayoutParams(params);
            ll_welcome.addView(imageView);
        }
        ImageView imageView = (ImageView)ll_welcome.findViewWithTag("0");
        imageView.setImageResource(R.drawable.shape_dot_on);


        tv_welcome = (TextView) findViewById(R.id.tv_welcome);
        tv_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIManager.toLogin(WelcomeActivity.this);
                WelcomeActivity.this.finish();
            }
        });
    }


}
