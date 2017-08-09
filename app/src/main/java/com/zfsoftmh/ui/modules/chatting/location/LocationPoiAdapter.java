package com.zfsoftmh.ui.modules.chatting.location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.zfsoftmh.R;

import java.util.ArrayList;

/**
 * Created by ｌｊｑ
 * on 2017/6/2.
 */

public class LocationPoiAdapter extends RecyclerView.Adapter<LocationPoiAdapter.MyHolder>{

    private ArrayList<MapItem> poiList;
    private MyItemClickListener mylistener;

    public LocationPoiAdapter(ArrayList<PoiItem> List){
        poiList=new ArrayList<>();
        for (PoiItem item:List) {
            poiList.add(new MapItem(item));
        }
    }

    public interface  MyItemClickListener{
        void setOnMyItemClickListener(View v, int position);
    }


    public void SetOnItemClickListener(MyItemClickListener listener){
        mylistener=listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.loc_poi_item,null);
        MyHolder holder=new MyHolder(view,mylistener);
        return holder;
    }



    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv.setText(poiList.get(position).getmPoiItem().getTitle());
        holder.tv_info.setText(poiList.get(position).getmPoiItem().getSnippet());
        holder.iv.setVisibility(poiList.get(position).isSelected()?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return poiList.size();
    }


    private int oldPos = -1;
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rl;
        TextView tv;
        TextView tv_info;
        ImageView iv;
        MyItemClickListener mListener =null;
        public MyHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            mListener=listener;
            rl= (RelativeLayout) itemView.findViewById(R.id.rl_recycler_poi);
            tv= (TextView) itemView.findViewById(R.id.tv_item_poi);
            tv_info= (TextView) itemView.findViewById(R.id.tv_item_poi_info);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener!=null){
                if (oldPos != -1){
                    poiList.get(oldPos).setSelected(false);
                }
                poiList.get(getLayoutPosition()).setSelected(true);
                oldPos = getLayoutPosition();
                notifyDataSetChanged();
                mListener.setOnMyItemClickListener(v,getLayoutPosition());
            }
        }
    }







}
