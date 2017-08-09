package com.zfsoftmh.ui.modules.personal_affairs.one_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.OneCardInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.one_card.card_recharge.CardRechargeActivity;
import com.zfsoftmh.ui.modules.personal_affairs.one_card.consumer_details.ConsumerDetailsActivity;
import com.zfsoftmh.ui.modules.personal_affairs.one_card.recharge_details.RechargeDetailsActivity;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: ui
 */

public class OneCardFragment extends BaseFragment<OneCardPresenter> implements OneCardContract.View,
        OneCardAdapter.OnItemClickListener {

    private static final String TAG = "OneCardFragment";
    private TextView tv_balance; //账户余额
    private AVLoadingIndicatorView loadingIndicatorView; //loading_view
    private String oneCardId; //卡号
    private boolean isLoading = true; //是否正在加载

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {

        tv_balance = (TextView) ((OneCardActivity) (context)).findViewById(R.id.one_card_end_money);
        loadingIndicatorView = (AVLoadingIndicatorView) ((OneCardActivity) (context)).findViewById(R.id.one_card_loading);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        //adapter
        OneCardAdapter adapter = new OneCardAdapter(context);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        getAccountBalance();
    }

    @Override
    protected void initListener() {

    }

    public static OneCardFragment newInstance() {
        return new OneCardFragment();
    }

    @Override
    public void onItemClick(int position) {

        switch (position) {
        /*
         *  卡片充值
         */
        case 0:
            startActivity(CardRechargeActivity.class);
            break;

        /*
         * 消费明细
         */
        case 1:
            if (isLoading) {
                showToastMsgShort(getResources().getString(R.string.loading));
                return;
            }
            Intent intentConsumerDetails = new Intent(context, ConsumerDetailsActivity.class);
            Bundle bundleConsumerDetails = new Bundle();
            bundleConsumerDetails.putString("id", oneCardId);
            intentConsumerDetails.putExtras(bundleConsumerDetails);
            openActivity(intentConsumerDetails);
            break;

        /*
         * 充值明细
         */
        case 2:
            if (isLoading) {
                showToastMsgShort(getResources().getString(R.string.loading));
                return;
            }
            Intent intentRechargeDetails = new Intent(context, RechargeDetailsActivity.class);
            Bundle bundleRechargeDetails = new Bundle();
            bundleRechargeDetails.putString("id", oneCardId);
            intentRechargeDetails.putExtras(bundleRechargeDetails);
            openActivity(intentRechargeDetails);
            break;

        /*
         * 卡片挂失
         */
        case 3:
            break;


        default:
            break;
        }
    }

    @Override
    public void getAccountBalance() {
        isLoading = true;
        presenter.getAccountBalance();
    }

    @Override
    public void startLoading() {
        tv_balance.setVisibility(View.GONE);
        loadingIndicatorView.setVisibility(View.VISIBLE);
        loadingIndicatorView.show();
    }

    @Override
    public void stopLoading() {
        loadingIndicatorView.hide();
        loadingIndicatorView.setVisibility(View.GONE);
        tv_balance.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadSuccess(OneCardInfo info) {
        if (info == null) {
            LoggerHelper.e(TAG, "loadSuccess info = " + null);
            return;
        }
        isLoading = false;
        oneCardId = info.getCardNumber();
        tv_balance.setText(info.getCardBlance());
    }

    @Override
    public void loadFailure(String errorMsg) {
        isLoading = false;
        showToastMsgShort(errorMsg);
        tv_balance.setText(getResources().getString(R.string.no_data));
    }
}
