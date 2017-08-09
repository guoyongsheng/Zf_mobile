package com.zfsoftmh.ui.modules.common.home.mine;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.cloud.contact.YWProfileInfo;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContactService;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.MyPortalInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 我的界面presenter
 */
public class MinePresenter implements MineContract.Presenter {

    private MineContract.View view;
    private CompositeDisposable compositeDisposable;
    private PersonalAffairsApi personalAffairsApi;
    private PersonalAffairsApi personalAffairsApiNoEncode; //不加密的
    private HttpManager httpManager;

    MinePresenter(MineContract.View view, PersonalAffairsApi personalAffairsApi,
                  PersonalAffairsApi personalAffairsApiNoEncode, HttpManager httpManager) {
        this.view = view;
        this.personalAffairsApi = personalAffairsApi;
        this.personalAffairsApiNoEncode = personalAffairsApiNoEncode;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }

    @Override
    public void loadData() {

        httpManager.request(personalAffairsApi.getMyPortalInfo(), compositeDisposable, view,
                new CallBackListener<MyPortalInfo>() {
                    @Override
                    public void onSuccess(MyPortalInfo data) {
                        view.loadSuccess(data);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.loadFailure(errorMsg);
                    }
                });
    }

    @Override
    public void upLoadFile(RequestBody userId, RequestBody apptoken, MultipartBody.Part file, final String path) {

        view.showUpLoadDialog(Constant.PICTURE_UPLOADING);
        httpManager.request(personalAffairsApiNoEncode.upLoadUserIcon(userId, apptoken, file),
                compositeDisposable, view, new CallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        view.hideUpLoadDialog();
                        view.saveUserInfo(path);
                        view.upLoadSuccess(Constant.PICTURE_UPLOAD_SUCCESS);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.hideUpLoadDialog();
                        view.upLoadFailure(Constant.PICTURE_UPLOAD_FAILURE);
                    }
                });
    }

    /**
     * 签到
     *
     * @param source    签到一次所加积分
     * @param appSource 签到来源（1.移动端签到 ，2.web,3.其他的）
     */
    @Override
    public void signIn(String source, String appSource) {
        view.showUpLoadDialog(Constant.loading);
        httpManager.request(personalAffairsApi.signIn(source, appSource),
                compositeDisposable, view, new CallBackListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.hideUpLoadDialog();
                view.singInSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.hideUpLoadDialog();
                view.upLoadFailure(errorMsg);

            }
        });

    }

    void upLoadProfile(final String path) {
        YWIMKit mIMKit = IMKitHelper.getInstance().getIMKit();
        final IYWContactService contactService = mIMKit.getIMCore().getContactService();
        ArrayList<String> userIds = new ArrayList<>();
        userIds.add(mIMKit.getIMCore().getLongLoginUserId());
        contactService.fetchUserProfile(userIds, IMKitHelper.APP_KEY, new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                if (result != null) {
                    List<YWProfileInfo> profileList = (List<YWProfileInfo>) result[0];
                    if (profileList == null || profileList.isEmpty()) return;
                    final YWProfileInfo mYWProfileInfo = profileList.get(0);
                    mYWProfileInfo.icon = path;
                    contactService.clearContactInfoCache(mYWProfileInfo.userId, IMKitHelper.APP_KEY);//清除用户缓存
                    contactService.updateProfileInfo(IMKitHelper.APP_KEY, mYWProfileInfo);//更新用户信息
                    contactService.updateProfileInfoToServer(mYWProfileInfo, new IWxCallback() {//更新用户信息到服务器
                        @Override
                        public void onSuccess(Object... objects) {
                            contactService.notifyContactProfileUpdate(mYWProfileInfo.userId, IMKitHelper.APP_KEY);//通知用户信息有更新
                        }

                        @Override
                        public void onError(int i, String s) {
                        }

                        @Override
                        public void onProgress(int i) {
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onProgress(int i) {
            }
        });
    }
}
