package cn.com.example.fengquan.framelibrary.base;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

import cn.com.example.fengquan.baselibrary.http.EngineCallBack;
import cn.com.example.fengquan.baselibrary.http.HttpUtils;

/**
 * Created by fengquan on 2019/5/19.
 */

public abstract class HttpCallBack<T> implements EngineCallBack {

    Gson gson = new Gson();
    @Override
    public void onPreExcute(Context context, Map<String, Object> params) {
        //在这里添加公共参数
        params.put("certification_country", "US");
        params.put("certification", "R");
        params.put("sort_by", "vote_average.desc");
        onPreExecute();
    }

    public void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {
       T t = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
       onSuccess(t);
    }

    public abstract void onSuccess(T t);
}
