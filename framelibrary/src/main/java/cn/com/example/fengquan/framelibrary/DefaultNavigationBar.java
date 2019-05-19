package cn.com.example.fengquan.framelibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.com.example.fengquan.baselibrary.ioc.ViewOnClick;
import cn.com.example.fengquan.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Created by fengquan on 2019/5/19.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {
    public DefaultNavigationBar(Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.navigation_layout;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.navigationTv,getmParams().mTitle);
        setOnclickListener(R.id.navigationTv,getmParams().mRightOnclickListener);

    }



    public static class Builder extends AbsNavigationBar.Builder{

        DefaultNavigationParams P;
        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context,parent);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

        //设置所有效果
        public DefaultNavigationBar.Builder setTitle(String title){
            P.mTitle = title;
            return this;
        }

        public DefaultNavigationBar.Builder setRightText(String rightText){
            P.mRightText = rightText;
            return this;
        }


        public DefaultNavigationBar.Builder setRightIcon(int rightRes){
            P.mRightRes = rightRes;
            return this;
        }

        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener clickListener){
            P.mRightOnclickListener = clickListener;
            return this;
        }


        //后面自己去扩展

        public static class DefaultNavigationParams extends AbsNavigationParams{

            public String mTitle;

            public String mRightText;

            public int mRightRes;

            public View.OnClickListener mRightOnclickListener;
            //所有效果
            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
