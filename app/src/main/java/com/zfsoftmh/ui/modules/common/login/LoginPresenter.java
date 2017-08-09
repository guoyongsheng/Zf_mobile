package com.zfsoftmh.ui.modules.common.login;

import com.alibaba.mobileim.channel.event.IWxCallback;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.CommonApi;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 业务逻辑
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private CommonApi commonApi;
    private CompositeDisposable compositeDisposable;
    private HttpManager httpManager;

    public LoginPresenter(LoginContract.View view, CommonApi commonApi, HttpManager httpManager) {
        this.view = view;
        this.commonApi = commonApi;
        this.httpManager = httpManager;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String userName, String password, String key) {

        if (checkDataIsEmpty(userName)) {
            view.showErrorMsg(Constant.INPUT_USER_NAME);
            return;
        }

        if (checkDataIsEmpty(password)) {
            view.showErrorMsg(Constant.INPUT_PASSWORD);
            return;
        }

        view.showProgressDialog(Constant.logging);
        httpManager.request(commonApi.login(userName, password, key), compositeDisposable, view, new CallBackListener<User>() {
            @Override
            public void onSuccess(final User data) {
                if (IMKitHelper.getInstance().getIMKit() != null){//原来有登录的帐号要先登出
                    IMKitHelper.getInstance().getIMKit().getLoginService().logout(new IWxCallback() {
                        @Override
                        public void onSuccess(Object... objects) {
                            IMSignIn(data);
                        }

                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onProgress(int i) {

                        }
                    });
                }else{
                    IMSignIn(data);
                }
            }

            @Override
            public void onError(String errorMsg) {
                view.hideProgressDialog();
                view.showErrorMsg(errorMsg);
            }
        });
    }

    private void IMSignIn(final User data){
        String uID = data.getUserId();
        IMKitHelper.getInstance().initIMKit(uID);
        IMKitHelper.getInstance().login(uID, new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                view.hideProgressDialog();
                view.loginSuccess(data);
            }

            @Override
            public void onError(int i, String s) {
                view.hideProgressDialog();
                view.loginSuccess(data);
                LoggerHelper.e("im_sign_in: " + s);
            }

            @Override
            public void onProgress(int i) {

            }
        });
    }

    @Override
    public boolean checkDataIsEmpty(String data) {
        return (data == null || data.length() == 0);
    }

    @Override
    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
