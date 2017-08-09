package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.entity.IntegralRankingInfo;
import com.zfsoftmh.entity.IntegralRankingItemInfo;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wangshimei
 * @date: 17/7/25
 * @Description: 积分排名
 */

public class IntegralRankingFragment extends BaseFragment<IntegralRankingPresenter> implements IntegralRankingContract.View {

    private String userName; // 用户名
    private String header; // 用户头像
    private String integralNumber; // 个人积分
    private String ranking; // 个人排名

    private List<IntegralRankingItemInfo> headerList = new ArrayList<>(); // 头部数据
    private List<IntegralRankingItemInfo> dataList = new ArrayList<>(); // 列表数据
    private IntegralRankingInfo rankingInfo;
    private IntegralRankingAdapter adapter;
    private RecyclerView ranking_recycler;
    private TextView no_data; // 暂无数据
    private RelativeLayout ranking_bottom; // 底部视图
    private TextView mine_ranking_number; // 个人排名
    private CircleImageView mine_ranking_header; // 个人头像
    private TextView mine_ranking_name; // 个人姓名
    private TextView mine_ranking_integral; // 个人积分数值
    private CircleImageView first_ranking_header, second_ranking_header, third_ranking_header; // 前三名排名头像
    private TextView first_ranking_name, second_ranking_name, third_ranking_name; // 前三名姓名
    private TextView first_ranking_number, second_ranking_number, third_ranking_number; // 前三名积分

    public static IntegralRankingFragment newInstance() {
        return new IntegralRankingFragment();
    }

    @Override
    protected void initVariables() {
        User user = DbHelper.getUserInfo(getActivity());
        if (user != null) {
            userName = user.getName();
            header = user.getHeadPicturePath();
        }
        integralNumber = SharedPreferenceUtils.readString(getActivity(), "integral", "integral");
        adapter = new IntegralRankingAdapter(context);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_integral_ranking;
    }

    @Override
    protected void initViews(View view) {
        ranking_recycler = (RecyclerView) view.findViewById(R.id.ranking_recycler);
        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        ranking_recycler.setLayoutManager(layoutManager);

        //adapter
        ranking_recycler.setAdapter(adapter);

        no_data = (TextView) view.findViewById(R.id.no_data);
        ranking_bottom = (RelativeLayout) view.findViewById(R.id.ranking_bottom);
        ranking_bottom.getBackground().setAlpha(245);//0~255透明度值
        mine_ranking_number = (TextView) view.findViewById(R.id.mine_ranking_number);
        mine_ranking_header = (CircleImageView) view.findViewById(R.id.mine_ranking_header);
        mine_ranking_name = (TextView) view.findViewById(R.id.mine_ranking_name);
        mine_ranking_integral = (TextView) view.findViewById(R.id.mine_ranking_integral);
        initHeaderView();
        loadIntegralRanking();
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
    public void loadIntegralRanking() {
        presenter.getIntegralRankingList();
    }

    @Override
    public void loadFailure(String s) {
        showToastMsgLong(s);
        no_data.setVisibility(View.VISIBLE);
        ranking_bottom.setVisibility(View.GONE);
        ranking_recycler.setVisibility(View.GONE);
    }

    @Override
    public void loadSuccess(IntegralRankingInfo info) {

        if (info != null) {
            no_data.setVisibility(View.GONE);
            ranking_bottom.setVisibility(View.VISIBLE);
            ranking_recycler.setVisibility(View.VISIBLE);
            rankingInfo = info;
            divideList();
            serPersonView();
            integralRankingView();
        } else {
            loadFailure("暂无数据");
        }
    }

    /**
     * 数据解析分离
     */
    @Override
    public void divideList() {
        ranking = rankingInfo.ranking;
        List<IntegralRankingItemInfo> itemInfo = new ArrayList<>();
        itemInfo = rankingInfo.itemList;
        if (itemInfo != null) {
            if (itemInfo.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    IntegralRankingItemInfo bean = new IntegralRankingItemInfo();
                    bean.ranking = itemInfo.get(i).ranking;
                    bean.xm = itemInfo.get(i).xm;
                    bean.wjlj = itemInfo.get(i).wjlj;
                    bean.source = itemInfo.get(i).source;
                    headerList.add(bean);
                }
                for (int i = 3; i < itemInfo.size(); i++) {
                    IntegralRankingItemInfo bean = new IntegralRankingItemInfo();
                    bean.ranking = itemInfo.get(i).ranking;
                    bean.xm = itemInfo.get(i).xm;
                    bean.wjlj = itemInfo.get(i).wjlj;
                    bean.source = itemInfo.get(i).source;
                    dataList.add(bean);
                }
            } else {
                headerList = itemInfo;
                dataList = null;
            }
        } else {
            no_data.setVisibility(View.VISIBLE);
            ranking_recycler.setVisibility(View.GONE);
        }
    }

    /**
     * 设置个人排名
     */
    @Override
    public void serPersonView() {
        if (ranking != null)
            mine_ranking_number.setText(ranking);
        if (header != null)
            ImageLoaderHelper.loadImage(context, mine_ranking_header, header);
        if (userName != null)
            mine_ranking_name.setText(userName);
        if (integralNumber != null)
            mine_ranking_integral.setText(integralNumber);
    }

    /**
     * 设置列表数据
     */
    @Override
    public void integralRankingView() {
        for (int i = 0; i < headerList.size(); i++) {
            if (i == 0) {
                if (headerList.get(0).wjlj != null)
                    ImageLoaderHelper.loadImage(context, first_ranking_header, headerList.get(0).wjlj);

                if (headerList.get(0).xm != null)
                    first_ranking_name.setText(headerList.get(0).xm);
                first_ranking_number.setText(String.valueOf(headerList.get(0).source));
            } else if (i == 1) {
                if (headerList.get(1).wjlj != null)
                    ImageLoaderHelper.loadImage(context, second_ranking_header, headerList.get(1).wjlj);

                if (headerList.get(1).xm != null)
                    second_ranking_name.setText(headerList.get(1).xm);
                second_ranking_number.setText(String.valueOf(headerList.get(1).source));
            } else if (i == 2) {
                if (headerList.get(2).wjlj != null)
                    ImageLoaderHelper.loadImage(context, third_ranking_header, headerList.get(2).wjlj);

                if (headerList.get(2).xm != null)
                    third_ranking_name.setText(headerList.get(2).xm);
                third_ranking_number.setText(String.valueOf(headerList.get(2).source));
            }
        }
        if (dataList != null) {
            adapter.initItemList(dataList);

        }


    }

    @Override
    public void initHeaderView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_integral_ranking_top, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        first_ranking_header = (CircleImageView) view.findViewById(R.id.first_ranking_header);
        second_ranking_header = (CircleImageView) view.findViewById(R.id.second_ranking_header);
        third_ranking_header = (CircleImageView) view.findViewById(R.id.third_ranking_header);

        first_ranking_name = (TextView) view.findViewById(R.id.first_ranking_name);
        second_ranking_name = (TextView) view.findViewById(R.id.second_ranking_name);
        third_ranking_name = (TextView) view.findViewById(R.id.third_ranking_name);

        first_ranking_number = (TextView) view.findViewById(R.id.first_ranking_number);
        second_ranking_number = (TextView) view.findViewById(R.id.second_ranking_number);
        third_ranking_number = (TextView) view.findViewById(R.id.third_ranking_number);

        adapter.setHeaderView(view);

    }


}
