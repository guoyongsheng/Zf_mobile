package com.zfsoftmh.ui.modules.personal_affairs.integral_mall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record.ExchangeRecordFragment;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search.IntegralGoodsSearchFragment;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income.IntegralIncomeFragment;

import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/24
 * @Description:
 */

public class IntegralMallHomeAdapter extends FragmentPagerAdapter {

    private List<String> listTitle;

    public IntegralMallHomeAdapter(FragmentManager fm, List<String> listTitle) {
        super(fm);
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            /**
             * 积分兑换商品
             */
            case 0:
                return IntegralGoodsSearchFragment.newInstance();
            /**
             * 兑换记录
             */
            case 1:
                return ExchangeRecordFragment.newInstance();
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
