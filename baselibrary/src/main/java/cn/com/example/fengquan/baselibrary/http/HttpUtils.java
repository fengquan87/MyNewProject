package cn.com.example.fengquan.baselibrary.http;

import android.content.Context;
import android.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by fengquan on 2019/5/19.
 */

public class HttpUtils{

    private static IHttpEngine mHttpEngine;

    private String mUrl;

    //请求方式
    private int mType = GET_TYPE;

    private static final int POST_TYPE = 0x0111;

    private static final int GET_TYPE = 0x0011;

    private Context mContext;

    private Map<String,Object> mParams;

    private HttpUtils(Context context){
        this.mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context){
        return new HttpUtils(context);
    }

    public HttpUtils post(){
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils get(){
        mType = GET_TYPE;
        return this;
    }

    public HttpUtils url(String url){
        mUrl = url;
        return this;
    }

    //添加参数
    public HttpUtils addParam(String key,Object value){
        mParams.put(key,value);
        return this;
    }

    //添加多个参数
    public HttpUtils addParams(Map<String,Object> params){
        mParams.putAll(params);
        return this;
    }


    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    //添加回掉 执行
    public void execute(EngineCallBack callBack){



        if (callBack == null){
            callBack = EngineCallBack.DEFAULT_CALLBACK;
        }

        callBack.onPreExcute(mContext,mParams);

        if (mType == POST_TYPE){
            post(mUrl,mParams,callBack);
        }

        if (mType == GET_TYPE){
            get(mUrl,mParams,callBack);
        }

    }

    public void execute(){
        execute(null);
    }

    //可以替换成其他的网络请求框架
    //在Application里面初始化引擎
    public static void init(IHttpEngine httpEngine){
        mHttpEngine = httpEngine;
    }

    /**
     * 每次可以自带引擎
     * @param httpEngine
     */
    public HttpUtils exchangeEngine(IHttpEngine httpEngine){
        mHttpEngine = httpEngine;
        return this;
    }

    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext,url,params,callBack);
    }


    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.post(mContext,url,params,callBack);
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
