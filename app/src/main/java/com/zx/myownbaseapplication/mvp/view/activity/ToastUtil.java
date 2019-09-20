package com.zx.myownbaseapplication.mvp.view.activity;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


//小米5上会有问题
public class ToastUtil {

    private static Toast toast;


    public static void ShowShortToast(Context context, String text){
        if (toast == null) {
            toast=Toast.makeText(context,null,Toast.LENGTH_SHORT);
            toast.setText(text);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(text);
        }
        toast.show();
    }
    public static void ShowLongToast(Context context, String text){
        if (toast == null) {
            toast=Toast.makeText(context,null,Toast.LENGTH_LONG);
            toast.setText(text);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(text);
        }
        toast.show();
    }

    //居中
    public static void ShowCenterShortToast(Context context, String text){
        if (toast == null) {
            toast=Toast.makeText(context,null,Toast.LENGTH_SHORT);
            toast.setText(text);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(text);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}