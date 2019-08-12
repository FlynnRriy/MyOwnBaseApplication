package com.zx.myownbaseapplication.mvp.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.myownbaseapplication.R;

import java.util.List;

public class WelcomePagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mData;

    public WelcomePagerAdapter(Context context ,List<String> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.viewpager_welcome,null);
        //显示文字
//        TextView tv = (TextView) view.findViewById(R.id.tv_viewpager_welcome);
//        tv.setText(mData.get(position));

        ImageView iv_viewpager_welcome = (ImageView) view.findViewById(R.id.iv_viewpager_welcome);
        if(position == 0){
            iv_viewpager_welcome.setImageResource(R.mipmap.welcome1);
        }else if(position == 1){
            iv_viewpager_welcome.setImageResource(R.mipmap.welcome2);
        }else if(position == 2){
            iv_viewpager_welcome.setImageResource(R.mipmap.welcome3);
        }else if(position == 3){
            iv_viewpager_welcome.setImageResource(R.mipmap.welcome4);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}