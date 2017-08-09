package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.OrderForminfo;

import static com.zfsoftmh.R.id.tv_orderform_onmore;

/**
 * Created by ljq
 * on 2017/7/19.
 */


public class OrderFormAdapter extends RecyclerArrayAdapter<OrderForminfo> {


    public static int TYPE_EATERYNAME = 0;
    public static int TYPE_ONEMORE = 1;
    public static int TYPE_DETAIL = 2;

    Context context;
    OnMyItemViewClickListener listener;
    int size;

    public OrderFormAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.orderform_item);
    }


    class ViewHolder extends BaseViewHolder<OrderForminfo> implements View.OnClickListener {
        ImageView iv_photo;
        TextView tv_name;
        TextView tv_cost;
        TextView tv_time;
        TextView tv_state;
        TextView tv_onemore;
        OnMyItemViewClickListener listener;

        public ViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            iv_photo = $(R.id.iv_orderform);
            tv_name = $(R.id.tv_orderform_eateryname);
            tv_cost = $(R.id.tv_orderform_cost);
            tv_time = $(R.id.tv_orderform_time);
            tv_state = $(R.id.tv_orderform_state);
            tv_onemore = $(tv_orderform_onmore);


        }

        @Override
        public void setData(OrderForminfo data) {
            super.setData(data);
            EateryInfo info = data.getCanteen();
            if (info != null) {
                tv_name.setText(info.getCanteenName());
                tv_cost.setText(data.getSummation());
                tv_time.setText(data.getCreatetimeStr());
                int flag = Integer.valueOf(data.getFlag());
                switch (flag) {
                    case 0:
                        tv_state.setText("商家未接单");
                        break;
                    case 1:
                        tv_state.setText("商家已接单");
                        break;
                    case 2:
                        tv_state.setText("商家拒单");
                        break;
                    default:
                        break;
                }
                ImageLoaderHelper.loadImage(context, iv_photo, info.getPicPath());
            }
        }


        @Override
        public void onClick(View v) {
            int i = v.getId();
            int type = 0;
            if (i == R.id.iv_orderform || i == R.id.tv_orderform_eateryname) {
                type = TYPE_EATERYNAME;
            } else if (i == R.id.tv_orderform_onmore) {
                type = TYPE_ONEMORE;
            } else {
                type = TYPE_DETAIL;
            }
            listener.setOnMyItemViewClick(getLayoutPosition(), v, type);
        }
    }

    interface OnMyItemViewClickListener {
        public void setOnMyItemViewClick(int pos, View v, int Type);
    }
}
