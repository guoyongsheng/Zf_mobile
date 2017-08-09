package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.FoodInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li
 * on 2017/8/1.
 */

public class OrderSubmitAdapter extends  RecyclerView.Adapter<OrderSubmitAdapter.MyViewHolder>{

    private static int HEADER=0;
    private static int FOOT=1;
    private static  int BODY=2;

    private Context context;
    String total_price;
    String eatery_name;
    List<FoodInfo> list_foodInfo;


    public OrderSubmitAdapter(Context context,String total_price,String eatery_name){
        this.context=context;
        this.eatery_name=eatery_name;
        this.total_price=total_price;
        list_foodInfo=new ArrayList<>();
    }


    public void addData(List<FoodInfo> list){
     this.list_foodInfo=list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==HEADER){
            view= LayoutInflater.from(context).inflate(R.layout.item_ordersubmit_header,parent,false);
        }else if(viewType==FOOT){
            view=LayoutInflater.from(context).inflate(R.layout.item_ordersubmit_foot,parent,false);
        }else{
            view=LayoutInflater.from(context).inflate(R.layout.item_ordersubmit,parent,false);
        }
        return new MyViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int type=getItemViewType(position);
        if(type==HEADER){
            holder.tv_.setText(eatery_name);
        }else if(type==FOOT){
            holder.tv_.setText("小计 ￥"+total_price);
        }else{
            FoodInfo info=list_foodInfo.get(position-1);
            holder.tv_price.setText("￥"+info.getPrice());
            holder.tv_count.setText("x"+info.getCount());
            holder.tv_name.setText(info.getFoodName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return HEADER;
        }else if(position==getItemCount()-1){
            return FOOT;
        }else{
            return BODY;
        }
    }

    @Override
    public int getItemCount() {
        if(list_foodInfo.size()==0){
            return  list_foodInfo.size();
        }else {
            return list_foodInfo.size() + 2;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name; //名
        TextView tv_count;//数量
        TextView tv_price;//价格

        TextView tv_;//用于放头部和尾部

        public MyViewHolder(View itemView,int Type) {
            super(itemView);
            if(Type==HEADER){
                tv_= (TextView) itemView.findViewById(R.id.item_ordersubmit_header);
            }else if(Type==FOOT){
                tv_= (TextView) itemView.findViewById(R.id.item_ordersubimt_foot);
            }else{tv_name= (TextView) itemView.findViewById(R.id.order_submit_name);
                tv_count= (TextView) itemView.findViewById(R.id.order_submit_count);
                tv_price= (TextView) itemView.findViewById(R.id.order_submit_price);

            }
        }
    }
}
