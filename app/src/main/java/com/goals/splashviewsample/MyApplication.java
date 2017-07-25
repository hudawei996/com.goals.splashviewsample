package com.goals.splashviewsample;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Created by huyongqiang on 2017/7/25.
 * email:262489227@qq.com
 * http://www.jianshu.com/p/ec7c76157e83
 */

public class MyApplication extends Application {
    private boolean isBackground = true;

    @Override
    public void onCreate() {
        super.onCreate();

        //监听是否在前台
        listenForForeground();
        //监听是否息屏
        listenForScreenTurningOff();
    }

    //监听是否在前台
    private void listenForForeground() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //应用切换至前台
                if (isBackground) {
                    isBackground = false;
                    notifyForeground();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    //监听手机是否息屏
    private void listenForScreenTurningOff() {
        //手机息屏
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isBackground = true;
                notifyBackground();
            }
        }, screenStateFilter);
    }

    /**
     * 监听是否在后台
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        //即将进入后台运行
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackground = true;
            notifyBackground();
        }

    }

    private void notifyForeground() {
        // This is where you can notify listeners, handle session tracking, etc
        Intent intent = new Intent(getApplicationContext(),SampleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void notifyBackground() {
        // This is where you can notify listeners, handle session tracking, etc
    }

    public boolean isBackground() {
        return isBackground;
    }
}
