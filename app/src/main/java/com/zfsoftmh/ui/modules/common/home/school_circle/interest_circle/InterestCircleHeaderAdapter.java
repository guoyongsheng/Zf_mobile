package com.zfsoftmh.ui.modules.common.home.school_circle.interest_circle;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.InterestCircleItemInfo;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-7-6
 * @Description: 适配器
 */

class InterestCircleHeaderAdapter extends RecyclerView.Adapter<InterestCircleHeaderAdapter.ItemViewHolder> {

    private List<InterestCircleItemInfo> list;
    private Context context;
    private LayoutInflater inflater;
    private OnMyCircleClickListener listener;

    private ConstraintLayout.LayoutParams params;

    InterestCircleHeaderAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);

            int margin = context.getResources().getDimensionPixelSize(R.dimen.common_margin_left);
            int screenWidth = ScreenUtils.getScreenWidth(context);
            int size = (screenWidth - 8 * margin) / 4;
            params = new ConstraintLayout.LayoutParams(size, size);
        }
    }


    void setOnMyCircleClickListener(OnMyCircleClickListener listener) {
        this.listener = listener;
    }

    public void setDataSource(List<InterestCircleItemInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_interest_circle_header, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null
                || context == null) {
            return;
        }

        final InterestCircleItemInfo itemInfo = list.get(position);
        String name = itemInfo.getName(); //名称
        String url = itemInfo.getUrl(); //url
        int number = itemInfo.getNumber(); // 数量

        ImageLoaderHelper.loadImageWithRoundedCorners(context, holder.iv_icon, url);
        holder.tv_name.setText(name);
        holder.tv_number.setText("发布数 " + number);

        holder.iv_icon.setLayoutParams(params);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMyCircleItemClick(itemInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            int size = list.size();
            if (size >= InterestCircleFragment.MOST_SHOW_ITEM_MY_CIRCLE) {
                return InterestCircleFragment.MOST_SHOW_ITEM_MY_CIRCLE;
            }
            return list.size();
        }
        return 0;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private TextView tv_number; //数字

        public ItemViewHolder(View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.item_interest_circle_header_iv);
            tv_name = (TextView) itemView.findViewById(R.id.item_interest_circle_header_name);
            tv_number = (TextView) itemView.findViewById(R.id.item_interest_circle_header_number);
        }
    }


    interface OnMyCircleClickListener {

        /**
         * 我的圈子item点击事件
         *
         * @param itemInfo 实体类
         */
        void onMyCircleItemClick(InterestCircleItemInfo itemInfo);
    }
}
