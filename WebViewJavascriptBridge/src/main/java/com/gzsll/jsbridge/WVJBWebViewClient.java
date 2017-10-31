package com.gzsll.jsbridge;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sll on 2016/3/1.
 */
public class WVJBWebViewClient extends WebViewClient {

    private WVJBWebView mWVJBWebView;


    public WVJBWebViewClient(WVJBWebView wvjbWebView) {
        mWVJBWebView = wvjbWebView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Logger.d("UnkownMessage:" + url);
        Logger.d("UnkownMessage:" + url.startsWith(WVJBConstants.SCHEME));
        if (url.startsWith(WVJBConstants.SCHEME)) {
            if (url.indexOf(WVJBConstants.BRIDGE_LOADED) > 0) {
                mWVJBWebView.injectJavascriptFile();
            } else if (url.indexOf(WVJBConstants.MESSAGE) > 0) {
                mWVJBWebView.flushMessageQueue();
            } else {
                Logger.d("UnkownMessage:" + url);
            }
            return true;
        } else {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mWVJBWebView.injectJavascriptFile();
    }


}