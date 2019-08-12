package com.zx.myownbaseapplication.base_mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.zx.myownbaseapplication.app.MyApp;
import com.zx.myownbaseapplication.utils.MyLog;
import com.zx.myownbaseapplication.utils.ToastUtil;

/**
 *
 * FragmentActivity
 * */
public abstract class BaseMvpFragmentActivity <P extends BasePresenter> extends FragmentActivity implements IBaseView {
    private static final String TAG = "BaseMvpActivity";
    /*** presenter 具体的presenter由子类确定*/
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将当前类加入到Activity的集合里
        MyApp.getInstance().addActivity(this);
        MyLog.d(TAG,"打开的activity="+getComponentName().getClassName());

        setContentView(getlayoutId());
        initData();
    }

    protected abstract int getlayoutId();

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
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
