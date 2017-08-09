package com.zfsoftmh.ui.modules.chatting.helper;

import android.os.Environment;

import com.alibaba.wxlib.util.SimpleKVStore;

/**
 * Created by sy
 * 2017/4/27.
 * <p>一些持久化存储</p>
 */
public class KVStoreHelper {
	
	public final static String STORE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/zfSoft/images/";

	private static String NEED_SOUND = "key_need_sound";//是否静音
    private static String NEED_VIBRATION = "key_need_vibration";//是否震动
    private static String PLAY_MODE = "key_voice_play_mode";//听筒播放还是扬声器播放

    public static boolean nickShow(){
        return SimpleKVStore.getBooleanPrefs("key_need_nick", true);
    }

    public static void setNickShow(boolean b){
        SimpleKVStore.setBooleanPrefs("key_need_nick", b);
    }

    public static boolean writingShow(){
        return SimpleKVStore.getBooleanPrefs("key_need_writing_state", true);
    }

    public static void setWritingShow(boolean b){
        SimpleKVStore.setBooleanPrefs("key_need_writing_state", b);
    }
    
    public static boolean getNeedSound(){
        return SimpleKVStore.getBooleanPrefs(NEED_SOUND, true);
    }

    public static void setUseInCallMode(boolean b){
        SimpleKVStore.setBooleanPrefs(PLAY_MODE, b);
    }

    public static boolean getUseInCallMode(){
        return SimpleKVStore.getBooleanPrefs(PLAY_MODE, true);
    }

    public static void setNeedSound(boolean value){
        SimpleKVStore.setBooleanPrefs(NEED_SOUND, value);
    }

    public static boolean getNeedVibration(){
        return SimpleKVStore.getBooleanPrefs(NEED_VIBRATION, true);
    }

    public static void setNeedVibration(boolean value){
        SimpleKVStore.setBooleanPrefs(NEED_VIBRATION, value);
    }

    public static void setNeverDisturb(boolean b){
        SimpleKVStore.setBooleanPrefs("key_never_disturb", b);
    }

    public static boolean getNeverDisturb(){
        return SimpleKVStore.getBooleanPrefs("key_never_disturb", false);
    }

//    private static String getUserId(){
//        return IMKitHelper.getInstance().getIMKit().getIMCore().getLongLoginUserId();
//    }
}
