package com.zfsoftmh.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangshimei
 * @date: 17/5/22
 * @Description: 云笔记列表
 */

public class CloudNoteInfo {

    /**
     * data : {"isOvered":true,"memoList":[{"memoCatalogId":"48944E4B1690545BE0538513470AC4A1","memoTitle":"Test5？","memoFileName":"12111490670396.rtfd","createTime":"2017-03-28 11:06:36.0","memoPath":"http://10.71.33.72:8088/zftal-mobile/memoFile/9941490670396.rtfd","userName":"1211","contentFlag":"3","toPage":1,"perPageSize":20,"totalItem":0,"startRow":0,"endRow":0}]}
     */

    public boolean isOvered;
    public List<CloudNoteListInfo> memoList;

    public CloudNoteInfo() {
    }

    public CloudNoteInfo(boolean isOvered, List<CloudNoteListInfo> memoList) {
        this.isOvered = isOvered;
        this.memoList = memoList;
    }




}
