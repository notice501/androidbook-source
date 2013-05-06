package com.pms.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TxtZhCNActivity extends Activity {
	TextView textView_1 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("研究中文乱码问题");
		// 得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		InputStream inputStream = null;
		try {
			inputStream = this.getAssets().open("test.txt");
			StringBuffer sb = new StringBuffer();
			InputStreamReader isr=new InputStreamReader(inputStream);
			char buf[] = new char[20];
			int nBufLen = isr.read(buf);
			while(nBufLen!=-1){
				sb.append(new String(buf, 0, nBufLen));
				nBufLen = isr.read(buf);
			}
			textView_1.setText(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6.关闭流和连接
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}