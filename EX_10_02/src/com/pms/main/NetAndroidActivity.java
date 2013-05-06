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
		// �õ��ؼ�
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//����AsyncTask
		NetAndroidTask task = new NetAndroidTask(this);
		//ִ���첽������������б�
		task.execute("http://192.168.1.7:8080/exa/index.jsp","testParam1=110","testParam2=NetAndroidActivity");

	}

	class NetAndroidTask extends AsyncTask<String, Integer, String> {
		//���캯��
		public NetAndroidTask(Context context) {
		}

		@Override
		protected String doInBackground(String... params) {
			//��������
			AndroidHttpClient client = null;
			HttpGet httpRequest = null;
			HttpResponse httpResponse = null;
			try {
				httpUrl = params[0]+"?"+params[1]+"&"+params[2];//ͨ�������б���װ·��
				// ����AndroidHttpClient��ʵ��
				client = AndroidHttpClient.newInstance("Android");
				// ���� HttpGet �������÷������Զ����� URL ��ַ���ض���
				httpRequest = new HttpGet(httpUrl);
				// ����HttpClient��ȡ��HttpResponse
				httpResponse = client.execute(httpRequest);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// ȡ�÷��ص��ַ���
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					return strResult;
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// �ر�����
				if (client != null)
					client.close();
			}

			return null;

		}
		@Override
		protected void onPostExecute(String result) {
			// ����HTMLҳ�������
			textView_1.setText(result);
		}
	}
}