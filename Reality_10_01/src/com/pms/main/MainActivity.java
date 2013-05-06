package com.pms.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {
	// ��������
	ImageView imgV_01 = null;
	ImageView imgV_02 = null;
	ImageView imgV_03 = null;
	ImageView imgV_04 = null;
	ImageView imgV_05 = null;
	ImageView imgV_06 = null;
	// �������ռ���ͼƬ·��
	final static String[] imgUrls = new String[] {
			"http://c.dryicons.com/images/icon_sets/sketchy_paper_icons/png/128x128/multi_user_comment.png",
			"http://b.dryicons.com/images/icon_sets/sketchy_paper_icons/png/128x128/home_page.png",
			"http://a.dryicons.com/images/icon_sets/sketchy_paper_icons/png/128x128/computer_monitor.png",
			"http://b.dryicons.com/images/icon_sets/sketchy_paper_icons/png/128x128/padlock_locked.png",
			"http://a.dryicons.com/images/icon_sets/sketchy_paper_icons/png/128x128/zoom_search.png",
			"http://c.dryicons.com/images/icon_sets/sketchy_paper_icons/png/128x128/favorite.png", };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ��Layout�л�ȡ�ؼ�
		imgV_01 = (ImageView) this.findViewById(R.id.imgV_01);
		imgV_02 = (ImageView) this.findViewById(R.id.imgV_02);
		imgV_03 = (ImageView) this.findViewById(R.id.imgV_03);
		imgV_04 = (ImageView) this.findViewById(R.id.imgV_04);
		imgV_05 = (ImageView) this.findViewById(R.id.imgV_05);
		imgV_06 = (ImageView) this.findViewById(R.id.imgV_06);
		ImgAsyncTask task = null;
		// ѭ���첽��ҵ����������
		for (int i = 0; i < imgUrls.length; i++) {
			String url = imgUrls[i];
			// ����AsyncTask
			switch (i) {
			case 0:
				task = new ImgAsyncTask(this, imgV_01);
				task.execute(url);
				break;
			case 1:
				task = new ImgAsyncTask(this, imgV_02);
				break;
			case 2:
				task = new ImgAsyncTask(this, imgV_03);
				break;
			case 3:
				task = new ImgAsyncTask(this, imgV_04);
				break;
			case 4:
				task = new ImgAsyncTask(this, imgV_05);
				break;
			case 5:
				task = new ImgAsyncTask(this, imgV_06);
				break;
			default:
				break;
			}
			// ִ���첽������������б�
			
		}
	}

	// �ڲ�����дAsyncTask
	class ImgAsyncTask extends AsyncTask<String, Integer, Bitmap> {
		ImageView imgV_00 = null;

		// ���캯��
		public ImgAsyncTask(Context context, ImageView imgV) {
			this.imgV_00 = imgV;
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			String imgUrl = params[0];
			URL url = null;
			HttpURLConnection httpurlconnection = null;
			InputStream inputStream = null;
			try {
				// ��������
				url = new URL(imgUrl);
				httpurlconnection = (HttpURLConnection) url.openConnection();
				inputStream = httpurlconnection.getInputStream();
				// ͨ��BitmapFactory��InputStreamת��ΪBitmap
				return BitmapFactory.decodeStream(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					// �ر���������
					if (inputStream != null)
						inputStream.close();
					if (httpurlconnection != null)
						httpurlconnection.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// ͼ����ʾ
			if (result != null)
				imgV_00.setImageBitmap(result);
		}
	}
}