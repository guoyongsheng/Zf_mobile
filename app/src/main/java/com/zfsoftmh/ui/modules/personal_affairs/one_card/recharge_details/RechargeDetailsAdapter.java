package com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.OneCardItemDetailsInfo;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 适配器
 */

class RechargeDetailsAdapter extends RecyclerArrayAdapter<OneCardItemDetailsInfo> {

    private String balance;

    RechargeDetailsAdapter(Context context) {
        super(context);
        if (context != null) {
            balance = context.getResources().getString(R.string.balance);
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_recharge_details);
    }

    /**
     * 静态内部类
     */
    private class ItemViewHolder extends BaseViewHolder<OneCardItemDetailsInfo> {

        private TextView tv_location; //地点
        private TextView tv_balance; //余额
        private TextView tv_time; //时间
        private TextView tv_money; //金钱

        ItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            tv_location = $(R.id.item_recharge_details_location);
            tv_balance = $(R.id.item_recharge_details_balance);
            tv_time = $(R.id.item_recharge_details_time);
            tv_money = $(R.id.item_recharge_details_money);
        }

        @Override
        public void setData(OneCardItemDetailsInfo info) {
            super.setData(info);

            if (info == null) {
                return;
            }

            String location = info.getConsumeAspect(); //地点
            String balances = info.getBalance(); //余额
            String time = info.getConsumetime(); //时间
            String money = info.getOutlay(); //金额

            tv_location.setText(location);
            tv_balance.setText(balance + "" + balances);
            tv_money.setText(money);
            tv_time.setText(time);
        }
    }
}
