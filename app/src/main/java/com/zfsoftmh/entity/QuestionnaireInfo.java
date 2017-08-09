package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-6
 * @Description: 问卷调查实体类
 */

public class QuestionnaireInfo {

    private boolean isOvered;

    private List<QuestionnaireItemInfo> list;

    public void setOvered(boolean overed) {
        isOvered = overed;
    }

    public boolean isOvered() {
        return isOvered;
    }

    public void setList(List<QuestionnaireItemInfo> list) {
        this.list = list;
    }

    public List<QuestionnaireItemInfo> getList() {
        return list;
    }
}
