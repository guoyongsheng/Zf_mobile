package com.zfsoftmh.common.login;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.Response;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.CommonApi;
import com.zfsoftmh.ui.modules.common.login.LoginContract;
import com.zfsoftmh.ui.modules.common.login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 单元测试就是测试某个类的某个方法,分为两种情况
 * (1) 这个方法有返回值 ：用到了Junit框架
 * (2) 这个方法没有返回值 ：验证这个方法得到了调用 比如我要测试LoginPresenter类中的login()方法,那我就要验证HttpHelper类的request()方法得到了调用, 用到了Mockito框架
 * Presenter 层的单元测试 因为里面的代码全部都是Java所以用到的框架有Junit和Mockito
 * final 类型 和 匿名类都不能被mock
 *
 * @author wesley
 * @date: 2017/3/16
 * @Description: 测试登录界面的Presenter 使用junit mockito ----全部都是java代码 没有android的
 */

public class LoginPresenterTest {

    private LoginPresenter loginPresenter;

    @Mock
    private LoginContract.View view;

    @Mock
    private CommonApi commonApi;

    @Mock
    private HttpManager httpManager;

    @Captor
    private ArgumentCaptor<CallBackListener<User>> captor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginPresenter = new LoginPresenter(view, commonApi, httpManager);
    }

    @Test
    public void testDataIsEmpty() {
        assertTrue(loginPresenter.checkDataIsEmpty(""));
        assertTrue("is not null", loginPresenter.checkDataIsEmpty(null));
    }

    @Test
    public void testLogin() {
        Response<User> response = new Response<>();
        Observable<Response<User>> observable = Observable.just(response);
        when(commonApi.login(anyString(), anyString(), anyString())).thenReturn(observable);
        loginPresenter.login("wesley", "111", "222");
        verify(view).showProgressDialog(anyString());
        verify(httpManager).request(eq(observable), any(CompositeDisposable.class), eq(view), captor.capture());
        captor.getValue().onError(response.getMsg());
        verify(view).hideProgressDialog();
        verify(view).showErrorMsg(null);
    }
}
