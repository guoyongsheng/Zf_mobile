package com.zfsoftmh.ui.modules.personal_affairs.contacts.phone_contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.ContactsItemInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wesley
 * @date: 2017/4/11
 * @Description: 适配器
 */

class PhoneContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        StickyRecyclerHeadersAdapter<PhoneContactsAdapter.HeadViewHolder> {

    private List<ContactsItemInfo> list;
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener listener;

    PhoneContactsAdapter(Context context, List<ContactsItemInfo> list) {
        this.context = context;
        this.list = list;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.item_phone_contacts, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list == null || list.size() <= position || list.get(position) == null || context == null
                || holder == null || !(holder instanceof ItemViewHolder)) {
            return;
        }

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final ContactsItemInfo info = list.get(position);
        String name = info.getName(); //名称
        String imageUrl = info.getPhotoUri(); //头像路径
        itemViewHolder.tv_name.setText(name);
        ImageLoaderHelper.loadImage(context, itemViewHolder.iv_icon, imageUrl);

        itemViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(info);
                }
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        if (list != null && list.size() > position && list.get(position) != null) {
           return list.get(position).getHeaderId();
        }
        return 0;
    }

    @Override
    public HeadViewHolder onCreateHeaderViewHolder(ViewGroup parent) {

        if (inflater == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.item_phone_contacts_header, parent, false);
        return new HeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeadViewHolder holder, int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        holder.tv_value.setText(list.get(position).getFirstLetter());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    //静态内部类
    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_item; //布局
        private CircleImageView iv_icon; //头像
        private TextView tv_name; //名称

        ItemViewHolder(View itemView) {
            super(itemView);

            ll_item = (LinearLayout) itemView.findViewById(R.id.item_phone_contacts);
            iv_icon = (CircleImageView) itemView.findViewById(R.id.item_phone_contacts_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_phone_contacts_name);
        }
    }

    //静态内部类---头部的view
    static class HeadViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_value;

        HeadViewHolder(View itemView) {
            super(itemView);

            tv_value = (TextView) itemView.findViewById(R.id.item_phone_contacts_head_value);
        }
    }


    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(ContactsItemInfo info);
    }

}
