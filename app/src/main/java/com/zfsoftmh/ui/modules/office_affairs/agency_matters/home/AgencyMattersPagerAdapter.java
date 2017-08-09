package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: ViewPager的适配器
 */

class AgencyMattersPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment;
    private List<String> listTitle;

    public AgencyMattersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    AgencyMattersPagerAdapter(FragmentManager fm, List<Fragment> listFragment, List<String> listTitle) {
        super(fm);
        this.listFragment = listFragment;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        if (listFragment != null && listFragment.size() > position) {
            return listFragment.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (listFragment != null) {
            return listFragment.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (listTitle != null && listTitle.size() > position) {
            return listTitle.get(position);
        }
        return super.getPageTitle(position);
    }
}
