package cn.com.example.fengquan.mynewproject;

import android.app.Application;

import cn.com.example.fengquan.baselibrary.ExceptionCrashHandler;
import cn.com.example.fengquan.baselibrary.http.HttpUtils;
import cn.com.example.fengquan.framelibrary.http.OkHttpEngine;

/**
 * Created by fengquan on 2019/5/18.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        111111
        222222
        ExceptionCrashHandler.exceptionHandler.init(this);
        HttpUtils.init(new OkHttpEngine());
    }
}
