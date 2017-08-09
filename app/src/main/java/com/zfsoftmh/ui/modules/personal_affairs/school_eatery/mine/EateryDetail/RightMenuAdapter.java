package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.FoodInfo;
import com.zfsoftmh.entity.ShopCart;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ljq
 * on 2017/7/26.
 */

 class RightMenuAdapter extends RecyclerView.Adapter<RightMenuAdapter.RightViewHolder> {
    private Context context;
    private Counter counter;//计数器 页面和适配器
    List<FoodInfo> info;
    ShopCart shopCart;


     RightMenuAdapter(Context context,ShopCart shopCart) {
        this.context = context;
         info=new ArrayList<>();
         this.shopCart=shopCart;
    }



    public void addRightData(List<FoodInfo> info){
        this.info=info;

    }
    @Override
    public RightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RightViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_eatery_food, parent,false));
    }

    @Override
    public void onBindViewHolder(RightViewHolder holder, final int position) {
       // holder.tv_count.setText("555");
        holder.tv_price.setText(context.getResources().getString(R.string.str_price,info.get(position).getPrice()+""));
        holder.tv_dish_name.setText(info.get(position).getFoodName());
        ImageLoaderHelper.loadImage(context,holder.iv_photo,info.get(position).getPicPath());




        final FoodInfo foodInfo=info.get(position);
        int count=0;

        if(shopCart.getFoodInfoMap().containsKey(foodInfo)){
            count=shopCart.getFoodInfoMap().get(foodInfo);
        }

        if(count<=0){
            holder.iv_remove_dish.setVisibility(View.GONE);
            holder.tv_count.setVisibility(View.GONE);

        }else {
            holder.iv_remove_dish.setVisibility(View.VISIBLE);
            holder.tv_count.setVisibility(View.VISIBLE);
            holder.tv_count.setText(""+count);
        }
        holder.iv_add_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopCart.AddFood(foodInfo)){
                    notifyItemChanged(position);
                    if(counter!=null){
                        counter.addFood(v,position);
                    }
                }

            }
        });
        holder.iv_remove_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopCart.RemoveFood(foodInfo)){
                    notifyItemChanged(position);
                    if(counter!=null){
                        counter.removeFood(v,position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photo;
        TextView tv_count;//数量
        TextView tv_dish_name;//名字
        TextView tv_price;//价格
        ImageView iv_add_dish;
        ImageView iv_remove_dish;

        RightViewHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.right_dish_photo);
            tv_count= (TextView) itemView.findViewById(R.id.right_dish_account);
            tv_dish_name= (TextView) itemView.findViewById(R.id.right_dish_name);
            tv_price= (TextView) itemView.findViewById(R.id.right_dish_price);
            iv_add_dish= (ImageView) itemView.findViewById(R.id.right_dish_add);
            iv_remove_dish= (ImageView) itemView.findViewById(R.id.right_dish_remove);
        }
    }



    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }
}
