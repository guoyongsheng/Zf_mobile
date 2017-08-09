package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.FoodInfo;
import com.zfsoftmh.entity.ShopCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljq
 * on 2017/7/31.
 */

public class ShopCartDialogAdapter extends RecyclerView.Adapter<ShopCartDialogAdapter.MyViewHolder> {
    private Context context;
    private ShopCart shopCart;
    private List<FoodInfo> list;
    private Counter counter;

    public ShopCartDialogAdapter(Context context, ShopCart shopCart){
        this.context=context;
        this.shopCart=shopCart;
        list=new ArrayList<>();
        list.addAll(shopCart.getFoodInfoMap().keySet());

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shopcartdialog,parent,false));
    }





    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            final FoodInfo foodInfo=list.get(position);
        if(foodInfo!=null){
            holder.foodname.setText(foodInfo.getFoodName());
            holder.foodsum.setText(shopCart.getFoodInfoMap().get(foodInfo)+"");
            holder.foodprice.setText(foodInfo.getPrice()+"");
            holder.removefood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(shopCart.RemoveFood(foodInfo)){
                        list.clear();
                        list.addAll(shopCart.getFoodInfoMap().keySet());
                     //   c
                        notifyItemChanged(position);
                        if(counter!=null) {
                            counter.removeFood(v, position);
                        }
                    }

                }
            });
            holder.addfood.setOnClickListener(new View.OnClickListener() {
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

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setCounter(Counter counter){
        this.counter=counter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView foodname;
        TextView foodprice;
        TextView foodsum;
        ImageView addfood;
        ImageView removefood;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodname= (TextView) itemView.findViewById(R.id.choice_foodname);
            foodprice= (TextView) itemView.findViewById(R.id.choice_foodprice);
            foodsum= (TextView) itemView.findViewById(R.id.choice_sum);
            addfood= (ImageView) itemView.findViewById(R.id.choice_add);
            removefood= (ImageView) itemView.findViewById(R.id.choice_remove);
        }
    }
}
