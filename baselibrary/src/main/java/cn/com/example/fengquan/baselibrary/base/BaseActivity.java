package cn.com.example.fengquan.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.com.example.fengquan.baselibrary.ioc.ViewUtils;

/**
 * Created by fengquan on 2019/5/17.
 * Description: 
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局layout
        setContentView();

        ViewUtils.inject(this);



        //初始化头部
        initTitle();

        //初始化界面
        initView();

        //初始化数据
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initTitle();

    protected abstract void setContentView();

    protected void startActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
