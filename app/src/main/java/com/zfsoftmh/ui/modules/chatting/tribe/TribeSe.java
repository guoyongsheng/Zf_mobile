package com.zfsoftmh.ui.modules.chatting.tribe;

import android.text.TextUtils;

import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeCheckMode;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeRole;
import com.alibaba.mobileim.gingko.model.tribe.YWTribeType;
import com.alibaba.mobileim.utility.PinYinUtil;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/25.
 * <p></p>
 */

public class TribeSe implements YWTribe {

    private long tribeId;
    private String tribeName;
    private String tribeIcon;
    private int memberCount;

    private String nameSpell;

    public String getNameSpell() {
        return nameSpell;
    }

    public String getShortName() {
        return shortName;
    }

    private String shortName;

    public TribeSe(YWTribe tribe){
        this.tribeId = tribe.getTribeId();
        this.tribeName = tribe.getTribeName();
        this.tribeIcon = tribe.getTribeIcon();
        this.memberCount = tribe.getMemberCount();
        generateSpell();
    }

    private void generateSpell(){
        String name = tribeName;
        if (!TextUtils.isEmpty(name)){
            ArrayList<String> shortNamesList = new ArrayList<>();
            ArrayList<String> nameSpellsList = new ArrayList<>();
            int length = name.length();
            for (int c = 0; c < length; ++c) {
                ArrayList pinYins = PinYinUtil.findPinyin(name.charAt(c));
                if (pinYins != null && pinYins.size() > 0) {
                    int snSize = shortNamesList.size();
                    ArrayList<String> shortNamesCache = new ArrayList<>();
                    ArrayList<String> nameSpellsCache = new ArrayList<>();
                    int j = 0;

                    for (int size = pinYins.size(); j < size; ++j) {
                        String pinyin = (String) pinYins.get(j);
                        if (pinyin != null) {
                            if (snSize == 0) {
                                shortNamesCache.add(String.valueOf(pinyin.charAt(0)));
                                nameSpellsCache.add(pinyin);
                            } else {
                                for (int k = 0; k < snSize; ++k) {
                                    try {
                                        shortNamesCache.add( shortNamesList.get(k) + String.valueOf(pinyin.charAt(0)));
                                        nameSpellsCache.add( nameSpellsList.get(k) + pinyin);
                                    } catch (OutOfMemoryError e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    if (shortNamesCache.size() > 0) {
                        shortNamesList.clear();
                        shortNamesList.addAll(shortNamesCache);
                        nameSpellsList.clear();
                        nameSpellsList.addAll(nameSpellsCache);
                    }
                }
            }

            String[] nameSpells = nameSpellsList.toArray(new String[0]);

            try {
                nameSpell = TextUtils.join("\r", nameSpells);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }

            String[] shortNames = shortNamesList.toArray(new String[0]);

            try {
                shortName = TextUtils.join("\r", shortNames);
            } catch (OutOfMemoryError var15) {
                var15.printStackTrace();
            }

        }
    }


    @Override
    public long getTribeId() {
        return this.tribeId;
    }

    @Override
    public String getTribeName() {
        return tribeName;
    }

    @Override
    public String getTribeNotice() {
        return null;
    }

    @Override
    public String getTribeIcon() {
        return this.tribeIcon;
    }

    @Override
    public YWTribeType getTribeType() {
        return null;
    }

    @Override
    public String getMasterId() {
        return null;
    }

    @Override
    public int getMsgRecType() {
        return 0;
    }

    @Override
    public int getAtFlag() {
        return 0;
    }

    @Override
    public YWTribeCheckMode getTribeCheckMode() {
        return null;
    }

    @Override
    public YWTribeRole getTribeRole() {
        return null;
    }

    @Override
    public int getMemberCount() {
        return this.memberCount;
    }

    @Override
    public IYWContact getTribeMaster() {
        return null;
    }

    @Override
    public boolean isEnableAtAll() {
        return false;
    }
}
