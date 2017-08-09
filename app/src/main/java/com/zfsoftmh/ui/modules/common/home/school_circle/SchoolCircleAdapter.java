package com.zfsoftmh.ui.modules.common.home.school_circle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zfsoftmh.ui.modules.common.home.school_circle.interest_circle.InterestCircleFragment;
import com.zfsoftmh.ui.modules.common.home.school_circle.my_attention.MyAttentionFragment;
import com.zfsoftmh.ui.modules.common.home.school_circle.school_dynamics.SchoolDynamicsFragment;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-7-17
 * @Description: 适配器
 */

class SchoolCircleAdapter extends FragmentPagerAdapter {

    private List<String> listTitle;

    SchoolCircleAdapter(FragmentManager fm, List<String> listTitle) {
        super(fm);
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
        /*
         * 校园动态
         */
        case 0:
            return SchoolDynamicsFragment.newInstance();

        /*
         * 我的关注
         */
        case 1:
            return MyAttentionFragment.newInstance();

        /*
         * 兴趣圈
         */
        case 2:
            return InterestCircleFragment.newInstance();


        default:
            break;
        }
        return null;
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
        return super.getPageTitle(position);
    }
}
