package com.zfsoftmh.ui.modules.personal_affairs.favourites;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.FavouritesInfo;
import com.zfsoftmh.entity.FavouritesListInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description: 我的收藏业务处理
 */

public class FavouritesListPresenter implements FavouritesListContract.Presenter {

    private FavouritesListContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public FavouritesListPresenter(FavouritesListContract.View view,
                                   PersonalAffairsApi personalAffairsApi,
                                   HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.httpManager = httpManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void loadFavouritesList(Map<String, String> params) {
//        view.createLoadingDialog(Constant.loading);
        httpManager.request(personalAffairsApi.getFavouritesList(params), compositeDisposable, view, new CallBackListener<ResponseListInfo<FavouritesListInfo>>() {
            @Override
            public void onSuccess(ResponseListInfo<FavouritesListInfo> data) {
//                view.hideUpLoadingDialog();
                view.loadSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
//                view.hideUpLoadingDialog();
                view.loadFailure(errorMsg);
            }
        });
    }

    @Override
    public void deleteFavourites(String favourId, final FavouritesListInfo info) {
        view.createLoadingDialog(Constant.loading);
        httpManager.request(personalAffairsApi.deleteFavourites(favourId), compositeDisposable,
                view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideUpLoadingDialog();
                        view.deleteFavouritesSuccess(info);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideUpLoadingDialog();
                        view.loadFailure(errorMsg);
                    }
                });
    }
}
