package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.IntegralMallGoodsInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_goods_detail.IntegralGoodsDetailActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/7/17
 * @Description: 积分商品搜索页
 */

public class IntegralGoodsSearchFragment extends BaseListFragment<IntegralGoodsSearchPresenter, IntegralMallGoodsInfo>
        implements IntegralGoodsSearchContract.View, RecyclerArrayAdapter.ItemView {

    private View integralGoodsView;
    private IntegralMallGoodsAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private String price = ""; // 积分排序（1为高到低，2为低到高，不传值则按商品创建时间排序，传值则依次按价格、创建时间排序 ）
    private String goodsName = ""; // 商品名，用于搜索
    private LinearLayout integral_up_or_down; // 积分排序视图
    private static final int REQUEST_CODE_DETAIL = 11; //详情跳转请求码
    private ImageView sort_icon; // 积分排序图标

    @Inject
    IntegralGoodsSearchPresenter presenter;


    public static IntegralGoodsSearchFragment newInstance() {
        return new IntegralGoodsSearchFragment();
    }

    @Override
    protected int getLayoutResID() {
        DaggerIntegralGoodsSearchComponent.builder()
                .appComponent(getAppComponent())
                .integralGoodsSearchPresenterModule(new IntegralGoodsSearchPresenterModule(this))
                .build()
                .inject(this);
        return super.getLayoutResID();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        gridLayoutManager = new GridLayoutManager(context, 2);
        return gridLayoutManager;
    }

    @Override
    protected RecyclerArrayAdapter<IntegralMallGoodsInfo> getAdapter() {
        adapter = new IntegralMallGoodsAdapter(context);
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(2));
        return adapter;
    }

    @Override
    protected void initVariables() {
        setHasOptionsMenu(true);
    }

    @Override
    protected void initHeaderView() {
        super.initHeaderView();
        adapter.addHeader(this);
    }

    @Override
    protected void loadData() {
        loadIntegralGoodsList(start_page, PAGE_SIZE);
    }

    @Override
    public void onItemClick(int position) {
        IntegralMallGoodsInfo bean = adapter.getItem(position);
        if (bean != null) {
            Intent intent = new Intent(getActivity(), IntegralGoodsDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("IntegralMallFragment", bean);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE_DETAIL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            /**
             * 刷新积分数
             */
            case REQUEST_CODE_DETAIL:
                onRefresh();
                break;
        }
    }

    @Override
    public void loadIntegralGoodsList(int start_page, int PAGE_SIZE) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("start", String.valueOf(start_page));
        params.put("size", String.valueOf(PAGE_SIZE));
        params.put("price", price);
        params.put("goodsname", goodsName);
        presenter.getIntegralGoodsList(params);
    }

    /**
     * 积分排序状态
     */
    @Override
    public void integralLiftingState() {
        if (price.equals("1")) {  // 降序
            sort_icon.setImageResource(R.mipmap.downsort);
        } else if (price.equals("2")) { // 升序
            sort_icon.setImageResource(R.mipmap.upsort);
        }
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    public View onCreateView(ViewGroup parent) {
        // 添加头布局
        integralGoodsView = LayoutInflater.from(getActivity()).inflate(R.layout.item_integral_goods_sort, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);

        integralGoodsView.setLayoutParams(params);
        // 积分升降视图
        integral_up_or_down = (LinearLayout) integralGoodsView.findViewById(R.id.integral_up_or_down);
        integral_up_or_down.setOnClickListener(onceClickListener);
        sort_icon = (ImageView) integralGoodsView.findViewById(R.id.sort_icon);
        return integralGoodsView;
    }

    @Override
    public void onBindView(View headerView) {

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
                 * 升降视图
                 */
                case R.id.integral_up_or_down:
                    if (price.equals("2")) {
                        // 降序
                        price = "1";
                    } else if (price.equals("1") || price.equals("")) {
                        // 升序
                        price = "2";
                    }
                    integralLiftingState();
                    onRefresh();
                    break;
            }
        }
    };

}
