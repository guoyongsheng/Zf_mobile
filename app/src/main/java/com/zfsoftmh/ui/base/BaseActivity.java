package com.zfsoftmh.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.IMMLeaksUtils;
import com.zfsoftmh.common.utils.ImmersionStatusBarUtils;
import com.zfsoftmh.common.utils.KeyboardUtils;
import com.zfsoftmh.di.AppComponent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AppSettingsDialog;


/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 所有activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialog dialog; //对话框
    protected Toolbar toolbar;
    private AppSettingsDialog appSettingsDialog;
    protected com.gyf.barlibrary.ImmersionBar immersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        isTouchToHideKeyboard = false;
        defToolBar = true;
        setRequestedOrientation();
        fixFocusedViewLeak();
        setContentView(getLayoutResID());
        initToolbar();
        setStatusBar();
        initVariables();
        handleIntent();
        initViews(savedInstanceState);
        setUpInject();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftInput();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher();
        if (refWatcher != null) {
            refWatcher.watch(this);
        }
        destroyImmrsionBar();
    }

    //销毁
    private void destroyImmrsionBar() {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                if (immersionBar != null && immersionEnabled()) {
                    immersionBar.destroy();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void hideDefaultToolBar() {
        defToolBar = false;
    }

    /**
     * 设置状态栏的颜色
     */
    protected void setStatusBar() {
        if (immersionEnabled()) {
            ImmersionStatusBarUtils.initStatusBarInActivity(this, R.color.colorPrimary,
                    R.color.colorPrimary, toolbar, true, 0.2f, keyBoardEnabled(), immersionBar);
        }
    }


    protected boolean keyBoardEnabled() {
        return false;
    }

    public boolean defToolBar;//默认的ToolBar

    //初始化Toolbar
    private void initToolbar() {
        if (defToolBar) {
            toolbar = (Toolbar) findViewById(R.id.too_bar_id);
        }
    }

    /**
     * 设置toolbar的标题
     *
     * @param title 标题
     */
    protected void setToolbarTitle(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 设置toolbar的标题
     *
     * @param resId 资源id
     */
    protected void setToolbarTitle(int resId) {
        if (toolbar != null) {
            toolbar.setTitle(resId);
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 设置toolbar的返回箭头是否显示
     *
     * @param enabled true:显示  false:不显示
     */
    protected void setDisplayHomeAsUpEnabled(boolean enabled) {
        if (toolbar != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(enabled);
                if (enabled) {
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onNavigationIconClick();
                        }
                    });
                }
            }
        }
    }

    /**
     * 自定义导航图标
     *
     * @param resId 图片的资源id
     */
    protected void setNavigationIcon(int resId) {
        if (toolbar != null) {
            toolbar.setNavigationIcon(resId);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationIconClick();
                }
            });
        }
    }

    /**
     * 自定义导航栏图标
     *
     * @param drawable drawable对象
     */
    protected void setNavigationIcon(Drawable drawable) {
        if (toolbar != null) {
            toolbar.setNavigationIcon(drawable);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationIconClick();
                }
            });
        }
    }

    /**
     * 空实现---子类重写这个方法就可以了
     */
    protected void onNavigationIconClick() {

    }


    //隐藏软键盘
    private void hideSoftInput() {
        KeyboardUtils.hideSoftInput(this);
    }

    //跳转界面时判读Intent是否携带数据
    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                handleBundle(bundle);
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @param msg 要显示的信息
     */
    protected void showToastMsgShort(String msg) {
        if (isFinishing()) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示信息
     *
     * @param msg 要显示的信息
     */
    protected void showToastMsgLong(String msg) {
        if (isFinishing()) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示对话框
     *
     * @param msg 对话框要显示的信息
     */
    protected void showProgressDialog(String msg) {
        dialog = new ProgressDialog(this);
        dialog.setMessage(msg);
        dialog.show();
    }

    /**
     * 对话框消失
     */
    protected void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 显示appSettingsDialog对话框
     */
    protected void showAppSettingDialog() {
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
     * 处理Activity的跳转---不带任何的数据
     *
     * @param cls 目标类
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 处理Activity的跳转---有返回值
     *
     * @param cls         目标类
     * @param requestCode 请求码
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 处理Activity的跳转---可能携带数据---如果携带数据,数据要包装在Bundle中
     *
     * @param intent 组件之间的纽带
     */
    protected void openActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * 处理Activity的跳转---有返回值
     *
     * @param intent      组件之间的纽带
     * @param requestCode 请求码
     */
    protected void openActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }


    //设置沉浸式状态栏
    private void setImmersiveStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                View decorView = window.getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.setStatusBarColor(Color.blue(R.color.colorPrimary));
            }
        }
    }

    //初始化沉浸式状态栏
    protected void initStatusBar() {
        if (immersionEnabled()) {
            immersionBar = ImmersionBar.with(this);
            immersionBar
                    .navigationBarWithKitkatEnable(false)
                    .init();

        }
    }

    //设置屏幕方向为竖屏
    private void setRequestedOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    //修复InputMethodManager内存泄漏
    private void fixFocusedViewLeak() {
        IMMLeaksUtils.fixFocusedViewLeak(getApplication());
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
     * 是否显示沉浸式状态栏
     *
     * @return true: 显示
     */
    protected boolean immersionEnabled() {
        return true;
    }

    /**
     * 初始化变量---如:list adapter
     */
    protected abstract void initVariables();


    /**
     * 处理上个界面传过来的数据---所有的Intent跳转的数据都需要包装在Bundle中
     *
     * @param bundle 界面跳转时传递的数据
     */
    protected abstract void handleBundle(@NonNull Bundle bundle);

    /**
     * 获取布局文件
     *
     * @return 布局文件id
     */
    protected abstract int getLayoutResID();

    /**
     * 初始化控件
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 注入
     */
    protected abstract void setUpInject();

    /**
     * 初始化点击事件
     */
    protected abstract void initListener();


    /**
     * 是否需要点击空白处收回软键盘
     */
    private boolean isTouchToHideKeyboard;

    public void enableTouchToHideKeyboard() {
        isTouchToHideKeyboard = true;
    }

    /**
     * create by sy
     * <p>点击空白处收回软键盘</p>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isTouchToHideKeyboard) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // 获得当前得到焦点的View
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    hideSoftInput(v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
