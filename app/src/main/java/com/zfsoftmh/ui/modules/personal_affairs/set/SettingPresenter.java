package com.zfsoftmh.ui.modules.personal_affairs.set;

import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.VersionInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.CommonApi;
import com.zfsoftmh.service.PersonalAffairsApi;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: presenter层
 */

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View view;
    private PersonalAffairsApi personalAffairsApi;
    private CommonApi commonApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

    public SettingPresenter(SettingContract.View view, PersonalAffairsApi personalAffairsApi,
            CommonApi commonApi, HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.commonApi = commonApi;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
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
}
