package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import com.zfsoftmh.entity.FileInfo;

/**
 * Created by sy
 * on 2017/7/11.
 * <p>文件发送的接口</p>
 */

interface FileSender {

    /**
     * 发送文件
     * @param type 类别 1:文档 2:音频 3:视频 4:图片
     * @param info 详情
     */
    void sendFile(int type, FileInfo info);

}
