package com.zfsoftmh.ui.modules.chatting.contact;

import com.alibaba.mobileim.contact.IYWContactCacheUpdateListener;

public class ContactCacheUpdateListenerImpl implements IYWContactCacheUpdateListener {

    /**
     * 好友缓存发生变化(联系人备注修改、联系人新增和减少等)，可以刷新使用联系人缓存的UI
     * todo 该回调在UI线程回调 ，请勿做太重的操作
     */
    @Override
    public void onFriendCacheUpdate(String currentUserId, String currentAppKey) {

    }
}
