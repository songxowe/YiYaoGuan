package com.example.yiyao;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = new WebView(this);

        // 设置允许执行 JS
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置放大/缩小控制
        webView.getSettings().setBuiltInZoomControls(true);
        Intent intent = this.getIntent();
        String url = (String) intent.getSerializableExtra("webView");
        // 加载云端网页
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用当前WebView处理跳转
                view.loadUrl(url);
                // true表示此事件在此处被处理，不需要再广播
                return true;
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // 转向错误时的处理
                Toast.makeText(WebViewActivity.this,
                        "Oh no! " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(webView);
    }

    /**
     * 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断按键是回退键 且WebView有历史记录
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 在WebView的历史记录中回退一次
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
