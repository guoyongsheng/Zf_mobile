package com.zfsoftmh.ui.modules.chatting.tribe.add;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;
import com.zfsoftmh.ui.widget.textdrawable.ColorGenerator;
import com.zfsoftmh.ui.widget.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/17.
 */

class MemberSelectAdapter extends RecyclerView.Adapter<MemberSelectAdapter.SelectHolder>implements
        StickyRecyclerHeadersAdapter<MemberSelectAdapter.HeadViewHolder> {

    private List<ChatContact> dataList;
    private ArrayList<ChatContact> selectContacts;
    private Context mContext;
    private ColorGenerator generator;

    MemberSelectAdapter(Context context){
        mContext = context;
        generator = ColorGenerator.MATERIAL;
        dataList = new ArrayList<>();
        selectContacts = new ArrayList<>();
    }

    private ArrayList<String> memberIDs;
    void setDataList(List<ChatContact> data,ArrayList<String> memberIDs){
        this.memberIDs = memberIDs;
        this.dataList.clear();
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    ArrayList<ChatContact> getSelectContacts(){
        return selectContacts;
    }

    @Override
    public SelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_member, parent, false);
        return new SelectHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectHolder holder, int position) {
        ChatContact mFriend = dataList.get(position);
        holder.tvName.setText(mFriend.getShowName());
        String mFriendAvatar = mFriend.getAvatarPath();
        if(TextUtils.isEmpty(mFriendAvatar)){
            TextDrawable draw= TextDrawable.builder().beginConfig()
                    .fontSize(30).useFont(Typeface.DEFAULT).endConfig()
                    .buildRound(mFriend.getShowName().substring(0,1),generator.getColor(mFriend.getShowName()));
            holder.header.setImageDrawable(draw);
        }else{
            ImageLoaderHelper.loadDefConfCirCleImage(mContext, holder.header, mFriendAvatar, 0);
        }
        if (memberIDs != null){
            if (memberIDs.contains(mFriend.getUserId())){
                holder.checkBox.setVisibility(View.GONE);
                holder.tvMember.setVisibility(View.VISIBLE);
            }else{
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.tvMember.setVisibility(View.GONE);
                holder.checkBox.setChecked(mFriend.isSelected());
            }
        }else{
            holder.checkBox.setChecked(mFriend.isSelected());
        }


    }

    @Override
    public long getHeaderId(int position) {
        if (getItemCount() > position && dataList.size() > 0) {
            return dataList.get(position).getHeaderId();
        }
        return 0;
    }

    @Override
    public HeadViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_contacts_header, parent, false);
        return new HeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeadViewHolder holder, int position) {
        String str = dataList.get(position).getFirstChar().toUpperCase();
        holder.tv_value.setText(isAtoZ(str)?str:"#");
    }

    //头部的view
    class HeadViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_value;
        HeadViewHolder(View itemView) {
            super(itemView);
            tv_value = (TextView) itemView.findViewById(R.id.item_phone_contacts_head_value);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    boolean isAtoZ(String str){
        return Character.isLetter(str.charAt(0));
    }

    class SelectHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        TextView tvName;
        ImageView header;
        CheckBox checkBox;
        TextView tvMember;
        SelectHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.item_select_contacts_name);
            header = (ImageView) itemView.findViewById(R.id.item_select_contacts_icon);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_select_check_box);
            tvMember = (TextView) itemView.findViewById(R.id.item_select_member);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            dataList.get(getLayoutPosition()).setSelected(isChecked);
            if (isChecked)
                selectContacts.add(dataList.get(getLayoutPosition()));
            else
                selectContacts.remove(dataList.get(getLayoutPosition()));
        }
    }
}
