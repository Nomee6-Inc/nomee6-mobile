package com.torbacihuso;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "";

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OneSignal.initWithContext(this);
        OneSignal.setAppId("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn();
        load();

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }

    public void conn(){
        webView = findViewById(R.id.web);
    }

    public void load(){

        webView.loadUrl("file:///android_asset/index.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setUserAgentString("Nomee6 Client " + webView.getSettings().getUserAgentString());
        android.webkit.CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        android.webkit.CookieManager.getInstance().setAcceptCookie(true);
        android.webkit.CookieManager.getInstance().acceptCookie();
        android.webkit.CookieManager.getInstance().setAcceptFileSchemeCookies(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}