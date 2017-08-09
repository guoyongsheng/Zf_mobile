package com.zfsoftmh.common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.entity.Response;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.ui.base.BaseApplication;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;
import com.zfsoftmh.ui.modules.common.login.LoginActivity;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 网络请求的工具类
 */
public class HttpManager {

    private static final String TAG = "HttpManager";

    public HttpManager() {

    }

    /**
     * 統一的网络请求
     *
     * @param observable          被观察者
     * @param <T>                 网络返回的数据
     * @param compositeDisposable 用于取消网络请求
     */
    public <T, K extends BasePresenter> void request(Observable<Response<T>> observable,
            final CompositeDisposable compositeDisposable, final BaseView<K> view,
            final CallBackListener<T> listener) {

        if (observable == null || compositeDisposable == null || view == null || listener == null) {
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<T> response) {
                        if (!view.isActive() || response == null) {
                            return;
                        }

                        int code = response.getCode();
                        switch (code) {
                        /*
                         * 失败
                         */
                        case 0:
                            listener.onError(response.getMsg());
                            break;

                        /*
                         * 成功
                         */
                        case 1:
                            listener.onSuccess(response.getData());
                            break;

                        /*
                         * token error---跳转到登录界面
                         */
                        case 2:
                            Context context = BaseApplication.getAppComponent().getContext();
                            if (context == null) {
                                LoggerHelper.e(TAG, "跳转失败 失败信息: context = null");
                                return;
                            }
                            Toast.makeText(context, context.getResources().getString
                                    (R.string.please_login_again), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("from", 1);
                            intent.putExtras(bundle);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                            break;

                        default:
                            listener.onError(response.getMsg());
                            break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!view.isActive() || e == null) {
                            return;
                        }
                        LoggerHelper.e(TAG, " onError " + e.getMessage());
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            switch (httpException.code()) {
                            /*
                             * 没有网络
                             */
                            case 504:
                                listener.onError(Constant.NET_WORK_ERROR);
                                break;

                            default:
                                listener.onError(Constant.REQUEST_FAILURE);
                                break;
                            }
                            return;
                        }
                        if (e instanceof ConnectException) {
                            listener.onError(Constant.CAN_NOT_CONNECT_TO_SERVER);
                            return;
                        }
                        if (e instanceof SocketException) {
                            listener.onError(Constant.NET_WORK_ERROR);
                            return;
                        }
                        if (e instanceof SocketTimeoutException) {
                            listener.onError(Constant.time_out);
                            return;
                        }
                        if (e instanceof JsonParseException || e instanceof JSONException ||
                                e instanceof MalformedJsonException) {
                            listener.onError(Constant.DATA_PARSE_EXCEPTION);
                            return;
                        }
                        listener.onError(Constant.REQUEST_FAILURE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * <p>
     * 另外的网络请求
     * </p>
     * <p>
     * created by sy
     * on 2017/6/21
     */
    public <T> void outRequest(Observable<Response<T>> observable, final CallBackListener<T> listener) {
        if (observable == null) {
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<T> response) {
                        int code = response.getCode();
                        switch (code) {
                        case 0:
                            listener.onError(response.getMsg());
                            break;
                        case 1:
                            listener.onSuccess(response.getData());
                            break;
                        case 2:
                            Context context = BaseApplication.getAppComponent().getContext();
                            if (context == null) {
                                LoggerHelper.e(TAG, "跳转失败 失败信息: context = null");
                                return;
                            }
                            Toast.makeText(context, context.getResources().getString
                                    (R.string.please_login_again), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("from", 1);
                            intent.putExtras(bundle);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                            break;

                        default:
                            listener.onError(response.getMsg());
                            break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e == null) {
                            return;
                        }
                        LoggerHelper.e(TAG, " onError " + e.getMessage());
                        if (e instanceof ConnectException) {
                            listener.onError(Constant.CAN_NOT_CONNECT_TO_SERVER);
                            return;
                        }
                        if (e instanceof SocketException) {
                            listener.onError(Constant.NET_WORK_ERROR);
                            return;
                        }
                        if (e instanceof SocketTimeoutException) {
                            listener.onError(Constant.time_out);
                            return;
                        }
                        listener.onError(Constant.REQUEST_FAILURE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
