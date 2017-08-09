package com.zfsoftmh.ui.modules.chatting.helper.view.forward;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/25.
 * <p>Activity管理</p>
 */
public class ActivityCollector {

    public static ActivityCollector getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder{
        private static ActivityCollector instance = new ActivityCollector();
    }

    private ActivityCollector(){
        activities = new ArrayList<>();
    }

    public void clearCollector(){
        activities.clear();
    }

    private List<Activity> activities;

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
