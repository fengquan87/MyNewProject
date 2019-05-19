package cn.com.example.fengquan.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by fengquan on 2019/5/19.
 */

public interface EngineCallBack{

    //一开始就会调用
    public void onPreExcute(Context context, Map<String,Object> params);


    //错误
    void onError(Exception e);

    //成功  返回对象会出问题
    void onSuccess(String result);


    //默认的
    public final EngineCallBack DEFAULT_CALLBACK = new EngineCallBack() {
        @Override
        public void onPreExcute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
