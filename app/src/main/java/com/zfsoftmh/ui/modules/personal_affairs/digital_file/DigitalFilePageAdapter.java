package com.zfsoftmh.ui.modules.personal_affairs.digital_file;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zfsoftmh.ui.modules.personal_affairs.digital_file.detail.DigitalFileDetailFragment;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-16
 * @Description:
 */

class DigitalFilePageAdapter extends FragmentStatePagerAdapter {

    private List<String> listTitle;

    DigitalFilePageAdapter(FragmentManager fm, List<String> listTitle) {
        super(fm);
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return DigitalFileDetailFragment.newInstance(String.valueOf(position));
    }

    @Override
    public int getCount() {
        if (listTitle != null) {
            return listTitle.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (listTitle != null && listTitle.size() > position) {
            return listTitle.get(position);
        }
        return "";
    }

    public void setDataSource(List<String> listTitle) {
        this.listTitle = listTitle;
        notifyDataSetChanged();
    }
}
