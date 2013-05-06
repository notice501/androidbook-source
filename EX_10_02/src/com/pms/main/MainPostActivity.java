package com.pms.main;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainPostActivity extends Activity {
	TextView textView_1 = null;
	String httpUrl = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// �õ��ؼ�
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		// ��������
		HttpClient httpClient = null;
		HttpPost httpRequest = null;
		HttpResponse httpResponse = null;
		try {
			httpUrl = "http://192.168.1.7:8080/exa/index.jsp";
			// ȡ��Ĭ�ϵ�HttpClient
			httpClient = new DefaultHttpClient();
			// HttpPost���Ӷ���
			httpRequest = new HttpPost(httpUrl);
			// ʹ��NameValuePair������Ҫ���ݵ�Post����
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// ���Ҫ���ݵĲ���
			params.add(new BasicNameValuePair("testParam1", "110"));
			// �����ַ���
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
			// ����httpRequest
			httpRequest.setEntity(httpentity);
			// ȡ��HttpResponse
			httpResponse = httpClient.execute(httpRequest);
			// HttpStatus.SC_OK��ʾ���ӳɹ�
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ȡ�÷��ص��ַ���
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				textView_1.setText(strResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}