package com.zfsoftmh.ui.modules.personal_affairs.qr_code.qr_code_scan_result;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * @author wesley
 * @date: 2017/3/21
 * @Description: 二维码扫描结果界面
 */

public class ScanQrCodeResultActivity extends BaseActivity {

    private String url; //二维码对应的网址
    private WebView webView;
    private ProgressBar progressBar;
    private TextView tv_value;  //二维码的url不是网址的时候显示
    private static final String PREFIX = "http";

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        url = bundle.getString("url");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_scan_qr_code_result;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        webView = (WebView) findViewById(R.id.scan_qr_code_result_web_view);
        progressBar = (ProgressBar) findViewById(R.id.scan_qr_code_result_progress_bar);
        tv_value = (TextView) findViewById(R.id.scan_qr_code_result_text);
        setToolbarTitle(R.string.scan_result);
        setDisplayHomeAsUpEnabled(true);

        if (url != null && url.startsWith(PREFIX)) {
            initWebView();
        } else {
            initTextView();
        }
    }

    //初始化文本
    private void initTextView() {
        webView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tv_value.setVisibility(View.VISIBLE);
        tv_value.setText(url);
    }

    //初始化网页
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        webView.setVisibility(View.VISIBLE);
        tv_value.setVisibility(View.GONE);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setToolbarTitle(title);
                setDisplayHomeAsUpEnabled(true);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }
}
