package com.pms.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class WebViewScriptActivity extends Activity {
	private WebView webView = null;
	private WebSettings ws = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webView = (WebView) this.findViewById(R.id.webview);
		ws = webView.getSettings();
		ws.setAllowFileAccess(true);// 设置允许访问文件数据
		ws.setJavaScriptEnabled(true);// 设置支持javascript脚本
		ws.setBuiltInZoomControls(true);// 设置支持缩放
		webView.loadUrl("file:///android_asset/test.html");
		//设置WebViewClient
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 当有新连接时，使用当前的 WebView
				view.loadUrl(url);
				return true;
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				// 构建一个Builder来显示网页中的alert对话框
				Builder builder = new Builder(WebViewScriptActivity.this);
				builder.setTitle("提示对话框");
				builder.setMessage(message);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}

						});
				builder.setCancelable(false);
				builder.create();
				builder.show();
				return true;
			}

			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {
				Builder builder = new Builder(WebViewScriptActivity.this);
				builder.setTitle("带选择的对话框");
				builder.setMessage(message);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}

						});
				builder.setNeutralButton(android.R.string.cancel,
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}

						});
				builder.setCancelable(false);
				builder.create();
				builder.show();
				return true;
			}

			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue, final JsPromptResult result) {
				LayoutInflater inflater = LayoutInflater
						.from(WebViewScriptActivity.this);
				final View v = inflater.inflate(R.layout.prom_dialog, null);
				// 设置 TextView对应网页中的提示信息
				((TextView) v.findViewById(R.id.TextView_PROM))
						.setText(message);
				// 设置EditText对应网页中的输入框
				((EditText) v.findViewById(R.id.EditText_PROM))
						.setText(defaultValue);
				Builder builder = new Builder(WebViewScriptActivity.this);
				builder.setTitle("带输入的对话框");
				builder.setView(v);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//得到Prompt中输入值
								String value = ((EditText) v
										.findViewById(R.id.EditText_PROM))
										.getText().toString();
								result.confirm(value);
							}

						});
				builder.setNegativeButton(android.R.string.cancel,
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}

						});
				builder.create();
				builder.show();
				return true;
			}

			// 设置网页加载的进度条
			public void onProgressChanged(WebView view, int newProgress) {
				WebViewScriptActivity.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}
			// 设置应用程序的标题
			public void onReceivedTitle(WebView view, String title) {
				WebViewScriptActivity.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});
	}
}