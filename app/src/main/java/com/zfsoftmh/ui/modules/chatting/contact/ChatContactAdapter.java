package com.zfsoftmh.ui.modules.chatting.contact;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.ui.widget.OnceClickListener;
import com.zfsoftmh.ui.widget.textdrawable.ColorGenerator;
import com.zfsoftmh.ui.widget.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/2.
 * 好友页面适配器
 */
class ChatContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements
        StickyRecyclerHeadersAdapter<ChatContactAdapter.HeadViewHolder> {

    private final int COMMON_ITEM_SIZE = 5;
    private List<ChatContact> dataList;
    private ColorGenerator generator;
    private Context mContext;

    ChatContactAdapter(Context context){
        mContext = context;
        dataList = new ArrayList<>();
        generator = ColorGenerator.MATERIAL;
    }

    void setDataList(List<ChatContact> data){
        this.dataList.clear();
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_friends, parent, false);
        return new ItemViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        switch (position){
            case 0:
                TextDrawable draw1 = TextDrawable.builder()
                        .buildRound("S",generator.getColor("系统"));
                viewHolder.iv_icon.setImageDrawable(draw1);
                viewHolder.tv_name.setText("系统消息");
                break;
            case 1:
                TextDrawable draw2 = TextDrawable.builder()
                        .buildRound("T",generator.getColor("群组"));
                viewHolder.iv_icon.setImageDrawable(draw2);
                viewHolder.tv_name.setText("群组");
                break;
            case 2:
                TextDrawable draw3 = TextDrawable.builder()
                        .buildRound("O",generator.getColor("办公"));
                viewHolder.iv_icon.setImageDrawable(draw3);
                viewHolder.tv_name.setText("办公通讯录");
                break;
            case 3:
                TextDrawable draw4 = TextDrawable.builder()
                        .buildRound("P",generator.getColor("手机"));
                viewHolder.iv_icon.setImageDrawable(draw4);
                viewHolder.tv_name.setText("手机通讯录");
                break;
            case 4:
                TextDrawable draw5 = TextDrawable.builder()
                        .buildRound("L",generator.getColor("生活"));
                viewHolder.iv_icon.setImageDrawable(draw5);
                viewHolder.tv_name.setText("生活号");
                break;
            default:
                ChatContact mFriend = dataList.get(position);
                viewHolder.tv_name.setText(mFriend.getShowName());
                String mFriendAvatar = mFriend.getAvatarPath();
                if(TextUtils.isEmpty(mFriendAvatar)){
                    TextDrawable draw= TextDrawable.builder().beginConfig()
                            .fontSize(30).useFont(Typeface.DEFAULT).endConfig()
                            .buildRound(mFriend.getShowName().substring(0,1),generator.getColor(mFriend.getShowName()));
                    viewHolder.iv_icon.setImageDrawable(draw);
                }else{
                    ImageLoaderHelper.loadDefConfCirCleImage(mContext, viewHolder.iv_icon, mFriendAvatar, 0);
                }
                break;
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
        if (getHeaderId(position) == 0) {
            assert holder != null;
            holder.tv_value.setVisibility(View.GONE);
            return;
        }
        String str = dataList.get(position).getFirstChar().toUpperCase();
        holder.tv_value.setText(isAtoZ(str)?str:"#");
    }

    boolean isAtoZ(String str){
        return Character.isLetter(str.charAt(0));
    }

    @Override
    public int getItemCount() {
        return dataList.size() == 0 ? COMMON_ITEM_SIZE : dataList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        ImageView iv_icon; //头像
        TextView tv_name; //名称

        RecyclerItemClickListener listener;

        OnceClickListener onceClickListener = new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                if (listener != null) {
                    int pos = getLayoutPosition();
                    if (pos < COMMON_ITEM_SIZE)
                        listener.onRecyclerItemClick(pos , null);
                    else
                        listener.onRecyclerItemClick(pos, dataList.get(pos));
                }
            }
        };

        ItemViewHolder(View itemView,RecyclerItemClickListener l) {
            super(itemView);
            this.listener = l;
            iv_icon = (ImageView) itemView.findViewById(R.id.item_chat_contacts_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_chat_contacts_name);
            itemView.setOnClickListener(onceClickListener);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                int pos = getLayoutPosition();
                if (pos < COMMON_ITEM_SIZE)
                    listener.onRecyclerItemLongClick(null);
                else
                    listener.onRecyclerItemLongClick(dataList.get(pos));
            }
            return true;
        }
    }

    //头部的view
    class HeadViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_value;
        HeadViewHolder(View itemView) {
            super(itemView);
            tv_value = (TextView) itemView.findViewById(R.id.item_phone_contacts_head_value);
        }
    }

    void setItemClickListener(RecyclerItemClickListener l){
        this.clickListener = l;
    }

    private RecyclerItemClickListener clickListener;

    interface RecyclerItemClickListener{
        void onRecyclerItemClick(int pos, ChatContact contact);
        void onRecyclerItemLongClick(ChatContact contact);
    }

}
