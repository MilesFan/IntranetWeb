package com.milesfan.intranetweb;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyAppWebViewClient extends WebViewClient {
/*
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().endsWith("sie-app.com")) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
*/
    private Activity context;
    private WebView globalWebView;
    private long LastPageRefreshTime;
    public int checkIntervalInSeconds=1;
    public int autoRefreshInterval=0;

    public  MyAppWebViewClient(Activity context, Integer autoRefreshInterval){
        this.context = context;
        this.autoRefreshInterval = autoRefreshInterval;
        TimerAutoRefreshCheck timer = new TimerAutoRefreshCheck();
    }

    private class TimerAutoRefreshCheck extends TimerTask {
        Timer timer;

        public TimerAutoRefreshCheck() {
            timer = new Timer();
            timer.schedule(this,checkIntervalInSeconds * 1000,checkIntervalInSeconds * 1000); // subsequent rate
        }

        @Override
        public void run() {
            if(context == null || context.isFinishing()) {
                // Activity killed
                this.cancel();
                return;
            }

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!context.hasWindowFocus()) return;
                    if (autoRefreshInterval<=0) return;
                    if (globalWebView==null) return;
                    String currentURL = globalWebView.getUrl();
                    if (currentURL.isEmpty() || currentURL.equalsIgnoreCase(context.getString(R.string.url_index))) return;

                    long currentTime = System.currentTimeMillis();
                    if (currentTime-LastPageRefreshTime>=autoRefreshInterval*1000 && globalWebView!=null){
                        LastPageRefreshTime = System.currentTimeMillis();
                        Toast.makeText(context, R.string.autorefreshstart, Toast.LENGTH_SHORT).show();
                        globalWebView.reload();
                    };
                }
            });
        }
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);

        // this will ignore the Ssl error and will go forward to your site
        handler.proceed();
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        if (globalWebView==null) globalWebView=webView;
        LastPageRefreshTime = System.currentTimeMillis();

    }

}