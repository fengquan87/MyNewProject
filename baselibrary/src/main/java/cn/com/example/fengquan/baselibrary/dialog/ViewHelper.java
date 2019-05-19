package cn.com.example.fengquan.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.com.example.fengquan.baselibrary.R;

/**
 * Created by fengquan on 2019/5/18.
 * Dialog view的辅助处理类
 */

class ViewHelper {
    private View mContentView;

    private SparseArray<WeakReference<View>> mViews ;
    public ViewHelper(Context mContext, int mViewLayoutResId) {
        this();
        mContentView = LayoutInflater.from(mContext).inflate(mViewLayoutResId,null);
    }

    public ViewHelper() {
        mViews = new SparseArray<>();
    }

    public void setContentView(View mView) {
        this.mContentView = mView;
    }

    public void setText(int viewId, CharSequence charSequence) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(clickListener);
        }
    }

    public <T extends View> T getView(int viewId){
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (viewWeakReference!=null){
            view = viewWeakReference.get();
        }

        if (view == null){
            view = mContentView.findViewById(viewId);
            if (view!= null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    public View getContentView() {
        return mContentView;
    }
}
