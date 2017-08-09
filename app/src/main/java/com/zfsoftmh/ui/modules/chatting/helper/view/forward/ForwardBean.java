package com.zfsoftmh.ui.modules.chatting.helper.view.forward;

import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeSe;

/**
 * Created by sy
 * on 2017/5/25.
 */

class ForwardBean {
    ChatContact contact;
    TribeSe tribe;
    ForwardBean(ChatContact contact, TribeSe tribe) {
        this.contact = contact;
        this.tribe = tribe;
    }
}
