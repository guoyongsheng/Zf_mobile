package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/7/6.
 * <p>文件浏览--ViewPager选择类型适配器</p>
 */

class FileTypeViewPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fmgList;
    private ArrayList<String> titleList;
    private FragmentManager manager;

    FileTypeViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
        super(fm);
        this.manager = fm;
        this.fmgList = list;
        this.titleList = titles;
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,position);
        manager.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fmgList.get(position);
        manager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    @Override
    public Fragment getItem(int position) {
        return fmgList.get(position);
    }

    @Override
    public int getCount() {
        return fmgList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
