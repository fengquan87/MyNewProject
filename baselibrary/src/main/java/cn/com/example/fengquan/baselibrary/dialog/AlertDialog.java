package cn.com.example.fengquan.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.lang.ref.WeakReference;

import cn.com.example.fengquan.baselibrary.R;

/**
 * Created by fengquan on 2019/5/18.
 */

public class AlertDialog extends Dialog {


    private AlertController mAlert;

    private AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this,getWindow());
    }

    public void setText(int viewId, CharSequence charSequence) {
        mAlert.setText(viewId,charSequence);
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        mAlert.setOnClickListener(viewId,clickListener);
    }

    public <T extends View> T getView(int viewId){
        return mAlert.getView(viewId);
    }

    public static class Builder {
        private final AlertController.AlertParams P;

        public Builder(Context context){
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }

        public Builder setContextView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        //设置布局内容的layoutId
        public Builder setContextView(int layoutId) {
            P.mView = null;
            P.mViewLayoutResId = layoutId;
            return this;
        }

        public Builder fullWidth(){
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder fromBottom(boolean isAnimation){
            if (isAnimation){
                P.mAnimation = R.style.dialog_from_bottom_anim;
            }

            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setWidthAndHeight(int width,int height){
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        //添加默认动画
        public Builder addDefaultAnimation(){
            P.mAnimation = R.style.dialog_default_anim;
            return this;
        }

        public Builder addAnimation(int animation){
            P.mAnimation = animation;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        //给view设置文本
        public Builder setText(int viewId,CharSequence charSequence){
            P.mTextArray.put(viewId,charSequence);
            return this;
        }

        //给view设置点击事件
        public Builder setOnclickListener(int viewId, View.OnClickListener clickListener){
            P.mClickArray.put(viewId,clickListener);
            return this;
        }


        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(android.content.DialogInterface.OnDismissListener) setOnDismissListener}.</p>
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(android.content.DialogInterface.OnDismissListener)
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public AlertDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final AlertDialog dialog = new AlertDialog(P.mContext,P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
