package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.orderform.orderdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.FoodInfo;

import java.util.List;

/**
 * Created by li
 * on 2017/8/3.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter <OrderDetailAdapter.MyViewHolder>{

    private static int HEADER=0;
    private static int BODY=1;
    private static int FOOT;


    List<FoodInfo> foodInfoList;
    Context context;


    public OrderDetailAdapter(Context context, List<FoodInfo> foodInfoList){
        this.context=context;
        this.foodInfoList=foodInfoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==HEADER){
            view= LayoutInflater.from(context).inflate(R.layout.item_orderdetail,parent,false);
        }else{
            view= LayoutInflater.from(context).inflate(R.layout.item_orderdetail_content,parent,false);
        }

        return new MyViewHolder(view,viewType);
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return  HEADER;
        }else{
            return  BODY;
        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int type=getItemViewType(position);
        if(type==HEADER){
            holder.tv_name.setText("我是店铺名");
            holder.tv_count.setText("下单时间 222222");
        }else{
            holder.tv_name.setText(foodInfoList.get(position-1).getFoodName());
            holder.tv_count.setText("x"+foodInfoList.get(position-1).getCount()+"");
            holder.tv_price.setText("￥"+foodInfoList.get(position-1).getPrice()+"");
        }

    }

    @Override
    public int getItemCount() {
        return foodInfoList.size()+1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_photo;
        TextView tv_name;
        TextView tv_count;
        TextView tv_price;

        public MyViewHolder(View itemView,int type) {
            super(itemView);
            if(type==HEADER){
                iv_photo= (ImageView) itemView.findViewById(R.id.orderdetail_eateryimage);
                tv_name= (TextView) itemView.findViewById(R.id.orderdetail_eateryname);
                tv_count= (TextView) itemView.findViewById(R.id.orderdetail_top_time);
            }else{
                iv_photo= (ImageView) itemView.findViewById(R.id.orderdetail_foodpic);
                tv_name= (TextView) itemView.findViewById(R.id.orderdetail_foodname);
                tv_count= (TextView) itemView.findViewById(R.id.orderdetail_foodcount);
                tv_price= (TextView) itemView.findViewById(R.id.orderdetail_foodprice);

            }
        }
    }
}
