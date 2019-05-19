package cn.com.example.fengquan.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by fengquan on 2019/5/18.
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    private ViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;

    }

    public void setViewHelper(ViewHelper viewHelper){
        this.mViewHelper = viewHelper;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setText(int viewId, CharSequence charSequence) {
        mViewHelper.setText(viewId,charSequence);
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        mViewHelper.setOnClickListener(viewId,clickListener);
    }

    public <T extends View> T getView(int viewId){
        return mViewHelper.getView(viewId);
    }

    public static class AlertParams {

        public Context mContext;
        public int mThemeResId;
        //点击空白是否能够取消
        public boolean mCancelable = true;
        //dialog cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog 消失监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog 按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;

        //布局的view
        public View mView;
        //布局的id
        public int mViewLayoutResId;

        //存放字体的修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();

        //存放设置的点击事件
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();

        //dialog的宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;

        //dialog的高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //默认在中间显示
        public int mGravity = Gravity.CENTER;

        //默认没有动画
        public int mAnimation = 0;


        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;

        }

        public void apply(AlertController mAlert) {
            //设置参数

            //1.设置布局  DialogViewHelper
            ViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new ViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new ViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请调用setContentView方法设置布局！");
            }

            //设置
            mAlert.setViewHelper(viewHelper);



            mAlert.getDialog().setContentView(viewHelper.getContentView());

            //2.设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                mAlert.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }

            //3.设置点击
            int clickArraySize = mClickArray.size();
            for (int i = 0;i<clickArraySize;i++){
                mAlert.setOnClickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }

            //配置自定义效果：全屏，从底部弹出
            Window window = mAlert.getWindow();
            window.setGravity(mGravity);
            if (mAnimation!=0) {
                window.setWindowAnimations(mAnimation);
            }


            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }
    }

}
