package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.ExchangeRecordItemInfo;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/14
 * @Description:
 */

public class ExchangeRecordDetailFragment extends BaseFragment<ExchangeRecordDetailPresenter>
        implements ExchangeRecordDetailContract.View {

    private Banner exchange_record_detail_banner; // 图片轮播图控件
    private TextView exchange_detail_goods_name; // 兑换的商品名
    private TextView exchange_detail_integral_number; // 兑换所需的积分数
    private TextView exchange_detail_number; // 兑换数量
    private TextView exchange_state; // 兑换状态
    private TextView exchange_order_number; // 兑换订单编号
    private TextView exchange_detail_goods_describe; // 兑换商品介绍
    private TextView exchange_create_time; // 兑换时间（记录创建时间）
    private TextView exchange_receive_time; // 领取时间
    private ExchangeRecordItemInfo info = null; // 兑换记录信息
    private IntegralMallGoodsInfo mallGoodsInfo = null; // 商品信息
    private String goodsId = null, orderID = null, createTimeStr = null; // 商品ID，订单编号,记录生成时间,
    private String flag = null; // 领取标志（0.未领取，1.领取）
    private String exchangeTimeStr = null; // 领取时间
    private int amount = 0; // 兑换商品数量
    private TextView detail_no_data_hint; //  暂无数据视图
    private NestedScrollView content_scroll; // 内容视图


    public static ExchangeRecordDetailFragment newInstance(ExchangeRecordItemInfo bean) {
        ExchangeRecordDetailFragment fragment = new ExchangeRecordDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ExchangeRecordFragment", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        info = bundle.getParcelable("ExchangeRecordFragment");
        if (info != null) {
            goodsId = info.goodsid;
            orderID = info.id;
            createTimeStr = info.createtimeStr;
            flag = info.flag;
            amount = info.amount;
            if (flag != null) {
                if (flag.equals("1")) {
                    exchangeTimeStr = info.exhangetimeStr;
                } else {
                    exchangeTimeStr = null;
                }
            }

        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_exchange_record_detail;
    }

    @Override
    protected void initViews(View view) {
        detail_no_data_hint = (TextView) view.findViewById(R.id.detail_no_data_hint);
        content_scroll = (NestedScrollView) view.findViewById(R.id.content_scroll);
        exchange_record_detail_banner = (Banner) view.findViewById(R.id.exchange_record_detail_banner);
        exchange_detail_goods_name = (TextView) view.findViewById(R.id.exchange_detail_goods_name);
        exchange_detail_integral_number = (TextView) view.findViewById(R.id.exchange_detail_integral_number);
        exchange_detail_number = (TextView) view.findViewById(R.id.exchange_detail_number);
        exchange_state = (TextView) view.findViewById(R.id.detail_exchange_state);
        exchange_order_number = (TextView) view.findViewById(R.id.exchange_order_number);
        exchange_detail_goods_describe = (TextView) view.findViewById(R.id.exchange_detail_goods_describe);
        exchange_create_time = (TextView) view.findViewById(R.id.exchange_create_time);
        exchange_receive_time = (TextView) view.findViewById(R.id.exchange_receive_time);

        loadDetailInfo();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void createLoadingDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideUpLoadingDialog() {
        hideProgressDialog();
    }

    @Override
    public void loadFailure(String s) {
        showToastMsgLong(s);
        detail_no_data_hint.setVisibility(View.VISIBLE);
        content_scroll.setVisibility(View.GONE);
    }

    @Override
    public void loadDetailInfo() {
        if (goodsId != null) {
            presenter.getRecordDetail(goodsId);
        }
    }

    @Override
    public void loadDetailInfoSuccess(IntegralMallGoodsInfo info) {
        detail_no_data_hint.setVisibility(View.GONE);
        content_scroll.setVisibility(View.VISIBLE);
        mallGoodsInfo = info;
        setData();
    }

    @Override
    public void setData() {
        if (info != null) {
            if (mallGoodsInfo.picPathList != null && mallGoodsInfo.picPathList.size() > 0) {
                initBanner(mallGoodsInfo.picPathList);
            }
            if (mallGoodsInfo.goodsname != null)
                exchange_detail_goods_name.setText(mallGoodsInfo.goodsname);

            exchange_detail_integral_number.setText(String.valueOf(mallGoodsInfo.numbericvalue));
            exchange_detail_number.setText(String.valueOf(amount));
            if (flag != null) {
                if (flag.equals("0")) {
                    exchange_state.setText("未领取");
                    exchange_receive_time.setVisibility(View.GONE);
                } else if (flag.equals("1")) {
                    exchange_state.setText("已领取");
                    exchange_receive_time.setVisibility(View.VISIBLE);
                }
            }

            if (orderID != null)
                exchange_order_number.setText(orderID);
            if (createTimeStr != null)
                exchange_create_time.setText("兑换时间：" + createTimeStr);
            if (exchangeTimeStr != null)
                exchange_receive_time.setText("领取时间：" + exchangeTimeStr);
            if (mallGoodsInfo.description != null)
                exchange_detail_goods_describe.setText(mallGoodsInfo.description);
        }
    }

    // 加载轮播图
    private void initBanner(List<String> images) {

        int bannerHeight = ScreenUtils.getScreenWidth(context) * 2 / 5;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, bannerHeight);
        exchange_record_detail_banner.setLayoutParams(params);
        exchange_record_detail_banner.setIndicatorGravity(BannerConfig.RIGHT);
        exchange_record_detail_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        exchange_record_detail_banner.setImages(images);
        // 轮播图无标题属性
        exchange_record_detail_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        exchange_record_detail_banner.setImageLoader(new GlideImageLoader());
        exchange_record_detail_banner.start();
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
}
