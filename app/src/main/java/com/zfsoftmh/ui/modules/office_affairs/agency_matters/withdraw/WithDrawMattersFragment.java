package com.zfsoftmh.ui.modules.office_affairs.agency_matters.withdraw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;

/**
 * @author wesley
 * @date: 2017/4/6
 * @Description: ui
 */

public class WithDrawMattersFragment extends BaseFragment<WithDrawMattersPresenter> implements
        WithDrawMattersContract.View {


    private TextView tv_with_draw_content; //退回的内容
    private Button btn_submit; //提交

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_with_draw_matters;
    }

    @Override
    protected void initViews(View view) {
        tv_with_draw_content = (TextView) view.findViewById(R.id.with_draw_content);
        btn_submit = (Button) view.findViewById(R.id.with_draw_submit);
    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawMatters();
            }
        });
    }

    public static WithDrawMattersFragment newInstance() {
        return new WithDrawMattersFragment();
    }

    @Override
    public String getWithDrawContent() {
        return tv_with_draw_content.getText().toString();
    }

    @Override
    public void withDrawMatters() {

    }
}
