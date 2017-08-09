package com.zfsoftmh.ui.modules.chatting.helper;

import com.alibaba.mobileim.contact.IYWContact;

/**
 * Created by sy
 * on 2017/5/10.
 * <p>好友添加结果</p>
 */

public interface FriendAddAcceptListener {
    void onAccept(IYWContact contact);
    void onRefuse();
}
