package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.EateryTitleInfo;
import com.zfsoftmh.entity.FoodCataListInfo;
import com.zfsoftmh.entity.FoodInfo;
import com.zfsoftmh.entity.ShopCart;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit.OrderSubmitActivity;
import com.zfsoftmh.ui.widget.divider.EateryDatailTitleDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ljq
 * on 2017/7/25.
 */

public class EateryDetailFragment extends BaseFragment<EateryDetailPresenter> implements EateryDetailContract.View, LeftMenuAdapter.OnLeftTitleClickListener, Counter, View.OnClickListener, ShopCartDialog.ShopDailogImp {

    RecyclerView leftMenu;
    RecyclerView rightMenu;
    LeftMenuAdapter leftMenuAdapter;
    RightMenuAdapter rightMenuAdapter;
    LinearLayoutManager rightManager;
    private boolean isMove;
    int rightChoicepos=0;
    String eateryId;
    private EateryInfo eateryinfo;
    ImageView shoppingCartView;
    ShopCartDialog dialog;
    ShopCart shopCart;
    RelativeLayout ll_eatery;
    ImageView eatery_detail_image;
    TextView eatery_name;
    TextView total_price;
    TextView pay_the_bill;




    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public  static  EateryDetailFragment newInstance(EateryInfo info){
        EateryDetailFragment fragment=new EateryDetailFragment();
        if(info!=null){
            Bundle bundle =new Bundle();
            bundle.putParcelable("eatery",info);
            fragment.setArguments(bundle);
        }
       return  fragment;

    }


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_eatery_detail;
    }

    @Override
    protected void initViews(View view) {
        ll_eatery= (RelativeLayout) view.findViewById(R.id.rl_eatery_detail);
        eatery_detail_image= (ImageView) view.findViewById(R.id.eatery_detail_image);
        total_price= (TextView) view.findViewById(R.id.shopcart_count);
        eatery_name= (TextView) view.findViewById(R.id.eatery_detail_tv_name);
        pay_the_bill= (TextView) view.findViewById(R.id.pay_the_bill);

        shoppingCartView= (ImageView) view.findViewById(R.id.shop_cart);
        leftMenu= (RecyclerView) view.findViewById(R.id.eatery_detail_recycler_left);
        rightMenu= (RecyclerView) view.findViewById(R.id.eatery_detail_recycler_right);
        leftMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        rightManager=new LinearLayoutManager(getContext());
        rightMenu.setLayoutManager(rightManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        leftMenu.addItemDecoration(itemDecoration);
        rightMenu.addItemDecoration(itemDecoration);
        Bundle bundle=getArguments();
       if(bundle!=null){
            eateryinfo=bundle.getParcelable("eatery");
            if(eateryinfo!=null){
                ImageLoaderHelper.loadImage(getContext(),eatery_detail_image,eateryinfo.getPicPath());
                eatery_name.setText(eateryinfo.getCanteenName());
                eateryId=eateryinfo.getCanteenId();
                getFoodInfo(eateryId);
            }
        }

        shopCart=new ShopCart();


      // getDate();

    }

    List<EateryTitleInfo> leftlist;
    private void getDate(){
        leftlist=new ArrayList<>();
        for (int i=0;i<8;i++){
            EateryTitleInfo info=new EateryTitleInfo();
            info.setPos(i*10);
            info.setTitlename("测试数据第"+i+"号");
            leftlist.add(info);
        }

        List<FoodInfo> foodlist=new ArrayList<>();
        for(int j=0;j<88;j++){
            FoodInfo foodInfo=new FoodInfo();
            foodInfo.setFoodName("我的名字"+j);
            foodInfo.setStorage("25");
            foodInfo.setPrice(String.valueOf(j*2));
            int c=j%10;
            foodInfo.setType_title("类别"+c);
            foodlist.add(foodInfo);
        }



        leftMenuAdapter=new LeftMenuAdapter(getContext(),this);
        leftMenuAdapter.AddLeftTitle(leftlist);
        leftMenu.setAdapter(leftMenuAdapter);

        rightMenuAdapter=new RightMenuAdapter(getContext(),shopCart);
        rightMenuAdapter.setCounter(this);
        rightMenuAdapter.addRightData(foodlist);
        rightMenu.setAdapter(rightMenuAdapter);
        rightMenu.addOnScrollListener(new RecyclerViewListener());


        EateryDatailTitleDecoration decoration=new EateryDatailTitleDecoration(getContext(),foodlist);
        rightMenu.addItemDecoration(decoration);



    }


    private void getFoodInfo(String eateryId){
            presenter.loadData(eateryId);
    }


    @Override
    protected void initListener() {
        pay_the_bill.setOnClickListener(this);
        shoppingCartView.setOnClickListener(this);
    }

    @Override
    public void setOnLeftTitleClick(View view, int pos) {
        Toast.makeText(getContext(),"点击了"+pos+"标题",Toast.LENGTH_SHORT).show();
        if(view!=null){
         RightScroolTo(pos * 10);
        }
    }




    private void RightScroolTo(int pos){
        rightChoicepos=pos;
        rightMenu.stopScroll();
        int firstItem = rightManager.findFirstVisibleItemPosition();
        int lastItem = rightManager.findLastVisibleItemPosition();
        Log.d("first--->", String.valueOf(firstItem));
        Log.d("last--->", String.valueOf(lastItem));
        if (pos <= firstItem) {
            rightMenu.smoothScrollToPosition(pos);
        } else if (pos <= lastItem) {
            Log.d("pos---->", String.valueOf(pos)+"VS"+firstItem);
            int top = rightMenu.getChildAt(pos - firstItem).getTop();
            Log.d("top---->", String.valueOf(top));
            rightMenu.smoothScrollBy(0, top);
        } else {
            rightMenu.smoothScrollToPosition(pos);
            isMove = true;
        }


    }
//获取食物列表成功

    List<FoodInfo> all_food_list;

    @Override
    public void getFoodSucess(List<FoodCataListInfo> list) {
        leftlist=new ArrayList<>();//左边的数据
        all_food_list=new ArrayList<>();
        int size=list.size();
        for(int i=0;i<list.size();i++){
            EateryTitleInfo left_info=new EateryTitleInfo();
            String type_title=list.get(i).getFoodcataName();//获取类别名
            left_info.setTitlename(type_title);
            left_info.setPos(i);
            List<FoodInfo> foodInfo=list.get(i).getFoodlist();
            int sizecount=foodInfo.size();
            for(int j=0;j<sizecount;j++){
                foodInfo.get(j).setType_title(type_title);
            }
            leftlist.add(left_info);
            all_food_list.addAll(foodInfo);
        }
         //这要设置右边的适配器和itemdecoration
        rightMenuAdapter =new RightMenuAdapter(getContext(),shopCart);


        leftMenuAdapter=new LeftMenuAdapter(getContext(),this);
        leftMenuAdapter.AddLeftTitle(leftlist);
        leftMenu.setAdapter(leftMenuAdapter);

        rightMenuAdapter=new RightMenuAdapter(getContext(),shopCart);
        rightMenuAdapter.setCounter(this);
        rightMenuAdapter.addRightData(all_food_list);
        rightMenu.setAdapter(rightMenuAdapter);
        rightMenu.addOnScrollListener(new RecyclerViewListener());


        EateryDatailTitleDecoration decoration=new EateryDatailTitleDecoration(getContext(),all_food_list);
        rightMenu.addItemDecoration(decoration);



    }

    @Override
    public void getFoodErr(String err) {

    }
//添加食物的动画
    @Override
    public void addFood(View view, int pos) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCartView.getLocationInWindow(cartLocation);
        rightMenu.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1]-recycleLocation[1]/2;
        endP.x = cartLocation[0];
        endP.y = cartLocation[1]-recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(getContext());
        ll_eatery.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.mipmap.ic_add_circle_blue_700_36dp);
        fakeAddImageView.getLayoutParams().width = 80;
        fakeAddImageView.getLayoutParams().height = 80;
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                ll_eatery.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCartView,"scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCartView,"scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(800);
        animatorSet.start();

        showTotalPrice();
    }

    private void showTotalPrice() {
        if(shopCart!=null){
            total_price.setText("￥"+shopCart.getTotalPrice()+"");
        }

    }

    @Override
    public void removeFood(View v, int pos) {
        showTotalPrice();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.shop_cart){
            showDialog();
        }else if(v.getId()==R.id.pay_the_bill){
            Intent intent =new Intent(getActivity(), OrderSubmitActivity.class);
            List<FoodInfo> foodList= shopCart.getFoodList();
            intent.putExtra("order", (Serializable) foodList);
            intent.putExtra("eateryinfo",eateryinfo);
            intent.putExtra("totalprice",shopCart.getTotalPrice()+"");
            startActivity(intent);
        }
    }

    private void showDialog(){
        ShopCartDialog dialog=new ShopCartDialog(getContext(),R.style.cartdialog, shopCart);
        Window window = dialog.getWindow();
        dialog.setShopDailogImp(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.dimAmount =0.5f;
        window.setAttributes(params);

    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
        rightMenuAdapter.notifyDataSetChanged();
    }


    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (isMove && newState == RecyclerView.SCROLL_STATE_IDLE) {
                isMove = false;
                int n = rightChoicepos - rightManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < rightMenu.getChildCount()) {
                    int top = rightMenu.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    rightMenu.smoothScrollBy(0, top);


                    FoodInfo foodInfo=all_food_list.get(n);
                    String foodtitle=foodInfo.getType_title();
                    for(int i=0;i<leftlist.size();i++){
                        if(foodtitle==leftlist.get(i).getTitlename()){
                            leftMenuAdapter.setSelectNum(i);
                            break;
                        }
                    }


                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

}
