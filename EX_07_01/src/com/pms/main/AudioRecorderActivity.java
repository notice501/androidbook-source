package com.pms.main;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AudioRecorderActivity extends Activity {
	Button bt_start = null;// 开始录制
	Button bt_stop = null;// 停止录制
	TextView tv_info = null;// 用于显示录制时间
	MediaRecorder recorder = null;// 录音
	Handler handler = null;// 布局UI处理器
	boolean isRecorder = false;
	static int recorderSecond = 0;// 录制时间(秒)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audiorecorder);
		setTitle("录制音频文件");
		// 得到布局文件布局对象
		bt_start = (Button) this.findViewById(R.id.bt_start);
		bt_stop = (Button) this.findViewById(R.id.bt_stop);
		tv_info = (TextView) this.findViewById(R.id.tv_info);
		// 开始录制音频
		bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					if (!Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						showTasot("SD卡不存在");
						return;
					}
					String sdCardPath = Environment
							.getExternalStorageDirectory().getPath();
					String path = sdCardPath + "/audio.amr";
					recorder = new MediaRecorder();////初始化Recorder
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);////设置麦克风
					recorder
							.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置输出格式
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频编码Encoder
					recorder.setOutputFile(path);//设置音频文件保存路径
					recorder.prepare();//准备录制
					recorder.start();//开始录制
					isRecorder = true;
					recorderSecond=0;
					bt_start.setText("录音中...");
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// 停止录制音频
		bt_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				bt_start.setText("开始录音");
				recorderSecond=0;
				isRecorder = false;
				//停止录音和释放资源
				recorder.stop();
				recorder.release();
			}
		});
		// 更新UI
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (isRecorder) {
						tv_info.setText("录音：" + recorderSecond + " 秒");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						recorderSecond++;
						handler.sendEmptyMessage(1);
					}
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

	/**
	 * Toast提示
	 * 
	 * @param text
	 */
	public void showTasot(String text) {
		Toast.makeText(AudioRecorderActivity.this, text, Toast.LENGTH_LONG)
				.show();
	}

}