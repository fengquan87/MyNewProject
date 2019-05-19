package cn.com.example.fengquan.mynewproject;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.example.fengquan.baselibrary.dialog.AlertDialog;

import cn.com.example.fengquan.baselibrary.http.HttpUtils;

import cn.com.example.fengquan.framelibrary.DefaultNavigationBar;
import cn.com.example.fengquan.framelibrary.base.BaseFrameActivity;
import cn.com.example.fengquan.framelibrary.base.HttpCallBack;
import cn.com.example.fengquan.mynewproject.handler.GlobalHandler;
import cn.com.example.fengquan.mynewproject.mode.DiscoverListResult;


public class MainActivity extends BaseFrameActivity implements GlobalHandler.HandleMsgListener {

    private GlobalHandler mHandler;
    @Override
    protected void initData() {

        mHandler = GlobalHandler.getInstance();
        mHandler.setHandleMsgListener(this);

        HttpUtils.with(this).url("http://api.themoviedb.org//3/discover/movie")
                .addParam("api_key", "7e55a88ece9f03408b895a96c1487979")
                .execute(new HttpCallBack<DiscoverListResult>() {
                    public static final int NETWORK_DATA_SUCCESS = 1;

                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        //加载进度条
                    }

                    @Override
                    public void onSuccess(DiscoverListResult discoverListResult) {

                        Message message = mHandler.obtainMessage(NETWORK_DATA_SUCCESS, discoverListResult);
                        message.sendToTarget();
                    }
                });
    }

    @Override
    protected void initView() {
        DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar.Builder(this, (ViewGroup) findViewById(R.id.viewgroup)).setTitle("caoimei").setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"dianwol",Toast.LENGTH_SHORT).show();
            }
        }).builder();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {


        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    public void text(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setContextView(R.layout.dialog)
                .setText(R.id.dialogEt, "sdasdadad")
                .fromBottom(true)
                .fullWidth()
                .show();

        final TextView textView = alertDialog.getView(R.id.dialogEt);
        alertDialog.setOnClickListener(R.id.dialogBtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void handleMsg(Message msg) {
        DiscoverListResult discoverListResult = (DiscoverListResult) msg.obj;
        Toast.makeText(this,"discoverListResult:"+discoverListResult.getTotal_results(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁移除所有消息，避免内存泄露
        mHandler.removeCallbacks(null);

    }
}
