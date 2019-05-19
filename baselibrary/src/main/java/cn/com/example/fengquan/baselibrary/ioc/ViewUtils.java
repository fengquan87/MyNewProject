package cn.com.example.fengquan.baselibrary.ioc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.com.example.fengquan.baselibrary.util.NetStateUtils;

/**
 * Created by fengquan on 2019/5/15.
 */

public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }


    public static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }


    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.遍历类里面所有的属性
        Class<?> clazz = object.getClass();
        //获取所有属性包括私有和共有
        Field[] fields = clazz.getDeclaredFields();
        //2.获取ViewById里面的value
        for (Field field : fields
                ) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里面的id值  --> R.id.xx
                int viewId = viewById.value();
                //3.findViewById找到View
                View view = finder.findViewById(viewId);

                if (view != null) {

                    //能注入所有的类型
                    field.setAccessible(true);
                    //4.动态地注入找到的view
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectEvent(ViewFinder finder, final Object object) {
        //1.遍历类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //2.获取ViewOnClick里面的value
        for (final Method method : methods
                ) {
            ViewOnClick onClick = method.getAnnotation(ViewOnClick.class);
            final NetCheck netState = method.getAnnotation(NetCheck.class);
            if (onClick != null) {
                //获取注解里面的id值  --> R.id.xx
                int[] viewId = onClick.value();
                //3.findViewById找到View
                for (final int viewid : viewId
                        ) {
                    View view = finder.findViewById(viewid);
                    if (view != null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if (netState != null) {
                                        //检查网络情况
                                        if (NetStateUtils.isNetworkConnected(v.getContext())) {
                                            Toast.makeText(v.getContext(), "没有网络，别点了", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    //反射调用方法
                                    method.setAccessible(true);
                                    method.invoke(object, v);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

            }
        }
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
