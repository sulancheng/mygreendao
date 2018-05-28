package com.susu.hh.mygreendao.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by su
 * on 2017/3/24.
 */
public class OkUtils {

    public static boolean RELEASE = true;
    private static final String ADDRESS_2 = "http://hcb.leifang.xin"; //release service
    private static final String ADDRESS_1 = "http://192.168.0.106:8080"; //local service
    private static final String SERVICE = "/app";
    private static Gson gson = new Gson();

    public static String getServerChannel() {
        String serverUrl = "";
        if (RELEASE) {
            serverUrl = ADDRESS_2 + SERVICE;
        } else {
            serverUrl = ADDRESS_1 + SERVICE;
        }
        return serverUrl;
    }

    public static String photopa() {
        String serverUrl = "";
        if (RELEASE) {
            serverUrl = ADDRESS_2;
        } else {
            serverUrl = ADDRESS_1;
        }
        return serverUrl;
    }

    public static void demo(Context context, MyResponse myresponse) {
        HashMap<String, String> paramss = new HashMap<>();
        paramss.put("username", "sulc");
        paramss.put("password", "123456");
        paramss.put("q", "s");
        requsetJsonparm("http://192.168.0.102:8085/php/gethint.php", context, paramss, myresponse);
    }

    //获取考试列表
    public static void getTestbiao(Context context, HashMap reparam, MyResponse myresponse) {
        requsetJsonparm(getServerChannel() + "/getExamineList", context, reparam, myresponse);
    }
    //轮播领取优惠券
    public static Observable<String> checkQuan(Context context, Object reparam, MyResponse myresponse) {
       return requsetJsonparm(getServerChannel() + "/checkQuan", context, reparam);
    }
    public static Observable<String> requsetJsonparm(final String url, final Context mContext, Object obj) {
        return Observable.create((ObservableEmitter<String> e)->{
            String json = gson.toJson(obj);
            OkGo.<String>post(url)
                    .tag(mContext)
                    //.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                    // .params(params)//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
                    .upJson(json)//setCertificates
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            e.onNext(response.body());
                            //数据发送已经完成
                            e.onComplete();
                        }

                        @Override
                        public void onError(Response<String> response) {
                            Throwable exception = response.getException();
                            e.onError(exception);
                        }
                    });
        });

    }

    //    //获取论坛回复
//    public static synchronized void getLunThfconent(Context context, ZxXqLt reparam, MyResponse myresponse) {
//        requsetJsonparm(getServerChannel() + "/getForumReplyByID",context,reparam,myresponse);
//    }
    public static void logintwo(Context context, MyResponse myresponse, File file) {
        HashMap<String, String> paramss = new HashMap<>();
        paramss.put("username", "sulc");
        paramss.put("password", "123456");
        paramss.put("q", "s");
        ArrayList<File> mfiles = new ArrayList<>();
        mfiles.add(file);
        //requsetWenjian("http://192.168.0.101:8085/php/upload_file.php", context, mfiles, myresponse);
    }

    public static void requsetJsonparm(String url, final Context mContext, HashMap<String, String> params, final MyResponse myresponse) {
        OkGo.<String>post(url)
                .tag(mContext)
                //.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .params(params)//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        checkresponse(1, response.body(), myresponse);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        checkresponse(2, response.body(), myresponse);
                    }
                });
    }



    public abstract static class MyResponse {
        public abstract void expResponse(String myresponse);

        public abstract void error(String erro);

        public void getProgress(Progress progress) {

        }
    }

    private static void checkresponse(int type, String responseBody, MyResponse myresponse) {
        if (type == 1) {//成功
            if (myresponse != null) {
                if (responseBody == null) {
                    responseBody = "";
                }
                myresponse.expResponse(responseBody);
            }

        } else {
            if (myresponse != null) {
                if (responseBody == null) {
                    responseBody = "erro";
                }
                myresponse.error(responseBody);
            }
        }

    }
}
