package com.pms.main;

import java.io.File;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VideoRecorderActivity extends Activity {
	private Button bt_start = null;// 开始录制
	private Button bt_stop = null;// 停止录制
	private TextView tv_info = null;// 用于显示录制时间
	private MediaRecorder recorder = null;// 录音
	private SurfaceView mSurfaceView=null;//绘图容器
	private SurfaceHolder mSurfaceHolder=null;//绘图画布处理
	private Handler handler = null;// 布局UI处理器
	boolean isRecorder = false;
	static int recorderSecond = 0;// 录制时间(秒)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videorecorder);
		setTitle("录制视频文件");
		// 得到布局文件布局对象
		bt_start = (Button) this.findViewById(R.id.bt_start);
		bt_stop = (Button) this.findViewById(R.id.bt_stop);
		tv_info = (TextView) this.findViewById(R.id.tv_info);
		mSurfaceView = (SurfaceView) findViewById(R.id.videoSurfaceView);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

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
					String path = sdCardPath + "/audio.3gp";
					File file = new File(path);
					if (!file.exists()) {
						file.createNewFile();
					}
					recorder = new MediaRecorder();// //初始化Recorder
					recorder.setPreviewDisplay(mSurfaceHolder.getSurface());//预览    
					recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// //设置麦克风
					recorder
							.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);// 设置输出格式
					recorder.setVideoSize(176, 144);
					recorder.setVideoFrameRate(15); // 视频帧频率
					recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263); // 设置视频编码
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置音频编码Encoder
					recorder.setOutputFile(path);// 设置音频文件保存路径
					recorder.prepare();// 准备录制
					recorder.start();// 开始录制
					isRecorder = true;
					recorderSecond = 0;
					bt_start.setText("录制视频中...");
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
				bt_start.setText("开始录制视频");
				recorderSecond = 0;
				isRecorder = false;
				// 停止录音和释放资源
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
						tv_info.setText("录制视频：" + recorderSecond + " 秒");
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
		Toast.makeText(VideoRecorderActivity.this, text, Toast.LENGTH_LONG)
				.show();
	}
}