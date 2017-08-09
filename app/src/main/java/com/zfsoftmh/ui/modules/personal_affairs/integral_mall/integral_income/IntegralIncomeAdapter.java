package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_income;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.IntegralIncomeItemInfo;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description: 积分明细
 */

public class IntegralIncomeAdapter extends RecyclerArrayAdapter<IntegralIncomeItemInfo> {


    public IntegralIncomeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralIncomeHolder(parent, R.layout.item_integral_income);
    }

    private class IntegralIncomeHolder extends BaseViewHolder<IntegralIncomeItemInfo> {
        private TextView integral_income_origin; // 积分明细来源
        private TextView integral_income_time; // 积分明细创建时间
        private TextView integral_income_number; // 积分明细积分数值

        public IntegralIncomeHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            integral_income_origin = $(R.id.integral_income_origin);
            integral_income_time = $(R.id.integral_income_time);
            integral_income_number = $(R.id.integral_income_number);
        }

        @Override
        public void setData(IntegralIncomeItemInfo data) {
            super.setData(data);
            String integralOrigin = data.appsource; // 积分来源
            String createTime = data.createtimeStr; // 积分创建时间
            int source = data.source; // 积分数值

            if (integralOrigin != null) {
                if (integralOrigin.equals("1")) {
                    integral_income_origin.setText("移动端签到");
                } else if (integralOrigin.equals("2")) {
                    integral_income_origin.setText("Web");
                } else {
                    integral_income_origin.setText("其他");
                }
            }

            if (createTime != null)
                integral_income_time.setText(createTime);
            integral_income_number.setText("+" + source);
        }
    }
}
