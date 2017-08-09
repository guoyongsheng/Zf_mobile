package com.zfsoftmh;

import com.facebook.stetho.Stetho;
import com.zfsoftmh.ui.base.BaseApplication;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: debug模式下的应用入口
 */
public class DebugApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
