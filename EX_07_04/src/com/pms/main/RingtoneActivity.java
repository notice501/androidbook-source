package com.pms.main;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public class RingtoneActivity extends Activity {
	private static final int Ringtone = 0;
	private static final int Alarm = 1;
	private static final int Notification = 2;
	private static final String FileRingtone = Environment
			.getExternalStorageDirectory()
			+ "/music/ringtones";

	Button button1 = null;// 来电铃声设置

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(
						RingtoneManager.ACTION_RINGTONE_PICKER);
				// 设置类型为来电
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
						RingtoneManager.TYPE_RINGTONE);
				// 设置显示的标题
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电铃声");
				startActivityForResult(intent, Ringtone);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		} else {
			// 得到我们选择的铃声
			Uri uri = data
					.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
			System.out.println(uri);
			if (uri != null) {
				switch (requestCode) {
				case Ringtone:
					System.out.println(FileRingtone);
					if(isFile(FileRingtone))
					RingtoneManager.setActualDefaultRingtoneUri(this,
							RingtoneManager.TYPE_RINGTONE, uri);
					break;
				case Alarm:
					RingtoneManager.setActualDefaultRingtoneUri(this,
							RingtoneManager.TYPE_ALARM, uri);
					break;
				case Notification:
					RingtoneManager.setActualDefaultRingtoneUri(this,
							RingtoneManager.TYPE_NOTIFICATION, uri);
					break;
				default:
					break;
				}
			}

		}
	}

	/**
	 * 判断文件是否存在，不存在则创建.
	 * 
	 * @param path
	 * @return
	 */
	private boolean isFile(String path) {
		boolean b = false;
		File f = new File(path);
		if (f.exists()) {
			b = true;
		} else {
			if (f.mkdirs()) {
				b = true;
			} else {
				b = false;
			}
		}
		return b;
	}

}