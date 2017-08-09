package com.zfsoftmh.common.utils;

import com.zfsoftmh.entity.QuestionnaireDetailInfo;
import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.entity.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: 数据库Realm的工具类
 */

public class RealmHelper {

    private static final String REALM_NAME = "zf_mobile.realm";
    private Realm realm;

    public RealmHelper() {
        realm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(REALM_NAME)
                .build());
    }


    public Realm getRealm() {
        return realm;
    }


    /**
     * 插入数据
     *
     * @param info ScheduleManagementInfo对象
     */
    public void insert(final ScheduleManagementInfo info) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(info);
            }
        });
    }


    /**
     * 根据特定的时间查找这个时间下所有的日程
     *
     * @param time 时间 格式: yyyy-MM-dd
     * @return ScheduleManagementInfo集合
     */
    public List<ScheduleManagementInfo> queryScheduleByTime(String time) {

        RealmResults<ScheduleManagementInfo> result = realm.where(ScheduleManagementInfo.class)
                .equalTo("time", time)
                .findAllSorted("start_time", Sort.ASCENDING);

        return realm.copyFromRealm(result);
    }


    /**
     * 根据id获取日程信息
     *
     * @param id id
     * @return ScheduleManagementInfo 对象
     */
    public ScheduleManagementInfo queryScheduleById(long id) {

        return realm.where(ScheduleManagementInfo.class)
                .equalTo("id", id)
                .findFirst();
    }

    /**
     * 根据id删除日程信息
     *
     * @param id id
     */
    public void deleteScheduleById(long id) {

        final ScheduleManagementInfo info = queryScheduleById(id);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                info.deleteFromRealm();
            }
        });
    }

    /**
     * 查询所有的日程信息
     *
     * @return 日程信息集合
     */
    public List<ScheduleManagementInfo> queryAllScheduleInfo() {

        RealmResults<ScheduleManagementInfo> result = realm.where(ScheduleManagementInfo.class)
                .findAllSorted("start_time", Sort.DESCENDING);

        return realm.copyFromRealm(result);
    }

    /**
     * 查询所有的问卷调查的信息
     *
     * @return 问卷调查的集合
     */
    public List<QuestionnaireItemInfo> queryAllQuestionnaireItemInfo() {
        RealmResults<QuestionnaireItemInfo> result = realm.where(QuestionnaireItemInfo.class)
                .findAllSorted("time", Sort.DESCENDING);

        return realm.copyFromRealm(result);
    }

    /**
     * 插入数据
     *
     * @param info 问卷调查的对象
     */
    public void insert(final QuestionnaireItemInfo info) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(info);
            }
        });
    }

    /**
     * 添加数据
     *
     * @param info       问卷调查的对象
     * @param detailInfo 问卷调查详情的对象
     * @param type       0:添加 1: 跟新
     */
    public void insert(final QuestionnaireItemInfo info, final QuestionnaireDetailInfo detailInfo, final int type) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                QuestionnaireDetailInfo detailInfo1 = realm.copyToRealmOrUpdate(detailInfo);
                if (type == 0) {
                    info.getList().add(detailInfo1);
                }
            }
        });
    }

    /**
     * 根据id获取问卷调查的信息
     *
     * @param id id
     * @return QuestionnaireItemInfo对象
     */
    public QuestionnaireItemInfo questionnaireItemInfoById(long id) {
        return realm.where(QuestionnaireItemInfo.class)
                .equalTo("id", id)
                .findFirst();
    }

    /**
     * 删除问卷调查的信息
     *
     * @param id id
     */
    public void deleteQuestionnaireItemInfo(long id) {

        final QuestionnaireItemInfo itemInfo = questionnaireItemInfoById(id);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                itemInfo.deleteFromRealm();
            }
        });
    }

    /**
     * 根据id删除问卷调查详情信息
     *
     * @param id id
     */
    public void deleteQuestionnaireDetailInfo(long id) {

        final QuestionnaireDetailInfo info = queryQuestionnaireDetailInfoInfoById(id);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                info.deleteFromRealm();
            }
        });
    }

    /**
     * 根据id查询问卷详情信息
     *
     * @param id id
     * @return QuestionnaireDetailInfo对象
     */
    public QuestionnaireDetailInfo queryQuestionnaireDetailInfoInfoById(long id) {

        return realm.where(QuestionnaireDetailInfo.class)
                .equalTo("id", id)
                .findFirst();
    }


    /**
     * 插入或者跟新用户信息
     *
     * @param user 用户登录信息
     */
    public void insertOrUpdateUserInfo(final List<User> user) {

        deleteAllUser();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
    }

    /**
     * 根据id查找用户信息
     *
     * @param userId 用户id
     * @return 用户登录信息
     */
    public User queryUserInfoById(String userId) {

        return realm.where(User.class)
                .equalTo("userId", userId)
                .findFirst();
    }


    /**
     * 查询所有的用户
     *
     * @return 用户集合
     */
    public List<User> queryAllUser() {

        RealmResults<User> list = realm.where(User.class).findAll();
        return realm.copyFromRealm(list);
    }

    /**
     * 删除用户数据
     */
    private void deleteAllUser() {
        final RealmResults<User> list = realm.where(User.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                list.deleteAllFromRealm();
            }
        });
    }
}
