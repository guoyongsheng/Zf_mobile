package com.zfsoftmh.ui.modules.personal_affairs.set.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.KeyboardUtils;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.set.feedback.suggestions.SuggestionsActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangshimei
 * @date: 2017/3/24
 * @Description: ui
 */

public class FeedBackFragment extends BaseFragment<FeedBackPresenter> implements
        FeedBackContract.View, View.OnClickListener {

    private TextView feedback_tv, reply_tv;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_feed_back;
    }

    @Override
    protected void initViews(View view) {
        feedback_tv = (TextView) view.findViewById(R.id.feedback_tv);
        reply_tv = (TextView) view.findViewById(R.id.reply_tv);
    }

    @Override
    protected void initListener() {
        feedback_tv.setOnClickListener(this);
        reply_tv.setOnClickListener(this);
    }

    public static FeedBackFragment newInstance() {
        return new FeedBackFragment();
    }

    @Override
    public void onClick(View view) {

        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
            /**
             * 我要反馈
             */
            case R.id.feedback_tv:
                startActivity(SuggestionsActivity.class);
                break;
            /**
             * 我的回复
             */
            case R.id.reply_tv:
                break;
            default:
                break;
        }
    }


    @Override
    public void showErrorMsg(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    @Override
    public void feedBackSuccess() {
        ((FeedBackActivity) context).finish();
    }

}
