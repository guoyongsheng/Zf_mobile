package com.zfsoftmh.ui.modules.common.splash;

import android.os.Bundle;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.common.guide.GuideActivity;
import com.zfsoftmh.ui.modules.common.home.HomeActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: ui展示
 */

public class SplashFragment extends BaseFragment<SplashPresenter> implements SplashContract.View {

    private boolean isFirstTimeIn; //用户对象

    @Override
    protected void initVariables() {
        isFirstTimeIn = DbHelper.checkUserIsFirstTimeIn(context);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_content;
    }

    @Override
    protected void initViews(View view) {
        if (DbHelper.checkUserIsLogin(context)) {
            String userID = DbHelper.getUserId(context);
            IMKitHelper.getInstance().initIMKit(userID);
            IMKitHelper.getInstance().login(userID, null);
        }

        if (isFirstTimeIn) {
            addUpInstallNumber();
            openActivity(0);
        } else {
            openActivity(1);
        }
    }

    //根据code跳转到不同的界面 0:跳转到GuideActivity  1:跳转到HomeActivity
    private void openActivity(final int pageCode) {
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        switch (pageCode) {
                        /*
                         * 跳转到GuideActivity
                         */
                        case 0:
                            startActivity(GuideActivity.class);
                            break;

                        /*
                         * 跳转到 HomeActivity
                         */
                        case 1:
                            startActivity(HomeActivity.class);
                            break;


                        default:
                            break;
                        }
                        ((SplashActivity) context).finish();
                    }
                });
    }

    @Override
    protected void initListener() {

    }

    /**
     * 获取Fragment的实例
     *
     * @return SplashFragment对象
     */
    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void addUpInstallNumber() {
        presenter.addUpInstallNumber();
    }

    @Override
    public void beginTransition() {

    }
}
