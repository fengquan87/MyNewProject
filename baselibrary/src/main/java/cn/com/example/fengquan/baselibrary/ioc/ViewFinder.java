package cn.com.example.fengquan.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by fengquan on 2019/5/15.
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;



    public ViewFinder(Activity activity) {
        this.mActivity = activity;

    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
