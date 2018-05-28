package com.susu.hh.mygreendao.aplication;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by su
 * on 2017/3/24.
 */
public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initokgo();

    }

//    private void initHuanxin() {
//        ChatClient.Options options = new ChatClient.Options();
//        options.setAppkey("1435171108061965#huichubang");//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
//        options.setTenantId("49571");//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
//
//        // Kefu SDK 初始化
//        if (!ChatClient.getInstance().init(this, options)){
//            return;
//        }
//        // Kefu EaseUI的初始化
//        UIProvider.getInstance().init(this);
//        //后面可以设置其他属性
//        // 设置为true后，将打印日志到logcat, 发布APP时应关闭该选项
//        ChatClient.getInstance().setDebugMode(true);
//    }

    private void initokgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
            //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(15, TimeUnit.SECONDS);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newBuilder().connectTimeout(5, TimeUnit.SECONDS)
                //.readTimeout(20, TimeUnit.SECONDS)
//                .build();
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())               //必须调用初始化
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);
        //.addCommonHeaders(headers)                      //全局公共头
        //.addCommonParams(params);                       //全局公共参数

    }

}
