package cn.com.example.fengquan.baselibrary.navigationbar;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fengquan on 2019/5/19.
 * Description:头部的基类
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;

    private View mNavigationView;

    public AbsNavigationBar(P params){
        this.mParams = params;
        createAndBindView();
    }

    public P getmParams() {
        return mParams;
    }

    protected void setText(int viewId, String mTitle) {
        TextView titleView = findViewById(viewId);
        if (titleView != null && !TextUtils.isEmpty(mTitle)){
            titleView.setText(mTitle);
        }
    }

    protected void setOnclickListener(int viewId,View.OnClickListener clickListener){
        findViewById(viewId).setOnClickListener(clickListener);
    }

    public <T extends View> T findViewById(int viewId){
        return (T)mNavigationView.findViewById(viewId);
    }

    /**
     * 创建和绑定view
     */
    private void createAndBindView() {
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(),mParams.mParent,false);

        mParams.mParent.addView(mNavigationView,0);

        applyView();

    }


    public abstract static class Builder{



        public Builder(Context context, ViewGroup parent){

        }

        public abstract AbsNavigationBar builder();


        public static class AbsNavigationParams{
            public Context mContext;
            public ViewGroup mParent;
            public AbsNavigationParams(Context context,ViewGroup parent){
                this.mContext = context;
                this.mParent = parent;
            }
        }
    }
}
