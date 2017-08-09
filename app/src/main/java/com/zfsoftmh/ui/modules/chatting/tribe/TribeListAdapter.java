package com.zfsoftmh.ui.modules.chatting.tribe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.ui.widget.OnceClickListener;
import com.zfsoftmh.ui.widget.ZRecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/15.
 * <p>群组列表适配器</p>
 */
class TribeListAdapter extends RecyclerView.Adapter<TribeListAdapter.TribeHolder> {

    private ArrayList<YWTribe> dataList;
    private Context mContext;

    TribeListAdapter(Context context){
        mContext = context;
        dataList = new ArrayList<>();
    }

    void setTribeData(ArrayList<YWTribe> data){
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public TribeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tribe_list,parent,false);
        return new TribeHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(TribeHolder holder, int position) {
        YWTribe tribe = dataList.get(position);
        String name = tribe.getTribeName();
        String iconUrl = tribe.getTribeIcon();
        holder.titleView.setText(name);
        ImageLoaderHelper.loadDefConfCirCleImage(mContext, holder.headView,
                iconUrl, R.drawable.aliwx_tribe_head_default);
    }

    private ZRecyclerItemClickListener<YWTribe> mItemClickListener;

    void setItemClickListener(ZRecyclerItemClickListener<YWTribe> l){
        mItemClickListener = l;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class TribeHolder extends RecyclerView.ViewHolder{

        ZRecyclerItemClickListener<YWTribe> mListener;
        TextView titleView;
        ImageView headView;

        TribeHolder(View itemView, ZRecyclerItemClickListener<YWTribe> l) {
            super(itemView);
            mListener = l;
            titleView = (TextView) itemView.findViewById(R.id.item_tribe_name);
            headView = (ImageView) itemView.findViewById(R.id.item_tribe_head);
            itemView.setOnClickListener(onceClickListener);
        }

        OnceClickListener onceClickListener = new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                if (mListener != null){
                    int pos = getLayoutPosition();
                    mListener.onRecyclerItemClick(pos, dataList.get(pos));
                }
            }
        };

    }


}
