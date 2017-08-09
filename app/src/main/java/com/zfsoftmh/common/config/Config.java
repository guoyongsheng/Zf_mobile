package com.zfsoftmh.common.config;

import android.os.Environment;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 基本配置信息
 */
public class Config {

    /**
     * url相关的
     */
    public static final class URL {

        //通用的url
        public static final String BASE_URL =
                //"http://10.71.33.72:8088/";
                //"http://10.71.33.201:8090/";
                "http://portal.zfsoft.com:9090/";
        //版本校验的url
        public static final String BASE_URL_VERSION_CHECK = "http://10.71.33.72:8080/";
        // 公司的url
        public static final String BASE_URL_CORPORATION = "http://portal.zfsoft.com:9090/";

        //部门黄页的url
        public static final String DEPARTMENT_YELLOW_URL = BASE_URL + "/zftal-mobile/txl/txl_index.html";

        public static final String MOBILE_OTHER_FLAG = BASE_URL + "/zftal-mobile/oauth2/authorize/authorizeURL?";
    }

    /**
     * 学校相关的
     */
    public static final class SCHOOL {
        //聊天的appID
        public static final String CHATTING_ID = "23777550";
        //学校名称
        public static final String SCHOOL_NAME = "移动校园";
    }

    /**
     * 数据存储相关的
     */
    public static final class DB {

        //SharedPreferences中用戶是否是第一次登录Once的name和key的值
        public static final String IS_FIRST_TIME_IN_NAME = "IS_FIRST_TIME_IN_NAME";
        public static final String IS_FIRST_TIME_IN_KEY = "IS_FIRST_TIME_IN_KEY";

        //保存在SharedPreferences中用户User的name值和key值
        public static final String USER_NAME = "USER_LOGIN_NAME";
        public static final String USER_KEY = "USER_LOGIN_KEY";

        //SharedPreferences中用户是否已经登录的name值和key值 IsLogin
        public static final String IS_LOGIN_NAME = "IS_LOGIN_NAME";
        public static final String IS_LOGIN_KEY = "IS_LOGIN_KEY";

        //SharedPreferences中是否是最新版本的name值和key值 IsNewVersion
        public static final String IS_NEW_VERSION_NAME = "IS_NEW_VERSION_NAME";
        public static final String IS_NEW_VERSION_KEY = "IS_NEW_VERSION_KEY";

        //SharedPreferences中是失物招领的name值和key值
        public static final String DISCOVERY_TYPE_NAME = "DISCOVERY_TYPE_NAME";
        public static final String DISCOVERY_TYPE_KEY = "DISCOVERY_TYPE_KEY";

        //保存在SharedPreferences中分享app的url
        public static final String ShareAppUrl_NAME="SHARE_APP_URL";
        public static final String ShareAppUrl_KEY="SHARE_APP_URL";
    }

    /**
     * 版本跟新相关的
     */
    public static final class VERSION {

        //版本跟新 3: 不需要跟新  2：需要跟新  1：强制跟新
        public static final String VERSION_SHOULD_NOT_UPDATE = "3";
        public static final String VERSION_SHOULD_UPDATE = "2";
        public static final String VERSION_SHOULD_UPDATE_FORCED = "1";
    }

    /**
     * 上传文件相关的
     */
    public static final class UPLOAD {

        //上传文件的数据格式
        public static final String UPLOAD_FILE_FORMAT = "multipart/form-data";
    }

    /**
     * 加载更多相关的
     */
    public static final class LOADMORE {
        public static final int VISIBLE_THRESHOLD = 2;

        //起始页
        public static final int START_PAGE = 1;

        //每页加载多少条数据
        public static final int PAGE_SIZE = 10;
    }

    /**
     * web模块相关的
     */
    public static final class WEB {

        public static final String PRO_CODE = "pro_code";

        //跳转到web界面时的key值
        public static final String URL = "url";

        //跳转到web界面时的key值
        public static final String TITLE = "title";

        //跳转到web界面时的key值
        public static final String IMAGE_URL = "image_url";
    }

    public static final String STRkEY = "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS";

    //兩次点击的间隔时间
    public static final long INTERVAL_TIME_ON_CLICK = 1000;

    //首页轮播图最大的数量
    public static final int HOME_MAX_BANNER_SIZE = 5;

    // 云笔记file文件保存根路径
    public static final String CLOUD_NOTE_PATH = Environment.getExternalStorageDirectory() + "/cloud_note_file/";

}
