package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-5-27
 * @Description: 事务招领适配器
 */

class LostAndFoundPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    LostAndFoundPagerAdapter(FragmentManager fm) {
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
        notifyDataSetChanged();
    }
}
