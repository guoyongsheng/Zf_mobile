package com.zfsoftmh.ui.modules.common.home.home;

import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.entity.BannerInfo;
import com.zfsoftmh.entity.VersionInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.CommonApi;
import com.zfsoftmh.service.SchoolPortalApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 业务逻辑
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private SchoolPortalApi schoolPortalApi;
    private SchoolPortalApi noEncodeShSchoolPortalApi;
    private CommonApi commonApi;
    private CompositeDisposable compositeDisposable;
    private HttpManager httpManager;

    HomePresenter(HomeContract.View view, SchoolPortalApi schoolPortalApi, SchoolPortalApi
            noEncodeSchoolPortalApi, CommonApi commonApi, HttpManager httpManager) {
        this.view = view;
        this.schoolPortalApi = schoolPortalApi;
        this.noEncodeShSchoolPortalApi = noEncodeSchoolPortalApi;
        this.commonApi = commonApi;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void checkVersion(Map<String, String> map) {

        httpManager.request(commonApi.checkVersion(map), compositeDisposable, view, new CallBackListener<VersionInfo>() {
            @Override
            public void onSuccess(VersionInfo data) {
                view.showData(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public boolean checkShouldUpdate(VersionInfo versionInfo) {
        if (versionInfo == null) {
            return false;
        }
        String updateTpye = versionInfo.getUpdateTpye();
        return !(updateTpye == null || updateTpye.equals(Config.VERSION.VERSION_SHOULD_NOT_UPDATE));
    }

    @Override
    public boolean checkShouldUpdateForced(VersionInfo versionInfo) {
        if (versionInfo == null) {
            return false;
        }
        String updateTpye = versionInfo.getUpdateTpye();
        return updateTpye != null && updateTpye.equals(Config.VERSION.VERSION_SHOULD_UPDATE_FORCED);
    }

    @Override
    public void getHomeBanner(int maxSize) {

        httpManager.request(noEncodeShSchoolPortalApi.getBanner(maxSize), compositeDisposable, view,
                new CallBackListener<List<BannerInfo>>() {
                    @Override
                    public void onSuccess(List<BannerInfo> data) {
                        view.loadBannerInfoSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }

    @Override
    public void getHomeServiceWithNoLogin() {

        httpManager.request(noEncodeShSchoolPortalApi.getHomeServiceInfoWithNoLogin(),
                compositeDisposable, view, new CallBackListener<ArrayList<AppCenterItemInfo>>() {

                    @Override
                    public void onSuccess(ArrayList<AppCenterItemInfo> data) {
                        view.loadHomeServiceInfoSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });

    }

    @Override
    public void getHomeServiceWithLogin() {

        httpManager.request(schoolPortalApi.getHomeServiceInfoWithLogin(), compositeDisposable,
                view, new CallBackListener<ArrayList<AppCenterItemInfo>>() {

                    @Override
                    public void onSuccess(ArrayList<AppCenterItemInfo> data) {
                        view.loadHomeServiceInfoSuccess(data);
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
