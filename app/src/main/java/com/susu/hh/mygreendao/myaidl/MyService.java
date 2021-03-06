package com.susu.hh.mygreendao.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.susu.hh.mygreendao.IMyAidlInterface;

/**
 * 远程服务
 */
public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
    //如果是本地服务不是aidl就返回这个
    class Mybinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }

    }

    private IBinder mIBinder = new IMyAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void testaidl() throws RemoteException {
            show();
        }
    };

    public void show() {
        System.out.println("我被调用了，哈哈哈哈！");
    }
}
