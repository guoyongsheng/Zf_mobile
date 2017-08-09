package com.zfsoftmh.ui.modules.personal_affairs.contacts.contacts_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.ContactsDetailInfo;
import com.zfsoftmh.entity.ContactsItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: 适配器
 */

class ContactsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;

    private LayoutInflater inflater;
    private List<ContactsDetailInfo> list;
    private OnItemClickListener listener;

    ContactsDetailAdapter(Context context, ContactsItemInfo info) {

        if (context != null) {
            inflater = LayoutInflater.from(context);

            if (info != null) {
                list = new ArrayList<>();
                ContactsDetailInfo info_header = new ContactsDetailInfo();
                list.add(info_header);

                //姓名
                String name = info.getName();
                if (!TextUtils.isEmpty(name)) {
                    ContactsDetailInfo info_name = new ContactsDetailInfo();
                    info_name.setKey(context.getResources().getString(R.string.name));
                    info_name.setValue(name);
                    info_name.setPhone(false);
                    list.add(info_name);
                }

                //手机号
                String phone = info.getPhone();
                if (!TextUtils.isEmpty(phone)) {
                    ContactsDetailInfo info_phone = new ContactsDetailInfo();
                    info_phone.setKey(context.getResources().getString(R.string.phone));
                    info_phone.setValue(phone);
                    info_phone.setPhone(true);
                    list.add(info_phone);
                }

                //部门
                String department = info.getDepartment();
                if (!TextUtils.isEmpty(department)) {
                    ContactsDetailInfo info_depart = new ContactsDetailInfo();
                    info_depart.setKey(context.getResources().getString(R.string.department));
                    info_depart.setValue(department);
                    info_depart.setPhone(false);
                    list.add(info_depart);
                }

                //邮箱
                String email = info.getEmail();
                if (!TextUtils.isEmpty(email)) {
                    ContactsDetailInfo info_email = new ContactsDetailInfo();
                    info_email.setKey(context.getResources().getString(R.string.email));
                    info_email.setValue(email);
                    info_email.setPhone(false);
                    list.add(info_email);
                }
            }
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

        View view;
        switch (viewType) {
        /*
         *  header
         */
        case TYPE_0:
            view = inflater.inflate(R.layout.item_contacts_detail_header, parent, false);
            return new HeaderViewHolder(view);

        /*
         *  content
         */
        case TYPE_1:
            view = inflater.inflate(R.layout.item_contacts_detail, parent, false);
            return new ItemViewHolder(view);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         *  header
         */
        case TYPE_0:
            break;

        /*
         * content
         */
        case TYPE_1:
            if (holder instanceof ItemViewHolder) {
                ContactsDetailInfo info = list.get(position);
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.tv_key.setText(info.getKey());
                final String value = info.getValue();
                itemViewHolder.tv_value.setText(value);

                boolean isPhone = info.isPhone();
                if (isPhone) {
                    itemViewHolder.iv_icon.setVisibility(View.VISIBLE);
                    itemViewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onItemClick(value);
                            }
                        }
                    });
                } else {
                    itemViewHolder.iv_icon.setVisibility(View.GONE);
                }
            }
            break;

        default:
            break;
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_0;
        }
        return TYPE_1;
    }


    //content
    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_key;
        private TextView tv_value;
        private ImageView iv_icon;
        private RelativeLayout rl_item;

        ItemViewHolder(View itemView) {
            super(itemView);

            tv_key = (TextView) itemView.findViewById(R.id.item_contacts_detail_key);
            tv_value = (TextView) itemView.findViewById(R.id.item_contacts_detail_value);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_contacts_call);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_rl);
        }
    }

    //header
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    interface OnItemClickListener {

        void onItemClick(String phone);
    }

}
