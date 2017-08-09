package com.zfsoftmh.ui.modules.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.base.BaseFragment;


/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: ui
 */

public class WebFragment extends BaseFragment {

    private static final String TAG = "WebFragment";
    private OnShareClickListener onShareClickListener;
    private LinearLayout ll_container; //布局容器
    private X5WebView x5WebView;
    private ProgressBar progressBar;
    private String url;

    @Override
    public void onResume() {
        super.onResume();
        if (x5WebView != null) {
            x5WebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (x5WebView != null) {
            x5WebView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (x5WebView != null) {
            x5WebView.setVisibility(View.GONE);
            x5WebView.removeAllViews();
            x5WebView.destroy();
            ll_container.removeView(x5WebView);
            x5WebView = null;
        }
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        url = bundle.getString("url");
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.fragment_web;
    }

    @Override
    protected void initViews(View view) {
        ll_container = (LinearLayout) view.findViewById(R.id.web_ll);
        progressBar = (ProgressBar) view.findViewById(R.id.web_progress_bar);
        x5WebView = (X5WebView) view.findViewById(R.id.web_view);
        initProgressBar();
        initWebView();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 实例化Fragment
     *
     * @param url   网址
     * @param title 标题
     * @return fragment
     */
    public static WebFragment newInstance(String url, String title) {
        WebFragment fragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    //初始化ProgressBar
    private void initProgressBar() {
        progressBar.setMax(100);
    }

    /* 初始化WebView
     * TBS x5内核
     * x5内核运行的辨别方式是 长按web中的页面选中文字 标签的样式是蓝色水滴状
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        x5WebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }
        });

        x5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                int progress = progressBar.getProgress();
                if (progress == progressBar.getMax()) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        x5WebView.setWebViewClient(new WebViewClient());
        x5WebView.addJavascriptInterface(api,"MobileJavaApi");

        x5WebView.addJavascriptInterface(api, "MobileJavaApi");

        x5WebView.loadUrl(url);
    }

    private MobileJavaApi api = new MobileJavaApi() {
        @JavascriptInterface
        public void Quit() {
        }

        @JavascriptInterface
        public void ShowAlert(String message) {
        }

        @JavascriptInterface
        public void GoBack() {
        }

        @JavascriptInterface
        public void ShowAlertQuit(String message) {
        }

        @JavascriptInterface
        public void PlayVideo(String uri) {
        }

        @JavascriptInterface
        public void webviewFileUpload(String uri, String lqh) {
        }

        @JavascriptInterface
        public void CallPhone(String number) {
        }
    };

    public interface MobileJavaApi {

    }


    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    /**
     * WebView是否可以返回
     *
     * @return true: 可以 false: 不可以
     */
    public boolean canGoBack() {
        return x5WebView != null && x5WebView.canGoBack();
    }

    public void goBack() {
        if (x5WebView != null && x5WebView.canGoBack()) {
            x5WebView.goBack();
        }
    }


    /**
     * 自定义回调接口 --- 点击分享
     */
    interface OnShareClickListener {
        void onShareClick();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web_share, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        case R.id.share:
            if (onShareClickListener != null) {
                onShareClickListener.onShareClick();
            }
            return true;

        default:
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
