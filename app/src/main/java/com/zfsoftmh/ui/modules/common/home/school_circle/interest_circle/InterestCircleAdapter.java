package com.zfsoftmh.ui.modules.common.home.school_circle.interest_circle;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.InterestCircleItemInfo;

/**
 * @author wesley
 * @date: 2017-7-5
 * @Description: 适配器
 */

class InterestCircleAdapter extends RecyclerArrayAdapter<InterestCircleItemInfo> {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ITEM = 1;
    private Context context;
    private OnJoinClickListener listener;

    private ConstraintLayout.LayoutParams params;

    InterestCircleAdapter(Context context) {
        super(context);
        this.context = context;

        if (context != null) {

            int margin = context.getResources().getDimensionPixelSize(R.dimen.common_margin_left);
            int screenWidth = ScreenUtils.getScreenWidth(context);
            int size = (screenWidth - 8 * margin) / 4;
            params = new ConstraintLayout.LayoutParams(size, size);
            params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.common_margin_left);
        }
    }


    void setOnJoinClickListener(OnJoinClickListener listener) {
        this.listener = listener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
        /*
         * 标题
         */
        case TYPE_TITLE:
            return new TitleViewHolder(parent, R.layout.item_interest_circle_title);

        /*
         * 类型
         */
        case TYPE_ITEM:
            return new ItemViewHolder(parent, R.layout.item_interest_circle);

        default:
            break;
        }
        return null;
    }


    @Override
    public int getViewType(int position) {
        return TYPE_ITEM;
    }

    //标题的viewHolder
    private class TitleViewHolder extends BaseViewHolder<InterestCircleItemInfo> {

        private TextView tv_title;

        TitleViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            tv_title = $(R.id.item_interest_circle_title);
        }

        @Override
        public void setData(InterestCircleItemInfo data) {
            super.setData(data);

            tv_title.setText(R.string.can_not_miss_the_circle_of_interest);
        }
    }


    //静态内部类---item
    private class ItemViewHolder extends BaseViewHolder<InterestCircleItemInfo> {

        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private TextView tv_description; //描述
        private TextView tv_level; //等级

        public ItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            iv_icon = $(R.id.item_interest_circle_iv);
            tv_name = $(R.id.item_interest_circle_name);
            tv_description = $(R.id.item_interest_circle_description);
            tv_level = $(R.id.item_interest_circle_level);
        }

        @Override
        public void setData(final InterestCircleItemInfo data) {
            super.setData(data);

            if (data == null || context == null) {
                return;
            }

            iv_icon.setLayoutParams(params);
            ImageLoaderHelper.loadImageWithRoundedCorners(context, iv_icon, data.getUrl());
            tv_name.setText(data.getName());
            tv_description.setText(data.getDescription());

            tv_level.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemJoin(data);
                    }
                }
            });
        }
    }


    interface OnJoinClickListener {

        /**
         * 加入
         *
         * @param data 实体类
         */
        void onItemJoin(InterestCircleItemInfo data);
    }
}
