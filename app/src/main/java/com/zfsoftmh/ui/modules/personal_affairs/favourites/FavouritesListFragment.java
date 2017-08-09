package com.zfsoftmh.ui.modules.personal_affairs.favourites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.mobileim.fundamental.widget.YWAlertDialog;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.entity.FavouritesInfo;
import com.zfsoftmh.entity.FavouritesListInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.personal_affairs.favourites.favourites_detail.FavouritesDetailActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description: 我的收藏
 */

public class FavouritesListFragment extends BaseListFragment<FavouritesListPresenter, FavouritesListInfo>
        implements FavouritesListContract.View, FavouritesListAdapter.OnItemLongListener {

    private FavouritesListAdapter adapter;


    public static FavouritesListFragment newInstance() {
        return new FavouritesListFragment();
    }

    @Override
    protected RecyclerArrayAdapter<FavouritesListInfo> getAdapter() {
        adapter = new FavouritesListAdapter(context);
        adapter.setLongListener(this);
        return adapter;
    }

    @Override
    protected void loadData() {
        loadFavouritesList(start_page, PAGE_SIZE);
    }

    @Override
    public void onItemClick(int position) {
        FavouritesListInfo bean = adapter.getItem(position);
        if (bean != null && !bean.equals("")) {
            if (bean.favouritesort.equals("1") || bean.favouritesort.equals("2")) { // 1.文本，2.图片
                Intent intent = new Intent(getActivity(), FavouritesDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("favouritesList", bean);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Config.WEB.TITLE, Constant.favourites_detail_title);
                if (bean.favouritesort.equals("3") || bean.favouritesort.equals("4")) { // 3.视频，4.网页
                    bundle.putString(Config.WEB.URL, bean.favouritecontent);
                } else if (bean.favouritesort.equals("5")) { // 5.附件
                    bundle.putString(Config.WEB.URL, bean.attachmentPath);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

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
     * 获取收藏列表数据
     *
     * @param start_page 起始页
     * @param PAGE_SIZE  每页条数
     */
    @Override
    public void loadFavouritesList(int start_page, int PAGE_SIZE) {
        String userId = DbHelper.getUserId(getActivity());
        Map<String, String> params = new LinkedHashMap<>();
        params.put("start", String.valueOf(start_page));
        params.put("size", String.valueOf(PAGE_SIZE));
        params.put("username", userId);
        presenter.loadFavouritesList(params);
    }

    @Override
    public void loadFavouritesFailure(String msg) {
        showToastMsgShort(msg);
    }

    /**
     * 设置收藏列表数据
     *
     * @param data
     */
    @Override
    public void setFavouritesData(ResponseListInfo<FavouritesListInfo> data) {
            loadSuccess(data);
    }

    @Override
    public void deleteFavouritesSuccess(FavouritesListInfo data) {
        adapter.remove(data);
        showToastMsgShort(Constant.delete_success);
        onRefresh();
    }


    /**
     * 长按删除
     *
     * @param view
     * @param info
     */
    @Override
    public void onItemLongListener(View view, FavouritesListInfo info) {
        showDeletePopMenu(info);
    }

    /**
     * 显示删除窗口
     *
     * @param data
     */
    private void showDeletePopMenu(final FavouritesListInfo data) {
        final String[] items = new String[]{"删除"};
        new YWAlertDialog.Builder(context)
                .setTitle("删除")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                String favourId = data.favourid;
                                if (favourId != null && !favourId.equals(""))
                                    presenter.deleteFavourites(favourId, data);
                                break;
                        }

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}
