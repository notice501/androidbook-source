package com.pms.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {
	Button bt_start = null;// 开始播放
	VideoView videoView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoview);
		setTitle("VideoView视频播放");
		// 得到布局文件布局对象
		bt_start = (Button) this.findViewById(R.id.bt_start);
		videoView = (VideoView) this.findViewById(R.id.videoView);
		// 开始播放
		bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					String sdCardPath = Environment
							.getExternalStorageDirectory().getPath();
					String path = sdCardPath + "/video.mp4";
					// 设置路径
					videoView.setVideoPath(path);
					// 设置播放器的控制条
					videoView.setMediaController(new MediaController(
							VideoViewActivity.this){
								@Override
								public void hide() {
									//使控制条不自动隐藏
									this.show();
								}
						
					});
					// 开始播放
					videoView.start();
					bt_start.setText("播放视频中...");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}