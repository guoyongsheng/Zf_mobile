package com.zfsoftmh.ui.modules.chatting.contact;

import android.text.TextUtils;

import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWDBContact;
import com.alibaba.mobileim.utility.PinYinUtil;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/2.
 */
public class ChatContact implements IYWContact {

    private String userId = "";
    private String appKey = "";
    private String avatarPath = "";
    private String showName = "";
    private String firstChar = "";

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ChatContact(IYWDBContact contact) {
        this.showName = contact.getShowName();
        this.userId = contact.getUserId();
        this.avatarPath = contact.getAvatarPath();
        this.appKey = contact.getAppKey();
        this.firstChar = contact.getFirstChar();
    }

    public ChatContact(String userId, String appKey, String avatarPath, String showName) {
        this.userId = userId;
        this.appKey = appKey;
        this.avatarPath = avatarPath;
        this.showName = showName;
    }

    public ChatContact(){

    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    private int headerId;

    public String getFirstChar() {
        return firstChar;
    }

    public int getHeaderId() {
        return headerId;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getAppKey() {
        return this.appKey;
    }

    @Override
    public String getAvatarPath() {
        return this.avatarPath;
    }

    @Override
    public String getShowName() {
        return this.showName;
    }


    public String getNameSpell() {
        return nameSpell;
    }

    public String getShortName() {
        return shortName;
    }

    private String nameSpell;
    private String shortName;
    public void generateSpell() {
        String name = showName + " " + userId;
        if (!TextUtils.isEmpty(name)) {
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

            if (shortNames.length > 0 && !TextUtils.isEmpty(shortNames[0])) {
                char var18 = shortNames[0].charAt(0);
                this.firstChar = String.valueOf(var18);
            }
        }

    }

}
