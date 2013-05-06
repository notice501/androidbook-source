package com.pms.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewHtmlActivity extends Activity {
	WebView webView=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webView=(WebView)this.findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("http://www.4399.com/flash/swf.htm?gamepath=http://s7.4399.com:8080/4399swf/upload_swf/ftp/20100212wen/4.swf&gamemark=1|0|1&gamename=合金弹头-场景版");
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        // 在WebView中而不是默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}