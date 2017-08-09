package com.zfsoftmh.ui.modules.personal_affairs.one_card.card_recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.pay.entity.BizContent;
import com.zfsoftmh.pay.entity.PayResult;
import com.zfsoftmh.pay.entity.SignInfo;
import com.zfsoftmh.pay.util.OrderInfoUtil2_0;
import com.zfsoftmh.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: ui
 */

public class CardRechargeFragment extends BaseFragment<CardRechargePresenter> implements
        CardRechargeContract.View, View.OnClickListener, CardRechargeDialogFragment.OnOkClickListener {

    private static final String TAG = "CardRechargeFragment";
    private static final String DEFAULT_RECHARGE_AMOUNT = "0.0";

    private TextView tv_recharge_amount; //充值金额
    private RelativeLayout rl_recharge_amount; //充值金额布局
    private Button btn_pay; //支付

    private String userName; //用户名

    @Override
    protected void initVariables() {
        User user = DbHelper.getUserInfo(context);
        if (user != null) {
            userName = user.getName();
        }
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_card_recharge;
    }

    @Override
    protected void initViews(View view) {
        TextView tv_school_name = (TextView) view.findViewById(R.id.card_recharge_school_name);
        TextView tv_name = (TextView) view.findViewById(R.id.card_recharge_name);
        TextView tv_card_number = (TextView) view.findViewById(R.id.card_recharge_card_name);
        tv_recharge_amount = (TextView) view.findViewById(R.id.card_recharge_recharge_number);
        rl_recharge_amount = (RelativeLayout) view.findViewById(R.id.rl_recharge_amount);
        btn_pay = (Button) view.findViewById(R.id.card_recharge_pay);

        tv_school_name.setText(Config.SCHOOL.SCHOOL_NAME);
        tv_name.setText(userName);
        tv_card_number.setText(getResources().getString(R.string.no_data));
    }

    @Override
    protected void initListener() {
        rl_recharge_amount.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
    }

    public static CardRechargeFragment newInstance() {
        return new CardRechargeFragment();
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 弹出输入金额的对话框
         */
        case R.id.rl_recharge_amount:
            showCardRechargeDialog();
            break;

        /*
         * 支付
         */
        case R.id.card_recharge_pay:
            recharge();
            break;

        default:
            break;
        }
    }

    @Override
    public void showCardRechargeDialog() {
        CardRechargeDialogFragment fragment = CardRechargeDialogFragment.newInstance();
        fragment.setOnOkClickListener(this);
        fragment.show(getChildFragmentManager(), TAG);
    }

    @Override
    public boolean checkRechargeAmountIsNull(String rechargeAmount) {
        return rechargeAmount == null || rechargeAmount.trim().length() == 0 ||
                rechargeAmount.equals("0") || rechargeAmount.equals("0.0") || rechargeAmount.equals("0.00");
    }

    @Override
    public String getCardRecharge() {
        return tv_recharge_amount.getText().toString();
    }

    @Override
    public void recharge() {
        if (checkRechargeAmountIsNull(getCardRecharge())) {
            showToastMsgShort(getResources().getString(R.string.please_input_recharge_amount));
            return;
        }
        String app_id = com.zfsoftmh.pay.config.Config.APP_ID; //应用的id
        String method = "alipay.trade.app.pay"; //接口名称
        String timestamp = getTimeStamp(); //请求时间
        BizContent biz_content = getBizContent(); //业务参数
        getSign(getSignInfo(app_id, method, timestamp, biz_content), GsonHelper.objectToString(biz_content));
    }

    @Override
    public BizContent getBizContent() {
        String total_amount = getCardRecharge(); //充值金额
        String subject = getResources().getString(R.string.card_recharge);
        String out_trade_no = OrderInfoUtil2_0.getOutTradeNo(); //商户网站唯一订单号
        BizContent bizContent = new BizContent();
        bizContent.setTotal_amount(total_amount);
        bizContent.setSubject(subject);
        bizContent.setOut_trade_no(out_trade_no);
        bizContent.setProduct_code("QUICK_MSECURITY_PAY");
        bizContent.setTimeout_express("30m");
        return bizContent;
    }

    @Override
    public String getSignInfo(String app_id, String method, String timestamp, BizContent bizContent) {
        SignInfo signInfo = new SignInfo();
        signInfo.setApp_id(app_id);
        signInfo.setMethod(method);
        signInfo.setTimestamp(timestamp);
        signInfo.setBiz_content(bizContent);
        signInfo.setCharset("utf-8");
        signInfo.setNotify_url("http://demo.zfsoft.com/ALiPay/servlet/NotifyServlet/getSign");
        signInfo.setVersion("1.0");
        signInfo.setSign_type("RSA");
        return GsonHelper.objectToString(signInfo);
    }


    @Override
    public String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        return format.format(date);
    }

    @Override
    public void getSign(String orderParam, String biz_content_android) {
        presenter.getSign(orderParam, biz_content_android);
    }

    @Override
    public void showDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }

    @Override
    public void loadFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    @Override
    public void loadSuccess(final SignInfo signInfo) {

        Observable.create(new ObservableOnSubscribe<PayResult>() {
            @Override
            public void subscribe(ObservableEmitter<PayResult> e) throws Exception {

                if (signInfo == null) {
                    LoggerHelper.e(TAG, " loadSuccess signInfo = " + null);
                    e.onError(null);
                    return;
                }
                String biz_content = GsonHelper.objectToString(signInfo.getBiz_content());
                PayTask payTask = new PayTask((CardRechargeActivity) context);
                e.onNext(new PayResult(payTask.payV2(OrderInfoUtil2_0.buildOrderParam(signInfo, biz_content), true)));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PayResult>() {
                    @Override
                    public void accept(@NonNull PayResult payResult) throws Exception {
                        if (!isActive() || payResult == null) {
                            LoggerHelper.e(TAG, " loadSuccess payResult = " + payResult);
                            return;
                        }

                        showToastMsgShort(payResult.getResultStatus() + " " + payResult.getResult());
                        String status = payResult.getResultStatus();
                        if (status != null && status.equals("9000")) {
                            showToastMsgShort(getResources().getString(R.string.pay_success));
                        } else {
                            showToastMsgShort(getResources().getString(R.string.pay_failure));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (!isActive()) {
                            return;
                        }

                        showToastMsgShort(getResources().getString(R.string.pay_failure));
                    }
                });
    }

    @Override
    public void onOkClick(String rechargeAmount) {
        if (checkRechargeAmountIsNull(rechargeAmount)) {
            tv_recharge_amount.setText(DEFAULT_RECHARGE_AMOUNT);
        } else {
            tv_recharge_amount.setText(rechargeAmount);
        }
    }
}
