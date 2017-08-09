package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.ShopCart;

/**
 * Created by ljq
 * on 2017/7/28.
 */

public class ShopCartDialog extends Dialog implements Counter, View.OnClickListener {

    private ShopCart shopCart;
    private LinearLayout ll_top;
    private RecyclerView recyclerView;
    ShopDailogImp shopDailogImp;
    private TextView tv_clear;
    private TextView total_price;


    public ShopCartDialog(@NonNull Context context, @StyleRes int themeResId, ShopCart shopCart) {
        super(context, themeResId);
        this.shopCart=shopCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopcartdailog);
        ll_top= (LinearLayout) findViewById(R.id.ll_shopcartdailog_top);
        tv_clear= (TextView) findViewById(R.id.tv_clear);
        total_price= (TextView) findViewById(R.id.shopcart_count);
        tv_clear.setOnClickListener(this);
        recyclerView= (RecyclerView) findViewById(R.id.rv_shopcartdailog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ShopCartDialogAdapter adapter=new ShopCartDialogAdapter(getContext(),shopCart);
            adapter.setCounter(this);

        recyclerView.setAdapter(adapter);
        showData();
    }




    @Override
    public void show() {
        super.show();
        animationShow(1000);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animationHide(1000);
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(ll_top, "translationY",1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(ll_top, "translationY",0,1000).setDuration(mDuration)
        );
        animatorSet.start();

       if(shopDailogImp!=null){
           shopDailogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ShopCartDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void addFood(View v, int pos) {

        showData();
    }

    @Override
    public void removeFood(View v, int pos) {
             if(shopCart.getAccount()==0){
                 this.dismiss();
             }
        showData();
    }

    private void showData(){

        if(shopCart!=null ){
             total_price.setText("￥"+shopCart.getTotalPrice());
        }else{

        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_clear){
            shopCart.clear();
            if(shopDailogImp!=null){
                shopDailogImp.dialogDismiss();
            }
            this.dismiss();
        }
    }

    interface  ShopDailogImp{
        public void dialogDismiss();
    }

    public ShopDailogImp getShopDailogImp() {
        return shopDailogImp;
    }

    public void setShopDailogImp(ShopDailogImp shopDailogImp) {
        this.shopDailogImp = shopDailogImp;
    }
}
