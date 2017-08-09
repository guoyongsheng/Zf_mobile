package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import com.zfsoftmh.entity.FileInfo;

/**
 * Created by sy
 * on 2017/7/11.
 * <p>文件点击事件</p>
 */

interface FileClickListener {

    /**
     * @param info 文件信息
     */
    void onFileClick(FileInfo info);
}
