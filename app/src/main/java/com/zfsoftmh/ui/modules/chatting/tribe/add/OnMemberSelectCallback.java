package com.zfsoftmh.ui.modules.chatting.tribe.add;

import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/23.
 * <p>成员选择回调接口</p>
 */
public interface OnMemberSelectCallback {

    void OnMemberSelect(ArrayList<ChatContact> selectData);

}
