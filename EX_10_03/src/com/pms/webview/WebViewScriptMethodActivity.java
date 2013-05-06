package com.pms.webview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewScriptMethodActivity extends Activity {
	public WebView webView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webView = (WebView) this.findViewById(R.id.webview);
		// 设置支持javascript脚本
		webView.getSettings().setJavaScriptEnabled(true);
		// 添加自定义JavaScript脚本
		webView.addJavascriptInterface(new PmsJavaScript(this), "pms");
		//加载页面
		webView.loadUrl("file:///android_asset/test2.html");
	}

	class PmsJavaScript {
		private WebViewScriptMethodActivity webViewScriptMethodActivity;
		public PmsJavaScript(
				WebViewScriptMethodActivity webViewScriptMethodActivity) {
			this.webViewScriptMethodActivity = webViewScriptMethodActivity;
		}
		/**
		 * 页面调用该方法，该方法再调用页面JavaScript函数，处理页面
		 */
		public void showAndroidJson() {
			webViewScriptMethodActivity.webView
					.loadUrl("javascript:insertTableData('" + query() + "')");
		}
		/**
		 * 封装数据json
		 * 
		 * @return
		 */
		public String query() {
			try {
				//组织Json对象，方便页面处理
				JSONObject o1 = new JSONObject();
				o1.put("id", 4);
				o1.put("name", "环环");
				o1.put("age", 23);
				o1.put("address", "中国深圳");
				JSONObject o2 = new JSONObject();
				o2.put("id", 5);
				o2.put("name", "小张");
				o2.put("age", 23);
				o2.put("address", "中国深圳");
				JSONArray arr = new JSONArray();
				arr.put(o1);
				arr.put(o2);
				return arr.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}