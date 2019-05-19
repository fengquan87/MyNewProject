package cn.com.example.fengquan.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by fengquan on 2019/5/19.
 * 引擎的规范
 */

public interface IHttpEngine {


    //get请求
    void get(Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //post请求
    void post(Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //下载文件


    //上传文件


    //https  添加证书
}
