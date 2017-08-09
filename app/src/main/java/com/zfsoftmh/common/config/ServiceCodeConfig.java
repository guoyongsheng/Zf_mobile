package com.zfsoftmh.common.config;

import android.util.SparseArray;

import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.modules.mobile_learning.library.LibraryActivity;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.AgencyMattersActivity;
import com.zfsoftmh.ui.modules.office_affairs.announcement.AnnouncementActivity;
import com.zfsoftmh.ui.modules.office_affairs.cloud_notes.CloudNoteListActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.discovery_type.DisCoveryTypeActivity;
import com.zfsoftmh.ui.modules.office_affairs.meet_the_meeting.MeetTheMeetingActivity;
import com.zfsoftmh.ui.modules.office_affairs.meeting_management.MeetingManagementActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.QuestionnaireActivity;
import com.zfsoftmh.ui.modules.personal_affairs.digital_file.DigitalFileActivity;
import com.zfsoftmh.ui.modules.personal_affairs.email.EmailActivity;
import com.zfsoftmh.ui.modules.personal_affairs.one_card.OneCardActivity;
import com.zfsoftmh.ui.modules.personal_affairs.school_weibo.SchoolWeiBoActivity;
import com.zfsoftmh.ui.modules.personal_affairs.vote_interaction.VoteInteractionActivity;
import com.zfsoftmh.ui.modules.personal_affairs.wechat_public_number.WeChatPublicNumberActivity;

/**
 * @author wesley
 * @date: 2017/3/30
 * @Description: 服务编码的配置
 */

public class ServiceCodeConfig {
    private static final String TAG = "ServiceCodeConfig";

    //存放服务编码对应的Activity
    private static SparseArray<Class<?>> map = new SparseArray<>();

    static {
        initOfficeAffairs();
        initPersonalAffairs();
        initMobileLearning();
        initEducationInformation();
    }

    /**
     * 通过服务编码获取对应的Activity
     *
     * @param serviceCode 服务编码
     * @return Activity
     */
    public static Class<?> getActivityByServiceCode(String serviceCode) {
        if (map != null) {
            try {
                int code = Integer.parseInt(serviceCode);
                return map.get(code);
            } catch (Exception e) {
                LoggerHelper.e(TAG, " getActivityByServiceCode " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * 所有办公事务的服务编码--- 待办事宜、日程、公文、通知、请示、问卷调查、失物招领、会议纪要、会议管理、会议签到
     */
    public static class OfficeAffairs {

        //待办事宜
        public static final int SERVICE_CODE_AGENCY_MATTERS = 306;

        //已办事宜
        public static final int SERVICE_CODE_HAS_BEEN_DONE = 321;

        //办结事宜
        public static final int SERVICE_CODE_SETTLEMENT = 324;

        //问卷调查
        static final int SERVICE_CODE_QUESTION_NAIRE = 453;

        //失物招领
        static final int SERVICE_CODE_LOST_AND_FOUND = 499;

        //会议纪要
        static final int SERVICE_CODE_MEETING_MINUTES = 460;

        //通知公告
        static final int SERVICE_CODE_ANNOUNCEMENT = 301;

        //会议管理
        static final int SERVICE_CODE_MEETING_MANAGEMENT = 304;

        //会议签到
        static final int SERVICE_CODE_MEET_THE_MEETING = 325;

    }

    /**
     * 所有个人事务的服务编码--- 邮件、一卡通、个人财务、通讯录、社区、数字档案、投票互动、学校微博、微信公众号
     */
    private static class PersonalAffairs {

        //我的邮件
        static final int SERVICE_CODE_EMAIL = 302;

        //一卡通
        static final int SERVICE_CODE_ONE_CARD = 506;

        //数字档案
        static final int SERVICE_CODE_DIGITAL_FILE = 508;

        //投票互动
        static final int SERVICE_CODE_VOTE_INTERACTION = 509;

        //学校微博
        static final int SERVICE_CODE_SCHOOL_WEIBO = 809;

        //微信公众号
        static final int SERVICE_CODE_WECHAT_PUBLIC_NUMBER = 484;
    }

    /**
     * 所有移动学习的服务编码--- 图书馆、知识文库、教学视频、在线测试、知识交流
     */
    private static class MobileLearning {

        //图书馆
        static final int SERVICE_CODE_LIBRARY = 903;
    }

    /**
     * 所有教务信息的服务编码--- 选课、课表、考试、迎新、离校
     */
    private static class EducationInformation {

    }

    //初始化办公事务
    private static void initOfficeAffairs() {

        //待办事宜
        map.put(OfficeAffairs.SERVICE_CODE_AGENCY_MATTERS, AgencyMattersActivity.class);

        //已办事宜
        map.put(OfficeAffairs.SERVICE_CODE_HAS_BEEN_DONE, AgencyMattersActivity.class);

        //办结事宜
        map.put(OfficeAffairs.SERVICE_CODE_SETTLEMENT, AgencyMattersActivity.class);

        //问卷调查
        map.put(OfficeAffairs.SERVICE_CODE_QUESTION_NAIRE, QuestionnaireActivity.class);

        //失物招领
        map.put(OfficeAffairs.SERVICE_CODE_LOST_AND_FOUND, DisCoveryTypeActivity.class);

        //会议纪要（云笔记）
//        map.put(OfficeAffairs.SERVICE_CODE_MEETING_MINUTES, MeetingMinutesActivity.class);
        map.put(OfficeAffairs.SERVICE_CODE_MEETING_MINUTES, CloudNoteListActivity.class);

        //通知公告
        map.put(OfficeAffairs.SERVICE_CODE_ANNOUNCEMENT, AnnouncementActivity.class);

        //会议管理
        map.put(OfficeAffairs.SERVICE_CODE_MEETING_MANAGEMENT, MeetingManagementActivity.class);

        //会议签到
        map.put(OfficeAffairs.SERVICE_CODE_MEET_THE_MEETING, MeetTheMeetingActivity.class);

    }

    //初始化个人事务
    private static void initPersonalAffairs() {

        //我的邮件
        map.put(PersonalAffairs.SERVICE_CODE_EMAIL, EmailActivity.class);

        //一卡通
        map.put(PersonalAffairs.SERVICE_CODE_ONE_CARD, OneCardActivity.class);

        //数字档案
        map.put(PersonalAffairs.SERVICE_CODE_DIGITAL_FILE, DigitalFileActivity.class);

        //投票互动
        map.put(PersonalAffairs.SERVICE_CODE_VOTE_INTERACTION, VoteInteractionActivity.class);

        //学校微博
        map.put(PersonalAffairs.SERVICE_CODE_SCHOOL_WEIBO, SchoolWeiBoActivity.class);

        //微信公众号
        map.put(PersonalAffairs.SERVICE_CODE_WECHAT_PUBLIC_NUMBER, WeChatPublicNumberActivity.class);
    }

    //初始化移动学习
    private static void initMobileLearning() {

        //图书馆
        map.put(MobileLearning.SERVICE_CODE_LIBRARY, LibraryActivity.class);
    }

    //初始化教务信息
    private static void initEducationInformation() {

    }
}
