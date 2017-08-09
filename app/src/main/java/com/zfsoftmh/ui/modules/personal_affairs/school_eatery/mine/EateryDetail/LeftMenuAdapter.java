package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.EateryTitleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljq
 * on 2017/7/26.
 */

public class LeftMenuAdapter extends RecyclerView.Adapter<LeftMenuAdapter.MyViewHolder> {

    private Context context;
    private List<EateryTitleInfo> titleList;
    OnLeftTitleClickListener listener;
    int selectNum;


    public LeftMenuAdapter(Context context, OnLeftTitleClickListener listener) {
        this.context = context;
        this.listener = listener;
        titleList = new ArrayList<>();
        selectNum=-1;
    }


    public void AddLeftTitle(List<EateryTitleInfo> data) {
        titleList = data;
        if(data!=null)
            selectNum=0;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.eatery_detail_title_decoration,null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(titleList.get(position).getTitlename());
        if(selectNum==position){
            holder.ll_eatery_title.setSelected(true);
        }else{
            holder.ll_eatery_title.setSelected(false);
        }


    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv;
        LinearLayout ll_eatery_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            ll_eatery_title= (LinearLayout) itemView.findViewById(R.id.ll_eatery_title);
            tv = (TextView) itemView.findViewById(R.id.title_decoration);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.setOnLeftTitleClick(v, getLayoutPosition());
            }
        }
    }

    interface OnLeftTitleClickListener {
        void setOnLeftTitleClick(View view, int pos);
    }


    public void setSelectNum(int pos){
        selectNum=pos;
        listener.setOnLeftTitleClick(null,pos);
        notifyDataSetChanged();
    }
}
