package com.zfsoftmh.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.mobileim.channel.IMChannel;
import com.alibaba.wxlib.util.SysUtil;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zfsoftmh.BuildConfig;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.AppModule;
import com.zfsoftmh.di.DaggerAppComponent;
import com.zfsoftmh.ui.modules.chatting.helper.CustomConfigHelper;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.widget.AppBlockCanaryContext;
import com.zxy.tiny.Tiny;

import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;


/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 应用的入口
 */
public class BaseApplication extends MultiDexApplication {

    private static RefWatcher refWatcher;
    private static AppComponent appComponent;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    //TBS初始化回调
    private QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
        @Override
        public void onCoreInitFinished() {

        }

        @Override
        public void onViewInitFinished(boolean b) {//初始化成功返回true

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        //安装x5内核
        QbSdk.initX5Environment(this, callback);

        // 这里return是为了退出Application.onCreate
        if (mustRunFirstInsideApplicationOnCreate()) {
            // 在":TCMSService"进程中，无需进行app业务的初始化，以节省内存
            return;
        }

        initChatting();

        initUmeng();

        initJPush();

        initLogger();

        initQRCode();

        initLeakCanary();

        initBlockCanary();

        initTiny();

        initRealm();

        initComponent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    //IM
    private void initChatting() {
        if (SysUtil.isMainProcess()) {
            //以下步骤调用顺序有严格要求
            CustomConfigHelper.initCustomConfig();
            IMKitHelper.getInstance().initSDK(this);
            IMKitHelper.getInstance().initFeedBack(this);
            IMChannel.DEBUG = false;
        }
    }

    //初始化友盟统计
    private void initUmeng() {
        Config.DEBUG = BuildConfig.DEBUG;
        Config.isJumptoAppStore = true;
        MobclickAgent.openActivityDurationTrack(false);
        UMShareAPI.get(this);

        PlatformConfig.setSinaWeibo("2977266946", "28eb9866042fe18a81ae9a9b27bee4ec", "http://demo.zfsoft.com");
        PlatformConfig.setQQZone("1105824655", "mhKeXqg3J1ae7vJ9");
        PlatformConfig.setWeixin("wx532120ff0739ea7e", "74023d0d4fc04e63b1d49ac6ff0f5349");
        PlatformConfig.setAlipay("2017021505682530");
    }

    //初始化极光
    private void initJPush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
    }

    //初始化logger
    private void initLogger() {
        LoggerHelper.init(getPackageName());
    }

    //初始化二维码
    private void initQRCode() {
        ZXingLibrary.initDisplayOpinion(this);
    }

    //初始化LeakCanary
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    //初始化BlockCanary
    private void initBlockCanary() {
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    //初始化图片压缩
    private void initTiny() {
        Tiny.getInstance().init(this);
    }

    //初始化数据库
    private void initRealm() {
        Realm.init(this);
    }

    /**
     * 获取监听对象---监听Activity和Fragment的内存泄漏
     *
     * @return RefWatcher的实例
     */
    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }


    //初始化Component
    private void initComponent() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    /**
     * 获取AppComponent实例
     *
     * @return AppComponent实例
     */
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    private boolean mustRunFirstInsideApplicationOnCreate() {
        //必须的初始化
        SysUtil.setApplication(this);
        sContext = getApplicationContext();
        return SysUtil.isTCMSServiceProcess(sContext);
    }
}
