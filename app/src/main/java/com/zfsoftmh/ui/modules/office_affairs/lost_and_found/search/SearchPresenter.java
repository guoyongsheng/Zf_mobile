package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.search;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 失物招领搜索---业务逻辑
 */

class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    SearchPresenter(SearchContract.View view, OfficeAffairsApi officeAffairsApi, HttpManager httpManager) {
        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void loadData(int start, int pageSize, String title, int type, String userId, String apptoken) {

        String start_encode = CodeUtil.getEncodedValueWithToken(String.valueOf(start), apptoken);
        String page_encode = CodeUtil.getEncodedValueWithToken(String.valueOf(pageSize), apptoken);
        String type_encode = CodeUtil.getEncodedValueWithToken(String.valueOf(type), apptoken);
        String title_encode = CodeUtil.getEncodedValueWithToken(title, apptoken);
        String userId_encode = CodeUtil.getEncodedValueWithToken("", apptoken);

        view.showDialog(Constant.SEARCHING);
        httpManager.request(officeAffairsApi.getLostAndFoundListInfo(start_encode, page_encode,
                type_encode, title_encode, userId_encode, apptoken), compositeDisposable, view,
                new CallBackListener<ResponseListInfo<LostAndFoundItemInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<LostAndFoundItemInfo> data) {
                        view.hideDialog();
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideDialog();
                        view.loadFailure(errorMsg);
                    }
                });
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
