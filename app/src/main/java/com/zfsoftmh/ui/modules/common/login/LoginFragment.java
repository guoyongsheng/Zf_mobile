package com.zfsoftmh.ui.modules.common.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ImageLoaderOptions;
import com.zfsoftmh.entity.IsLogin;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.common.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: ui展示
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View, View.OnClickListener, TextWatcher {

    private static final int MAX_ID_COUNT = 3;
    private AutoCompleteTextView et_user_name; //用户名
    private EditText et_user_password; //密码
    private Button btn_login; //登录
    private CircleImageView circleImageView; //头像
    private boolean isLoading; //是否正在登录
    private int from; // 0: 用户没有登录  1: token_error 2:退出登录

    private FrameLayout frame_login_with_qq; //qq登录
    private FrameLayout frame_login_with_weixin; // 微信登录
    private FrameLayout frame_login_with_sina; //新浪登录

    private OnViewClickListener listener;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        from = bundle.getInt("from");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViews(View view) {
        et_user_name = (AutoCompleteTextView) view.findViewById(R.id.login_et_user_name);
        et_user_password = (EditText) view.findViewById(R.id.login_et_user_password);
        circleImageView = (CircleImageView) view.findViewById(R.id.login_user_icon);
        btn_login = (Button) view.findViewById(R.id.login_btn_login);

        frame_login_with_qq = (FrameLayout) view.findViewById(R.id.login_with_qq);
        frame_login_with_weixin = (FrameLayout) view.findViewById(R.id.login_with_weixin);
        frame_login_with_sina = (FrameLayout) view.findViewById(R.id.login_with_sina);

        initIdAndHeaderPath(getUserInfo(), 0);

        et_user_name.setThreshold(1);
        et_user_name.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, getUserIdList()));
    }

    @Override
    protected void initListener() {
        btn_login.setOnClickListener(this);
        frame_login_with_qq.setOnClickListener(this);
        frame_login_with_weixin.setOnClickListener(this);
        frame_login_with_sina.setOnClickListener(this);
        et_user_name.addTextChangedListener(this);
    }

    public static LoginFragment newInstance(int from) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {

        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 登录
         */
        case R.id.login_btn_login:
            login();
            break;

        /*
         * qq登录
         */
        case R.id.login_with_qq:
            if (listener == null) {
                return;
            }
            listener.onQQClick();
            break;

        /*
         * 微信登录
         */
        case R.id.login_with_weixin:
            if (listener == null) {
                return;
            }
            listener.onWeiXinClick();
            break;

        /*
         * 新浪登录
         */
        case R.id.login_with_sina:
            if (listener == null) {
                return;
            }
            listener.onSinaClick();
            break;

        default:
            break;
        }
    }

    @Override
    public User getUserInfo() {
        return DbHelper.getUserInfo(context);
    }

    @Override
    public List<String> getUserIdList() {
        List<User> list = queryAllUser();
        if (list == null) {
            return null;
        }

        List<String> listId = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            listId.add(list.get(i).getUserId());
        }
        return listId;
    }

    @Override
    public void copyToRealmOrUpdate(User user) {
        List<User> list = queryAllUser();
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add(user);
            getAppComponent().getRealmHelper().insertOrUpdateUserInfo(list);
            return;
        }

        if (checkUserIsInList(user, list)) {
            return;
        }

        list.add(0, user);
        if (list.size() > MAX_ID_COUNT) {
            list.remove(list.size() - 1);
        }
        getAppComponent().getRealmHelper().insertOrUpdateUserInfo(list);
    }

    @Override
    public boolean checkUserIsInList(User user, List<User> list) {
        if (user == null || list == null) {
            return false;
        }

        String userId = user.getUserId();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (userId != null && userId.equals(list.get(i).getUserId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> queryAllUser() {
        return getAppComponent().getRealmHelper().queryAllUser();
    }

    @Override
    public void initIdAndHeaderPath(User user, int type) {
        String headerPath = null;
        if (user != null) {
            headerPath = user.getHeadPicturePath();
            if (type == 0) {
                et_user_name.setText(user.getUserId());
            }
        }
        ImageLoaderOptions options = new ImageLoaderOptions.Builder()
                .error(R.mipmap.iv_icon_user_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .build();
        ImageLoaderHelper.loadImage(context, circleImageView, headerPath, options);
    }

    @Override
    public String getUserName() {
        return et_user_name.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_user_password.getText().toString();
    }

    @Override
    public void login() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        presenter.login(getUserName(), getPassword(), Config.STRkEY);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        isLoading = false;
        showToastMsgShort(errorMsg);
    }

    @Override
    public void loginSuccess(User user) {
        isLoading = false;
        DbHelper.saveValueBySharedPreferences(context, Config.DB.USER_NAME, Config.DB.USER_KEY, user);
        setUserIsLogin();
        copyToRealmOrUpdate(user);

        switch (from) {
        /*
         * 用户点击了我的门户、应用中心时没有登录的情况---重新登录和退出登录
         */
        case 0:
        case 1:
        case 2:
            Intent intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            openActivity(intent);
            break;

        default:
            break;
        }
        ((LoginActivity) context).finish();
    }

    //记住用户登录的状态
    private void setUserIsLogin() {
        IsLogin isLogin = new IsLogin();
        isLogin.setLogin(true);
        DbHelper.saveValueBySharedPreferences(context, Config.DB.IS_LOGIN_NAME, Config.DB.IS_LOGIN_KEY, isLogin);
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null) {
            return;
        }
        String userId = s.toString();
        User user = getAppComponent().getRealmHelper().queryUserInfoById(userId);
        initIdAndHeaderPath(user, 1);
    }

    /**
     * 自定义回调接口
     */
    interface OnViewClickListener {


        void onQQClick();


        void onWeiXinClick();


        void onSinaClick();
    }
}
