package com.zfsoftmh.ui.modules.personal_affairs.one_card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.OneCardItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: 适配器
 */

class OneCardAdapter extends RecyclerView.Adapter<OneCardAdapter.ItemViewHolder> {

    private LayoutInflater inflater;
    private OnItemClickListener listener;
    private List<OneCardItemInfo> list = new ArrayList<>();

    OneCardAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);

            //卡片充值
            OneCardItemInfo info_card_recharge = new OneCardItemInfo();
            info_card_recharge.setResId(R.mipmap.ic_icon_card_recharge);
            info_card_recharge.setName(context.getResources().getString(R.string.card_recharge));
            list.add(info_card_recharge);

            //消费明细
            OneCardItemInfo info_consumer_details = new OneCardItemInfo();
            info_consumer_details.setResId(R.mipmap.ic_icon_consumer_details);
            info_consumer_details.setName(context.getResources().getString(R.string.consumer_details));
            list.add(info_consumer_details);

            //充值明细
            OneCardItemInfo info_recharge_details = new OneCardItemInfo();
            info_recharge_details.setResId(R.mipmap.ic_icon_recharge_details);
            info_recharge_details.setName(context.getResources().getString(R.string.recharge_details));
            list.add(info_recharge_details);

            //卡片挂失
            OneCardItemInfo info_card_reported_loss = new OneCardItemInfo();
            info_card_reported_loss.setResId(R.mipmap.ic_icon_reported_loss);
            info_card_reported_loss.setName(context.getResources().getString(R.string.card_reported_loss));
            list.add(info_card_reported_loss);
        }
    }


    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.item_one_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        OneCardItemInfo info = list.get(position);
        int resId = info.getResId(); //图片资源id
        String name = info.getName(); //名称

        holder.iv_icon.setImageResource(resId);
        holder.tv_name.setText(name);

        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    /**
     * 静态内部类
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_item; //布局
        private ImageView iv_icon; //图标
        private TextView tv_name; //名称

        ItemViewHolder(View itemView) {
            super(itemView);

            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_one_card);
            iv_icon = (ImageView) itemView.findViewById(R.id.one_card_icon);
            tv_name = (TextView) itemView.findViewById(R.id.one_card_name);
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(int position);
    }
}
