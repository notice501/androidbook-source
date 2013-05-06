package com.pms.main;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainGetActivity extends Activity {
	TextView textView_1 = null;
	String httpUrl = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//�õ��ؼ�
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//��������
		HttpClient httpClient = null;
		HttpGet httpRequest = null;
		HttpResponse httpResponse = null;
		try {
			httpUrl = "http://192.168.1.7:8080/exa/index.jsp?testParam1=110";
			// ����HttpClient��ʵ��
			httpClient = new DefaultHttpClient();
			// HttpGet���Ӷ���
			httpRequest = new HttpGet(httpUrl);
			// ����HttpClient��ȡ��HttpResponse
			httpResponse = httpClient.execute(httpRequest);
			// ����ɹ�
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ȡ�÷��ص��ַ���
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				//���õ�ҳ��ؼ���
				textView_1.setText(strResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}