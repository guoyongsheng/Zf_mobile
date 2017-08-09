package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.my_realeased;

import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 我的发布界面的业务逻辑
 */

class MyReleasedPresenter implements MyReleasedContract.Presenter {

    private MyReleasedContract.View view;
    private OfficeAffairsApi officeAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;


    MyReleasedPresenter(MyReleasedContract.View view, OfficeAffairsApi officeAffairsApi,
            HttpManager httpManager) {

        this.view = view;
        this.officeAffairsApi = officeAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void loadData(int start, int pageSize, int type, String userId, String apptoken) {

        String start_encode = CodeUtil.getEncodedValueWithToken(String.valueOf(start), apptoken);
        String page_encode = CodeUtil.getEncodedValueWithToken(String.valueOf(pageSize), apptoken);
        String type_encode = CodeUtil.getEncodedValueWithToken("", apptoken);
        String title_encode = CodeUtil.getEncodedValueWithToken("", apptoken);
        String userId_encode = CodeUtil.getEncodedValueWithToken(userId, apptoken);

        httpManager.request(officeAffairsApi.getLostAndFoundListInfo(start_encode, page_encode,
                type_encode, title_encode, userId_encode, apptoken), compositeDisposable, view,
                new CallBackListener<ResponseListInfo<LostAndFoundItemInfo>>() {
                    @Override
                    public void onSuccess(ResponseListInfo<LostAndFoundItemInfo> data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
