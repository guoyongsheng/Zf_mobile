package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 问卷调查适配器
 */

class QuestionnairePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    QuestionnairePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (list != null && list.size() > position) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setDataSource(List<Fragment> list) {
        this.list = list;
    }
}
