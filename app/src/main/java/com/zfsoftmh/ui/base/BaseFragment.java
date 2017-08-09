package com.zfsoftmh.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.ui.widget.loading_dialog.LoadingDialog;

import pub.devrel.easypermissions.AppSettingsDialog;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 所有Fragment的基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends BaseImmersionStatusBarFragment
        implements BaseView<T> {

    protected Context context;
    protected T presenter;
    //    protected ProgressDialog dialog;
    protected LoadingDialog dialog;
    protected AppSettingsDialog appSettingsDialog;
    protected com.gyf.barlibrary.ImmersionBar immersionBar;
    protected BaseDialogFragmentImp fragmentImp;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        handleArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater == null) {
            return null;
        }
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view == null) {
            return;
        }
        initViews(view);
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher();
        if (refWatcher != null) {
            refWatcher.watch(this);
        }

        if (presenter != null) {
            presenter.cancelRequest();
        }

        if (immersionBar != null && immersionEnabled()) {
            immersionBar.destroy();
        }
    }


    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    //处理Activity界面传递过来的参数
    private void handleArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            handleBundle(bundle);
        }
    }

    /**
     * 显示提示信息
     *
     * @param msg 要显示的信息
     */
    protected void showToastMsgShort(String msg) {
        if (!isActive()) {
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示信息
     *
     * @param msg 要显示的信息
     */
    protected void showToastMsgLong(String msg) {
        if (!isActive()) {
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示对话框
     *
     * @param msg 对话框要显示的信息
     */
    @Override
    public void showProgressDialog(String msg) {
        if (!isActive()) {
            return;
        }
//        dialog = new ProgressDialog(context);
//        dialog.setMessage(msg);
//        dialog.show();
        //dialog = LoadingDialog.createDialog(context, R.drawable.loading_dialog);
        // 触摸外部无法取消,必须
//        dialog.setCanceledOnTouchOutside(false);
       // dialog.setMessage(msg);
       // dialog.show();

        fragmentImp = BaseDialogFragmentImp.newInstance(msg);
        fragmentImp.show(getChildFragmentManager(), "fragmentImp");
    }

    /**
     * 对话框消失
     */
    @Override
    public void hideProgressDialog() {
        if (!isActive()) {
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if(fragmentImp != null){
            fragmentImp.dismiss();
        }
    }

    /**
     * 显示appSettingsDialog对话框
     */
    protected void showAppSettingDialog() {
        if (!isActive()) {
            return;
        }
        if (appSettingsDialog == null) {
            appSettingsDialog = new AppSettingsDialog
                    .Builder(this)
                    .setTitle(R.string.request_permissions)
                    .setRationale(R.string.permissions_rationale)
                    .setPositiveButton(R.string.Ok)
                    .setNegativeButton(R.string.cancel)
                    .build();
        }
        appSettingsDialog.show();
    }

    /**
     * 判断Fragment是否还依附在Activity上面
     *
     * @return true:还依附在Activity上面 false:Fragment已经从Activity上面移除
     */
    @Override
    public boolean isActive() {
        return isAdded();
    }

    /**
     * 处理Activity的跳转---不带任何的数据
     *
     * @param cls 目标类
     */
    protected void startActivity(Class<?> cls) {
        if (!isActive()) {
            return;
        }
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    /**
     * 处理Activity的跳转---有返回值
     *
     * @param cls         目标类
     * @param requestCode 请求码
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        if (!isActive()) {
            return;
        }
        Intent intent = new Intent(context, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 处理Activity的跳转---可能携带数据---如果携带数据,数据要包装在Bundle中
     *
     * @param intent 组件之间的纽带
     */
    protected void openActivity(Intent intent) {
        if (!isActive()) {
            return;
        }
        startActivity(intent);
    }

    /**
     * 处理Activity的跳转---有返回值
     *
     * @param intent      组件之间的纽带
     * @param requestCode 请求码
     */
    protected void openActivityForResult(Intent intent, int requestCode) {
        if (!isActive()) {
            return;
        }
        startActivityForResult(intent, requestCode);
    }

    //设置SwipeRefreshLayout的颜色
    protected void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
    }


    /**
     * 获取全局的组件
     *
     * @return AppComponent的实例
     */
    protected AppComponent getAppComponent() {
        return BaseApplication.getAppComponent();
    }

    /**
     * 获取用户登录的id
     *
     * @return 用户id
     */
    protected String getUserId() {
        return DbHelper.getUserId(context);
    }


    @Override
    protected void immersionInit() {
        if (immersionEnabled()) {
            immersionBar = com.gyf.barlibrary.ImmersionBar.with(this);
            immersionBar
                    .navigationBarWithKitkatEnable(false)
                    .init();
        }
    }

    /**
     * 初始化变量---如list adapter FragmentManager
     */
    protected abstract void initVariables();

    /**
     * 处理Bundle
     */
    protected abstract void handleBundle(Bundle bundle);

    /**
     * 获取布局文件
     *
     * @return 布局文件的id
     */
    protected abstract int getLayoutResID();

    /**
     * 初始化控件
     *
     * @param view 布局文件的跟布局
     */
    protected abstract void initViews(View view);

    /**
     * 初始化点击事件
     */
    protected abstract void initListener();

}
