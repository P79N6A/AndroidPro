package com.ogmallpad.ui.fragment;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.databinding.FgBasewebviewLayoutBinding;
import com.ogmallpad.ui.BaseFragment;

/**
 * 浏览器
 * Created by chenjunshan on 2018-05-16.
 */

public class BasewebviewFragment extends BaseFragment<FgBasewebviewLayoutBinding, Object> {
    private String url;

    @Override
    public int bindLayout() {
        return R.layout.fg_basewebview_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        url = getActivity().getIntent().getStringExtra(Constants.URL);
        initWebView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.webView.destroy();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.llClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_close:
                getActivity().finish();
                break;
        }
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return null;
            }
        });
        WebSettings webSettings = mBinding.webView.getSettings();
        // step 4: bind javascript
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        mBinding.webView.removeJavascriptInterface("searchBoxJavaBridge_");
        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mBinding.webView.loadUrl(url);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.webView.onPause();
        mBinding.webView.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.webView.resumeTimers();
        mBinding.webView.onResume();
    }
}
