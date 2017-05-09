package com.app.tychodemo.app;

import android.app.Application;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DBCacheStore;

/**
 * Created by xingjikang on 2017/3/24.
 */

public class MyApplication extends Application {
    private static Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(10 * 1000) // 全局连接超时时间，单位毫秒。
                .setReadTimeout(10 * 1000) // 全局服务器响应超时时间，单位毫秒。
        );
        NoHttp.initialize(this, new NoHttp.Config()
                .setCacheStore(
                        new DBCacheStore(this) // 配置缓存到数据库。
                                .setEnable(true) // true启用缓存，fasle禁用缓存。
                )
        );
        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("tychoDemo"); // 设置NoHttp打印Log的TAG。
    }
    public static Application getInstance() {
        return instance;
    }
}
