package com.android.baseproject.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.android.baseproject.base.activity.BaseActivity;
import com.android.baseproject.utils.logger.MyCrashListener;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.xuexiang.xlog.XLog;
import com.xuexiang.xlog.crash.CrashHandler;

import java.util.ArrayList;

/**
 * @author yhm
 * @date 2017/11/27
 */
//public class BaseApplication extends Application implements HasActivityInjector {
public class BaseApplication extends Application {


    private static BaseApplication instance;
    private RefWatcher refWatcher;
    private ArrayList<BaseActivity> activities = new ArrayList<>();
    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        refWatcher = LeakCanary.install(this);
        instance = this;
        //崩溃日志保存到本地
        ///storage/emulated/0/Android/data/com.common.esimrfid/cache/crash_log
        XLog.init(this);
        CrashHandler.getInstance().setOnCrashListener(new MyCrashListener());

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    public void addActivity(BaseActivity activity){
        activities.add(activity);
    }

    public void removeActivity(BaseActivity activity){
        activities.remove(activity);
    }

    public void exitActivitys(){
        for (int i = 0; i < activities.size(); i++) {
            activities.get(i).finish();
        }
    }

}
