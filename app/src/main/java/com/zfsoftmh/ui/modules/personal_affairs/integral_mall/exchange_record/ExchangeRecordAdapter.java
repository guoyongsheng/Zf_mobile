package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.ExchangeRecordItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description: 兑换记录列表
 */

public class ExchangeRecordAdapter extends RecyclerArrayAdapter<ExchangeRecordItemInfo> {
    private Context context;

    public ExchangeRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExchangeRecordHolder(parent, R.layout.item_exchange_record);
    }

    private class ExchangeRecordHolder extends BaseViewHolder<ExchangeRecordItemInfo> {
        private ImageView exchange_record_image; // 兑换商品图片
        private TextView exchange_record_name; // 兑换的商品名
        private TextView exchange_record_time; // 生成兑换记录的时间
        private TextView exchange_state; // 领取状态

        public ExchangeRecordHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            exchange_record_image = $(R.id.exchange_record_image);
            exchange_record_name = $(R.id.exchange_record_name);
            exchange_record_time = $(R.id.exchange_record_time);
            exchange_state = $(R.id.exchange_state);
        }

        @Override
        public void setData(ExchangeRecordItemInfo data) {
            super.setData(data);
            String image = data.goodspicPath;
            String exchangeGoodsName = data.goodsname;
            String exchangeTime = data.createtimeStr;
            String state = data.flag;
            if (image != null)
                ImageLoaderHelper.loadImage(context, exchange_record_image, image);
            if (exchangeGoodsName != null)
                exchange_record_name.setText(exchangeGoodsName);

            if (exchangeTime != null)
                exchange_record_time.setText("兑换时间：" + exchangeTime);

            if (state != null) {
                if (state.equals("0")) {
                    exchange_state.setText("未领取");
                } else if (state.equals("1")) {
                    exchange_state.setText("已领取");
                }
            }

        }
    }
}
