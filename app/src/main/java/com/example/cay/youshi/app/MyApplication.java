package com.example.cay.youshi.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.example.cay.youshi.http.HttpUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;



/** d
 * Created by jingbin on 2016/11/22.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    public static final String APP_ID = "2882303761517558273";
    public static final String APP_KEY = "5941755876273";
    public static final String TAG = "com.example.cay.youshi";
    public static Context context;
    public static MyApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }

    public static Context getContext() {
        if (context == null) {
            context = getContext();
        }
        return context;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        HttpUtils.getInstance().setContext(getApplicationContext());
       CrashReport.initCrashReport(getApplicationContext());
//*********************小米推送开始**************************
       //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
            //    Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
               // Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

        //*********************小米推送结束**************************
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

    }

   private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
