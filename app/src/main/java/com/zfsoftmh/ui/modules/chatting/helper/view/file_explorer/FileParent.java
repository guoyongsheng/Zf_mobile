package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.zfsoftmh.entity.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/7/7.
 * <p>office文件展开--实现父类接口</p>
 */

class FileParent implements Parent<FileInfo> {

    private String title;
    private ArrayList<FileInfo> dataList = new ArrayList<>();

    FileParent(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setDataList(ArrayList<FileInfo> dataList) {
        this.dataList = dataList;
    }

    @Override
    public List<FileInfo> getChildList() {
        return dataList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
