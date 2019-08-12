package com.zx.myownbaseapplication.base_mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.myownbaseapplication.utils.MyLog;
import com.zx.myownbaseapplication.utils.ToastUtil;

public abstract class BaseMvpFragment<P extends BasePresenter>extends Fragment implements IBaseView  {
    private static final String TAG = "BaseMvpFragment";
    /*** presenter 具体的presenter由子类确定*/
    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        //Fragment与Activity已经完成绑定
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化Fragment。可通过参数savedInstanceState获取之前保存的值。
        super.onCreate(savedInstanceState);
        initData();
    }
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化Fragment的布局。加载布局和findViewById的操作通常在此函数内完成，但是不建议执行耗时的操作，比如读取数据库数据列表。
//        return super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(getlayoutId(),container,false);
        return root;
    }

    protected abstract int getlayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //执行该方法时，与Fragment绑定的Activity的onCreate方法已经执行完成并返回，在该方法内可以进行与
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        //执行该方法时，Fragment由不可见变为可见状态。
        super.onStart();
    }

    @Override
    public void onResume() {
        //执行该方法时，Fragment处于活动状态，用户可与之交互。
        super.onResume();
    }

    @Override
    public void onPause() {
        //执行该方法时，Fragment处于暂停状态，但依然可见，用户不能与之交互。
        super.onPause();
    }

    @Override
    public void onStop() {
        //执行该方法时，Fragment完全不可见。
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        //销毁与Fragment有关的视图，但未与Activity解除绑定，依然可以通过onCreateView方法重新创建视图。通常在ViewPager+Fragment的方式下会调用此方法。
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachMV();
        }
        //销毁Fragment。通常按Back键退出或者Fragment被回收时调用此方法。
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        //解除与Activity的绑定。在onDestroy方法之后调用。
        super.onDetach();
    }
    @Override
    public void showToast(String msg) {
        ToastUtil.ShowShortToast(getContext(),msg);
    }
}
