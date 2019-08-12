package com.zx.myownbaseapplication.base_mvp;

import java.lang.ref.WeakReference;

//首先声明了两个泛型 M 和 V，M 对应要处理的 Model，V 则对应负责展示的View。
// 由于 V 一般比较大，这里采用了弱引用的写法，避免内存泄漏。
// isViewAttached() 用于检测 V 是否已关联 P，为真则让 getView() 返回对应的 V，否则返回 null。

public abstract class BasePresenter<M, V> {
    protected M mIModel;
    protected WeakReference<V> mIViewRef;

    /**
     * 返回presenter要持有的Model引用(presenter要实现)
     * @return
     */
    protected abstract M getModel();

    protected V getView() {
        return isViewAttached() ? mIViewRef.get() : null;
    }

    /**
     * 绑定IModel和IView的引用
     * @param v view
     */
    public void attachMV(V v) {
        this.mIModel = getModel();
        mIViewRef = new WeakReference<>(v);
        this.afterAttach();
    }

    /**
     * 解绑IModel和IView
     */
    public void detachMV() {
        if (null != mIViewRef) {
            mIViewRef.clear();
            mIViewRef = null;
        }
        mIModel = null;
    }

    protected boolean isViewAttached() {
        return null != mIViewRef && null != mIViewRef.get();
    }

    /**
     * IView和IModel绑定完成立即执行
     * <p>
     * 实现类实现绑定完成后的逻辑,例如数据初始化等
     */
    public abstract void afterAttach();
}
