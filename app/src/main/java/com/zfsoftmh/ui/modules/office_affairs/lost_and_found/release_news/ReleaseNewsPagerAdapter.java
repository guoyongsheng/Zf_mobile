package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 适配器
 */

class ReleaseNewsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    ReleaseNewsPagerAdapter(FragmentManager fm) {
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
