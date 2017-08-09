package com.zfsoftmh.ui.modules.chatting.tribe.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.contact.IYWContact;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.add.InviteMemberActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/18.
 */
class TribeMemberAdapter extends RecyclerView.Adapter<TribeMemberAdapter.MemberHolder>{

    private Context mContext;
    private long mTribeId;
    TribeMemberAdapter(Context context,long tribeID){
        mContext = context;
        mTribeId = tribeID;
        dataList = new ArrayList<>();
    }

    private List<IYWContact> dataList;

    public void setData(List<IYWContact> data){
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tribe_members,parent,false);
        return new MemberHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberHolder holder, int position) {
        if (position == dataList.size()){
            holder.header.setImageResource(R.drawable.ic_add_box);
            holder.name.setVisibility(View.GONE);
        }else{
            holder.name.setText(dataList.get(position).getShowName());
            holder.name.setVisibility(View.VISIBLE);
            ImageLoaderHelper.loadDefConfCirCleImage(mContext,holder.header,
                    dataList.get(position).getAvatarPath(), R.drawable.aliwx_head_default);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    class MemberHolder extends RecyclerView.ViewHolder{
        ImageView header;
        TextView name;
        MemberHolder(View itemView) {
            super(itemView);
            header = (ImageView) itemView.findViewById(R.id.item_member_header);
            name = (TextView) itemView.findViewById(R.id.item_member_name);
            itemView.setOnClickListener(clickListener);
        }

        OnceClickListener clickListener = new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                int pos = getLayoutPosition();
                if (pos == dataList.size()){
                    ArrayList<String> dataIDs = new ArrayList<>();
                    for (IYWContact contact : dataList) {
                        dataIDs.add(contact.getUserId());
                    }
                    Intent intent = new Intent(mContext,InviteMemberActivity.class);
                    intent.putStringArrayListExtra("data_id",dataIDs);
                    intent.putExtra("targetTribe",mTribeId);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext,TribeMemberProfileActivity.class);
                    intent.putExtra("targetID",dataList.get(pos).getUserId());
                    intent.putExtra("targetNick",dataList.get(pos).getShowName());
                    mContext.startActivity(intent);
                }

            }
        };

    }

}
