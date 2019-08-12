package com.zx.myownbaseapplication.base_mvp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zx.myownbaseapplication.app.MyApp;
import com.zx.myownbaseapplication.utils.MyLog;
import com.zx.myownbaseapplication.utils.ToastUtil;


//如果你用了 ButterKnife、Dagger 等依赖注入框架，初始化和解绑（去 onDestory() 方法）工作同样可以在这个 BaseActivity 里完成。

public abstract class BaseMvpActivity<P extends BasePresenter> extends Activity implements IBaseView {
    private static final String TAG = "BaseMvpActivity";
    /*** presenter 具体的presenter由子类确定*/
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将当前类加入到Activity的集合里
        MyApp.getInstance().addActivity(this);
        MyLog.d(TAG,"打开的activity="+getComponentName().getClassName());

        //顶部沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if(getlayoutId()!=0){
            setContentView(getlayoutId());
        }

        attachView();
    }

    protected abstract int getlayoutId();

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void attachView() {
        if (this instanceof IBaseView) {
            this.setPresenter();
            if (mPresenter != null) {
                mPresenter.attachMV(this);
            }
        } else {
            MyLog.e(TAG, "attach M V failed.");
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachMV();
        }
        //将集合里的Activity销毁
        MyApp.removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.ShowShortToast(getApplicationContext(),msg);
    }

}