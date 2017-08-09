package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;

/**
 * @author wangshimei
 * @date: 17/7/5
 * @Description:
 */

public class IntegralMallGoodsAdapter extends RecyclerArrayAdapter<IntegralMallGoodsInfo> {
    private Context context;


    public IntegralMallGoodsAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public void addHeader(ItemView view) {
        super.addHeader(view);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralGoodsHolder(parent, R.layout.item_integral_mall_goods);
    }


    private class IntegralGoodsHolder extends BaseViewHolder<IntegralMallGoodsInfo> {
        private ImageView item_integral_goods_image; // 积分商品图
        private TextView item_integral_goods_name; // 积分商品名
        private TextView item_integral_number; // 兑换所需积分

        public IntegralGoodsHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            item_integral_goods_image = $(R.id.item_integral_goods_image);
            item_integral_goods_name = $(R.id.item_integral_goods_name);
            item_integral_number = $(R.id.item_integral_number);
        }

        @Override
        public void setData(IntegralMallGoodsInfo data) {
            super.setData(data);

            String goodsName = data.goodsname;
            String integralNumber = String.valueOf(data.numbericvalue);

            if (data.picPathList != null && data.picPathList.size() > 0){
                String image = data.picPathList.get(0);
                ImageLoaderHelper.loadImage(context, item_integral_goods_image, image);

            }
            if (goodsName != null)
                item_integral_goods_name.setText(goodsName);
            if (integralNumber != null)
                item_integral_number.setText(integralNumber);
        }
    }


}
