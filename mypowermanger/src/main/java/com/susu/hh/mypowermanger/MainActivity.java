package com.susu.hh.mypowermanger;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    PowerManager.WakeLock mWakeLock;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //. 注意在网络连接或传输时最好加锁，以免传输被中断
        PowerManager pm =(PowerManager)getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");
        mWakeLock.acquire();
        regisit();
        boolean flag = pm.isIgnoringBatteryOptimizations(getPackageName());
        if(!flag){
            Intent i = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            i.setData(Uri.parse("package:" + getPackageName()));
            startActivity(i);
        }
//        //关闭省电模式
//        Intent intent = new Intent(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)
//                    .putExtra(PowerManager.EXTRA_POWER_SAVE_MODE, false)
//                    .addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);//只有动态定义的广播接收器才能接收到该广播
//        sendBroadcast(intent);
        }

    private void regisit() {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
        registerReceiver(new myreceive(),mIntentFilter);
    }

    private class  myreceive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
