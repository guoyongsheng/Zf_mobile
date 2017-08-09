package com.zfsoftmh.common.utils;

import android.content.Context;

import com.zfsoftmh.entity.NotificationInfo;
import com.zfsoftmh.entity.RealmIntegerInfo;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseApplication;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;
import io.realm.RealmList;

/**
 * @author wesley
 * @date: 2017-5-26
 * @Description: 推送相关的工具类
 */

public class PushUtils {

    private static final int MAX_TIMES = 100;
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String TAG = "PushUtils";

    private PushUtils() {

    }

    /**
     * 推送通知
     *
     * @param context 上下文对象
     * @param info    日程信息
     */
    public static void addLocalNotification(Context context, ScheduleManagementInfo info) {
        if (context == null || info == null) {
            LoggerHelper.e(TAG, " addLocalNotification " + " context = " + context + " info = " + info);
            return;
        }

        int hintType = info.getHint_type(); // 提醒类型
        if (hintType == 4) {
            return;
        }
        long id = info.getId(); // id
        String title = info.getTitle(); // 标题
        int repeatType = info.getRepeat_type();
        long startTime = DateUtils.dataToLong(DateUtils.stringToDate(info.getStart_time(), PATTERN));
        long endTime = DateUtils.dataToLong(DateUtils.stringToDate(info.getEnd_time(), PATTERN));
        setBroadcastTime(hintType, repeatType, startTime, endTime, id, title, context.getApplicationContext(), info);
    }

