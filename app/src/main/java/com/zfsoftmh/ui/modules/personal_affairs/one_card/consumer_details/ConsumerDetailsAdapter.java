package com.zfsoftmh.ui.modules.personal_affairs.one_card.consumer_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.OneCardItemDetailsInfo;
import com.zfsoftmh.ui.widget.textdrawable.ColorGenerator;
import com.zfsoftmh.ui.widget.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 适配器
 */

class ConsumerDetailsAdapter extends RecyclerView.Adapter<ConsumerDetailsAdapter.ItemViewHolder>
        implements StickyRecyclerHeadersAdapter<ConsumerDetailsAdapter.HeaderViewHolder> {

    private int isLoadOrRefreshing; // 0: 下拉刷新  1: 滚动加载
    private LayoutInflater inflater;
    private List<OneCardItemDetailsInfo> list = new ArrayList<>();

    private String no_location; //未知地点
    private ColorGenerator colorGenerator;

    ConsumerDetailsAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);
            no_location = context.getResources().getString(R.string.no_location);
            colorGenerator = ColorGenerator.MATERIAL;
        }
    }

    void setIsLoadOrRefreshing(int isLoadOrRefreshing) {
        this.isLoadOrRefreshing = isLoadOrRefreshing;
    }

    void setDataSource(List<OneCardItemDetailsInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_consumer_detail, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        OneCardItemDetailsInfo info = list.get(position);
        String time = info.getConsumetime(); //时间
        String location = info.getConsumeAspect(); //位置
        String money = info.getOutlay(); //消费

        if (TextUtils.isEmpty(location)) {
            location = no_location;
        }

        holder.tv_time.setText(time);
        holder.tv_location.setText(location);
        holder.tv_money.setText(money);

        int start = location.length() - 1;
        if (start < 0) {
            start = 0;
        }
        int end = location.length();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(location.substring(start, end), colorGenerator.getColor(location));
        holder.iv_icon.setImageDrawable(drawable);
    }

    @Override
    public long getHeaderId(int position) {
        if (list != null && list.size() > position && list.get(position) != null) {
            return list.get(position).getHeaderId();
        }
        return -1;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        if (inflater == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.item_phone_contacts_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {

        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        String time = list.get(position).getHeader();
        holder.tv_value.setText(time);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    List<OneCardItemDetailsInfo> getAllItems() {
        return list;
    }


    /**
     * 静态内部类
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_time; //时间
        private ImageView iv_icon; //头像
        private TextView tv_location; //位置
        private TextView tv_money; //消费

        ItemViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.item_consumer_details_time);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_consumer_details_icon);
            tv_location = (TextView) itemView.findViewById(R.id.item_consumer_details_location);
            tv_money = (TextView) itemView.findViewById(R.id.item_consumer_details_money);
        }
    }

    /**
     * 静态内部类--- Header
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_value;

        HeaderViewHolder(View itemView) {
            super(itemView);

            tv_value = (TextView) itemView.findViewById(R.id.item_phone_contacts_head_value);
        }
    }
}
