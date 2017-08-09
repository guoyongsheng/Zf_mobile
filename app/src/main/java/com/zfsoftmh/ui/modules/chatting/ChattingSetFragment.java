package com.zfsoftmh.ui.modules.chatting;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.modules.chatting.helper.KVStoreHelper;

/**
 * Created by sy
 * on 2017/5/31.
 */

public class ChattingSetFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public static ChattingSetFragment newInstance(){
        return new ChattingSetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.chat_set);
        sBindPreference(findPreference("key_never_disturb"));
        sBindPreference(findPreference("key_voice_play_mode"));
        sBindPreference(findPreference("key_need_sound"));
        sBindPreference(findPreference("key_need_vibration"));
        sBindPreference(findPreference("key_need_nick"));
        sBindPreference(findPreference("key_need_writing_state"));
    }

    private void sBindPreference(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference instanceof SwitchPreference){
            boolean state = (boolean) newValue;
            if (preference.hasKey()){
                switch (preference.getKey()){
                    case "key_never_disturb":
                        KVStoreHelper.setNeverDisturb(state);
                        break;
                    case "key_voice_play_mode":
                        KVStoreHelper.setUseInCallMode(state);
                        break;
                    case "key_need_sound":
                        KVStoreHelper.setNeedSound(state);
                        break;
                    case "key_need_vibration":
                        KVStoreHelper.setNeedVibration(state);
                        break;
                    case "key_need_nick":
                        KVStoreHelper.setNickShow(state);
                        break;
                    case "key_need_writing_state":
                        KVStoreHelper.setWritingShow(state);
                        break;
                }
            }
        }
        return true;
    }
}
