package com.pms.main;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class NetAndroidActivity extends Activity {
	TextView textView_1 = null;
	String httpUrl = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//调用AsyncTask
		NetAndroidTask task = new NetAndroidTask(this);
		//执行异步，并传入参数列表
		task.execute("http://192.168.1.7:8080/exa/index.jsp","testParam1=110","testParam2=NetAndroidActivity");

	}

	class NetAndroidTask extends AsyncTask<String, Integer, String> {
		//构造函数
		public NetAndroidTask(Context context) {
		}

		@Override
		protected String doInBackground(String... params) {
			//声明变量
			AndroidHttpClient client = null;
			HttpGet httpRequest = null;
			HttpResponse httpResponse = null;
			try {
				httpUrl = params[0]+"?"+params[1]+"&"+params[2];//通过参数列表组装路径
				// 构造AndroidHttpClient的实例
				client = AndroidHttpClient.newInstance("Android");
				// 创建 HttpGet 方法，该方法会自动处理 URL 地址的重定向
				httpRequest = new HttpGet(httpUrl);
				// 请求HttpClient，取得HttpResponse
				httpResponse = client.execute(httpRequest);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 取得返回的字符串
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					return strResult;
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭连接
				if (client != null)
					client.close();
			}

			return null;

		}
		@Override
		protected void onPostExecute(String result) {
			// 返回HTML页面的内容
			textView_1.setText(result);
		}
	}
}