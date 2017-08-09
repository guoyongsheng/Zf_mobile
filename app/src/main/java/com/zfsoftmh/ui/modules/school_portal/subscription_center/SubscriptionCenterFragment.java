package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.config.ServiceCodeConfig;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.entity.AppServiceInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.AgencyMattersActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;
import com.zfsoftmh.ui.widget.itemtouchhelper.ItemDragHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: ui
 */

public class SubscriptionCenterFragment extends BaseFragment<SubscriptionCenterPresenter>
        implements SubscriptionCenterContract.View, SubscriptionCenterAdapter.OnMenuItemTitleChangeListener,
        SubscriptionCenterAdapter.OnItemClickListener {

    private static final String TAG = "SubscriptionCenterFragment";
    private static final int SPAN_COUNT = 4;
    private ArrayList<AppCenterItemInfo> list;
    private ArrayList<AppCenterItemInfo> listBefore = new ArrayList<>();

    private SubscriptionCenterAdapter adapter; //适配器

    private MenuItem menuItem;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        list = bundle.getParcelableArrayList("list");
        if (list != null) {
            listBefore.addAll(list);
        }
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(final View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));

        //LayoutManager
        final GridLayoutManager layoutManager = new GridLayoutManager(context, SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                if (viewType == SubscriptionCenterAdapter.TYPE_OTHER_HEADER ||
                        viewType == SubscriptionCenterAdapter.TYPE_SELECTED_HEADER) {
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });

        //ItemTouchHelper
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        //adapter
        adapter = new SubscriptionCenterAdapter(context, helper, list);
        adapter.setOnMenuItemTitleChangeListener(this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        getAppCenterInfo();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_subscription_center, menu);
        menuItem = menu.findItem(R.id.menu_subscription_center);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        case R.id.menu_subscription_center:
            if (checkIsEditMode(item.getTitle().toString())) {
                adapter.setEditMode(false);
                updateMenuItemTitle(getResources().getString(R.string.edit));
            } else {
                adapter.setEditMode(true);
                updateMenuItemTitle(getResources().getString(R.string.done));
            }
            adapter.notifyDataSetChanged();
            return true;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static SubscriptionCenterFragment newInstance(ArrayList<AppCenterItemInfo> list) {
        SubscriptionCenterFragment fragment = new SubscriptionCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void getAppCenterInfo() {
        presenter.getAppCenterInfo();
    }

    @Override
    public void loadSuccess(ArrayList<AppCenterItemInfo> list) {
        adapter.setListOther(list);
    }

    @Override
    public void loadFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
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
    public void serviceSubmitSuccess() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        intent.putExtras(bundle);
        ((SubscriptionCenterActivity) context).setResult(Activity.RESULT_OK, intent);
        ((SubscriptionCenterActivity) context).finish();
    }

    @Override
    public void serviceSubmitFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
        ((SubscriptionCenterActivity) context).finish();
    }

    @Override
    public boolean checkIsEditMode(String title) {
        return title != null && title.equals(getResources().getString(R.string.done));
    }

    @Override
    public void updateMenuItemTitle(String title) {
        if (menuItem != null) {
            menuItem.setTitle(title);
        }
    }

    @Override
    public boolean checkHasService(ArrayList<AppCenterItemInfo> list) {
        return !(list == null || list.size() == 0);
    }

    @Override
    public String objectToString(List<String> list) {
        AppServiceInfo info = new AppServiceInfo();
        info.setServiceList(list);
        info.setUserId(getUserId());
        return GsonHelper.objectToString(info);
    }

    @Override
    public String getUserId() {
        return DbHelper.getUserId(context);
    }

    /**
     * 判断是否是编辑状态
     *
     * @return true: 是编辑状态  false: 不是编辑状态
     */
    public boolean checkIsEditMode() {
        return adapter.isEditMode();
    }

    /**
     * 设置成完成状态
     */
    public void setIsNotEditMode() {
        adapter.setEditMode(false);
        updateMenuItemTitle(getResources().getString(R.string.edit));
        adapter.notifyDataSetChanged();
    }

    /**
     * 保存选中的服务
     */
    public void saveSelectService() {
        ArrayList<AppCenterItemInfo> listSelect = adapter.getSelectedService();
        List<String> listString = adapter.getServiceCode();
        if (!checkHasService(listSelect)) {
            showToastMsgShort(getResources().getString(R.string.please_select_app));
            return;
        }

        if (listSelect != null && listSelect.equals(listBefore)) {
            ((SubscriptionCenterActivity) context).finish();
            return;
        }
        presenter.submitService(objectToString(listString));
    }

    /**
     * 判断是否有已选应用
     *
     * @return true: 有  false: 沒有
     */
    public boolean checkHasSelectedApp() {
        return adapter.checkHasSelectService();
    }

    @Override
    public void onMenuItemTitleChange(String title) {
        updateMenuItemTitle(title);
    }

    @Override
    public void onItemClick(AppCenterItemInfo itemInfo) {
        if (itemInfo == null) {
            return;
        }
        String type = itemInfo.getType(); //服务类型
        if (type != null && type.equals(Constant.APP_APPLICATION)) {
            dealWithAppApplication(itemInfo);
        } else if (type != null && type.equals(Constant.APP_SERVICE)) {
            dealWithLocalService(itemInfo);
        } else if (type != null && type.equals(Constant.WEB_SERVICE)) {
            dealWithWebService(itemInfo);
        }
    }

    private void dealWithAppApplication(AppCenterItemInfo itemInfo) {
        LoggerHelper.e(TAG, itemInfo.getId());
    }

    //处理本地服务
    private void dealWithLocalService(AppCenterItemInfo itemInfo) {
        String serviceCode = itemInfo.getServiceCode(); //服务编码
        Class<?> activity = ServiceCodeConfig.getActivityByServiceCode(serviceCode);
        if (activity == null) {
            showToastMsgShort(getResources().getString(R.string.not_know_activity));
            return;
        }
        Intent intent = new Intent(context, activity);
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", itemInfo);
        if (activity.equals(AgencyMattersActivity.class)) {
            if (serviceCode != null && serviceCode.equals(String.valueOf(ServiceCodeConfig.OfficeAffairs.SERVICE_CODE_AGENCY_MATTERS))) {
                bundle.putInt("currentItem", 0);
            } else if (serviceCode != null && serviceCode.equals(String.valueOf(ServiceCodeConfig.OfficeAffairs.SERVICE_CODE_HAS_BEEN_DONE))) {
                bundle.putInt("currentItem", 1);
            } else if (serviceCode != null && serviceCode.equals(String.valueOf(ServiceCodeConfig.OfficeAffairs.SERVICE_CODE_SETTLEMENT))) {
                bundle.putInt("currentItem", 2);
            }
        }
        intent.putExtras(bundle);
        openActivity(intent);
    }

    //处理网页服务
    private void dealWithWebService(AppCenterItemInfo itemInfo) {
        String otherFlag = itemInfo.getOtherFlag();
        if (checkIsOtherFlag(otherFlag)) {
            openWebActivity(itemInfo.getName(), itemInfo.getUrl(), itemInfo.getProcode());
        } else {
            openWebActivity(itemInfo.getName(), itemInfo.getUrl());
        }
    }

    //打开web界面
    private void openWebActivity(String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Config.WEB.URL, url);
        bundle.putString(Config.WEB.TITLE, title);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    //打开web界面
    private void openWebActivity(String title, String url, String proCode) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Config.WEB.URL, url);
        bundle.putString(Config.WEB.TITLE, title);
        bundle.putString(Config.WEB.PRO_CODE, proCode);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    //判断是否是otherFlag
    private boolean checkIsOtherFlag(String otherFlag) {
        return otherFlag != null && otherFlag.equals("0");
    }
}
