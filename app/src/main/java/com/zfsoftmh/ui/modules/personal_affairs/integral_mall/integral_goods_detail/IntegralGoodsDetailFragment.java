package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.common.home.home.HomeFragment;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/7/6
 * @Description: 积分商品详情页
 */

public class IntegralGoodsDetailFragment extends BaseFragment<IntegralGoodsDetailPresenter>
        implements IntegralGoodsDetailContract.View, AmountView.OnAmountChangeListener {

    private IntegralMallGoodsInfo goodsInfo;
    private String goodsName = null; // 商品名
    private String goodsIntegral = null; // 兑换所需积分
    private String goodsStock = null; // 商品库存
    private String goodsDescription = null; // 商品描述
    private String isActive = null; // 商品能否兑换状态（1.启用,0.停用）
    private List<String> images = new ArrayList<String>(); // 商品图集合

    private int goodsNumber = 0; // 兑换数量
    private String integralTotal; // 总积分

    private Banner integral_goods_detail_banner; // 轮播图控件
    private TextView integral_goods_detail_name; // 商品名
    private TextView integral_goods_detail_number; // 商品所需兑换积分
    private TextView stock_number; // 商品库存
    private TextView integral_goods_detail_describe_content; // 商品详情描述
    private Button integral_goods_exchange_bt; // 兑换按钮
    private AmountView adder; // 兑换数量加减器

    public static IntegralGoodsDetailFragment newInstance(IntegralMallGoodsInfo bean) {
        IntegralGoodsDetailFragment fragment = new IntegralGoodsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("IntegralMallFragment", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void createLoadingDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideUpLoadingDialog() {
        hideProgressDialog();
    }

    /**
     * 兑换商品
     */
    @Override
    public void exchangeGoods() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("goodsid", goodsInfo.goodsid);
        params.put("amount", String.valueOf(goodsNumber));
        presenter.giftExchange(params);
    }

    @Override
    public void loadFailure(String s) {
        showToastMsgLong(s);
    }

    /**
     * 成功兑换商品
     *
     * @param data 返回总积分
     */
    @Override
    public void exchangeGoodsSuccess(String data) {
        showToastMsgLong("礼品兑换成功！");
        SharedPreferenceUtils.write(context, "integral", "integral", data);
        integralTotal = data;
        skipPage();
    }

    @Override
    public void skipPage() {
        Intent intent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    protected void initVariables() {
        integralTotal = SharedPreferenceUtils.readString(context, "integral", "integral");
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        goodsInfo = bundle.getParcelable("IntegralMallFragment");
        if (goodsInfo != null) {
            if (goodsInfo.picPathList != null && goodsInfo.picPathList.size() > 0) {
                images = goodsInfo.picPathList;
            } else {
                images = null;
            }
            if (goodsInfo.goodsname != null)
                goodsName = goodsInfo.goodsname;

            if (goodsInfo.description != null)
                goodsDescription = goodsInfo.description;

            if (goodsInfo.isactive != null)
                isActive = goodsInfo.isactive;
            goodsIntegral = String.valueOf(goodsInfo.numbericvalue);
            goodsStock = String.valueOf(goodsInfo.storage);
        }

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_integral_goods_detail;
    }

    @Override
    protected void initViews(View view) {
        adder = (AmountView) view.findViewById(R.id.adder);
        integral_goods_detail_banner = (Banner) view.findViewById(R.id.integral_goods_detail_banner);
        integral_goods_detail_name = (TextView) view.findViewById(R.id.integral_goods_detail_name);
        integral_goods_detail_number = (TextView) view.findViewById(R.id.integral_goods_detail_number);
        stock_number = (TextView) view.findViewById(R.id.stock_number);
        integral_goods_detail_describe_content = (TextView) view.findViewById(R.id.integral_goods_detail_describe_content);
        integral_goods_exchange_bt = (Button) view.findViewById(R.id.integral_goods_exchange_bt);

//        initBanner();
        initData();
    }

    @Override
    protected void initListener() {
        adder.setOnAmountChangeListener(this);
        integral_goods_exchange_bt.setOnClickListener(onceClickListener);
    }

    private OnceClickListener onceClickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            if (v == null) {
                return;
            }
            int key = v.getId();
            switch (key) {
                /**
                 * 兑换按钮
                 */
                case R.id.integral_goods_exchange_bt:
                    if (isActive.equals("1")) {
                        // 兑换商品数量
                        if (goodsNumber < 1) {
                            showToastMsgLong("礼品数量不能为0，请选择礼品数量！");
                            return;
                        }
                        // 每件商品所需兑换积分
                        int goodsIntegral1 = Integer.valueOf(goodsIntegral);

                        // 总积分数
                        int integralTotal1 = Integer.valueOf(integralTotal);
                        if (goodsIntegral1 * goodsNumber < integralTotal1) {
                            exchangeGoods();
                        } else {
                            showToastMsgLong("积分不足，无法兑换礼品！");
                        }
                    } else {
                        showToastMsgLong("已兑换完！");
                    }

                    break;
            }
        }
    };

    // 加载轮播图
    private void initBanner() {

        int bannerHeight = ScreenUtils.getScreenWidth(context) * 2 / 5;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, bannerHeight);
        integral_goods_detail_banner.setLayoutParams(params);
        integral_goods_detail_banner.setIndicatorGravity(BannerConfig.RIGHT);
        integral_goods_detail_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        integral_goods_detail_banner.setImages(images);
        // 轮播图无标题属性
        integral_goods_detail_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        integral_goods_detail_banner.setImageLoader(new GlideImageLoader());
        integral_goods_detail_banner.start();
    }

    @Override
    public void onAmountChange(View view, int amount) {
        goodsNumber = amount;
    }

    //图片加载器
    private static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (path != null && path instanceof String) {
                ImageLoaderHelper.loadImage(context, imageView, (String) path);
            }
        }
    }

    private void initData() {
        if (isActive != null) {
            if (isActive.equals("1")) { // 可兑换状态
                integral_goods_exchange_bt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                integral_goods_exchange_bt.setText("兑换");
            } else {
                integral_goods_exchange_bt.setBackgroundColor(getResources().getColor(R.color.color_bg_gray2));
                integral_goods_exchange_bt.setText("已兑完");
            }
        }

        if (images != null) {
            initBanner();
        }
        if (goodsName != null)
            integral_goods_detail_name.setText(goodsName);
        if (goodsIntegral != null)
            integral_goods_detail_number.setText(goodsIntegral);
        if (goodsStock != null) {
            stock_number.setText(goodsStock + "件");
            adder.setGoods_storage(Integer.valueOf(goodsStock));
        }
        if (goodsDescription != null)
            integral_goods_detail_describe_content.setText(goodsDescription);

    }
}