    /**
     * 移除通知
     *
     * @param context 上下文对象
     * @param info    日程信息
     */
    public static void removeLocalNotification(Context context, ScheduleManagementInfo info) {
        if (context == null || info == null) {
            LoggerHelper.e(TAG, " removeLocalNotification " + " context =" + context + " info = " + info);
            return;
        }
        RealmList<RealmIntegerInfo> list = info.getNotificationIdList();
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                RealmIntegerInfo integerInfo = list.get(i);
                if (integerInfo != null) {
                    long id = integerInfo.getNotificationId();
                    JPushInterface.removeLocalNotification(context.getApplicationContext(), id);
                }
            }
        }
    }

    /**
     * 清除所有通知
     *
     * @param context 上下文对象
     */
    public static void clearAllNotifications(Context context) {
        if (context == null) {
            LoggerHelper.e(TAG, " clearAllNotifications context is null");
            return;
        }
        JPushInterface.clearAllNotifications(context.getApplicationContext());
    }

    /**
     * 设置推送时间
     *
     * @param hintType   提醒类型 0：提前10分钟提醒 1：提前30分钟提醒 2：提前5分钟提醒 3：准时提醒 4：不提醒
     * @param repeatType 重复类型 0：提醒1次 1：每5分钟提醒一次 2：每10分钟提醒一次 3：每15分钟提醒一次
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param id         通知id
     * @param title      内容
     * @param context    上下文对象
     */
    private static void setBroadcastTime(int hintType, int repeatType, long startTime,
            long endTime, long id, String title, Context context, ScheduleManagementInfo info) {

        if (startTime > endTime) {
            LoggerHelper.e(TAG, " setBroadcastTime " + " hintType = " + hintType + " repeatType = " + repeatType +
                    " startTime = " + startTime + " endTime = " + endTime);
            return;
        }

        switch (hintType) {
        /*
         * 0：提前10分钟提醒  4：不提醒
         */
        case 0:
            hint_before(repeatType, startTime, endTime, id, title, 10, context, info);
            break;

        /*
         * 1：提前30分钟提醒
         */
        case 1:
            hint_before(repeatType, startTime, endTime, id, title, 30, context, info);
            break;

        /*
         * 2：提前5分钟提醒
         */
        case 2:
            hint_before(repeatType, startTime, endTime, id, title, 5, context, info);
            break;

        /*
         *  3：准时提醒
         */
        case 3:
            hint_before(repeatType, startTime, endTime, id, title, 0, context, info);
            break;

        default:
            break;
        }
    }

    /**
     * 设置推送时间
     *
     * @param repeatType 重复类型 0：提醒1次 1：每5分钟提醒一次 2：每10分钟提醒一次 3：每15分钟提醒一次
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param id         通知id
     * @param title      内容
     * @param beforeTime 提前多少分钟提醒
     */
    private static void hint_before(int repeatType, long startTime, long endTime, long id,
            String title, int beforeTime, Context context, ScheduleManagementInfo info) {

        NotificationInfo notificationInfo = new NotificationInfo();
        notificationInfo.setId(id);
        notificationInfo.setType(2);
        String extras = GsonHelper.objectToString(notificationInfo);

        long interval = beforeTime * 60 * 1000; //间隔时间
        long start_time_before = startTime - interval;
        long time = endTime - startTime;
        if (repeatType == 0) {
            RealmList<RealmIntegerInfo> list = new RealmList<>();
            RealmIntegerInfo integerInfo = new RealmIntegerInfo();
            integerInfo.setNotificationId(id);
            list.add(integerInfo);
            JPushLocalNotification notification = new JPushLocalNotification();
            notification.setNotificationId(id);
            notification.setContent(title);
            notification.setBroadcastTime(start_time_before);
            notification.setExtras(extras);
            notification.setBuilderId(1);
            JPushInterface.addLocalNotification(context, notification);
            info.setNotificationIdList(list);
            BaseApplication.getAppComponent().getRealmHelper().insert(info);
        } else if (repeatType == 1) {
            long time_5 = 5 * 60 * 1000;
            long hint_times = time / time_5;
            if (hint_times >= MAX_TIMES) {
                hint_times = MAX_TIMES;
            }
            RealmList<RealmIntegerInfo> list = new RealmList<>();
            for (int i = 0; i < hint_times; i++) {
                RealmIntegerInfo integerInfo = new RealmIntegerInfo();
                JPushLocalNotification notification = new JPushLocalNotification();
                long currentTime = System.currentTimeMillis();
                integerInfo.setNotificationId(currentTime);
                list.add(integerInfo);
                notification.setNotificationId(currentTime);
                notification.setContent(title);
                notification.setBroadcastTime(start_time_before + (i * time_5));
                notification.setExtras(extras);
                notification.setBuilderId(1);
                JPushInterface.addLocalNotification(context, notification);
            }
            info.setNotificationIdList(list);
            BaseApplication.getAppComponent().getRealmHelper().insert(info);
        } else if (repeatType == 2) {
            long time_10 = 10 * 60 * 1000;
            long hint_times = time / time_10;
            if (hint_times >= MAX_TIMES) {
                hint_times = MAX_TIMES;
            }
            RealmList<RealmIntegerInfo> list = new RealmList<>();
            for (int i = 0; i < hint_times; i++) {
                RealmIntegerInfo integerInfo = new RealmIntegerInfo();
                JPushLocalNotification notification = new JPushLocalNotification();
                long currentTime = System.currentTimeMillis();
                notification.setNotificationId(currentTime);
                integerInfo.setNotificationId(currentTime);
                list.add(integerInfo);
                notification.setContent(title);
                notification.setBroadcastTime(start_time_before + (i * time_10));
                notification.setExtras(extras);
                notification.setBuilderId(1);
                JPushInterface.addLocalNotification(context, notification);
            }
            info.setNotificationIdList(list);
            BaseApplication.getAppComponent().getRealmHelper().insert(info);
        } else if (repeatType == 3) {
            long time_15 = 15 * 60 * 1000;
            long hint_times = time / time_15;
            if (hint_times >= MAX_TIMES) {
                hint_times = MAX_TIMES;
            }
            RealmList<RealmIntegerInfo> list = new RealmList<>();
            for (int i = 0; i < hint_times; i++) {
                RealmIntegerInfo integerInfo = new RealmIntegerInfo();
                JPushLocalNotification notification = new JPushLocalNotification();
                long currentTime = System.currentTimeMillis();
                notification.setNotificationId(currentTime);
                integerInfo.setNotificationId(currentTime);
                list.add(integerInfo);
                notification.setContent(title);
                notification.setBroadcastTime(start_time_before + (i * time_15));
                notification.setExtras(extras);
                notification.setBuilderId(1);
                JPushInterface.addLocalNotification(context, notification);
            }
            info.setNotificationIdList(list);
            BaseApplication.getAppComponent().getRealmHelper().insert(info);
        }
    }
}
