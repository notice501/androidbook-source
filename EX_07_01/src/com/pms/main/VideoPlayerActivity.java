package com.pms.main;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class VideoPlayerActivity extends Activity {
	// 声明变量
	private Button bt_start_video = null;// 开始播放
	private Button bt_stop_video = null;// 结束播放
	private SeekBar skb_video = null;// 进度条
	private SurfaceView surfaceView;// 视频绘图容器
	private SurfaceHolder surfaceHolder;// 和MediaPlayer配合播放视频
	private MediaPlayer mediaPlayer = null;
	private Timer mTimer;// 计时器
	private TimerTask mTimerTask;

	private boolean isChanging = false;// 防止定时器与SeekBar拖动时进度冲突

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoplayer);
		setTitle("播放视频");
		mediaPlayer = new MediaPlayer();
		// 播放结束之后弹出提示
		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer arg0) {
						mediaPlayer.release();
					}
				});
		// 定时器记录播放进度并更新进度条UI
		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				if (isChanging == true)
					return;
				skb_video.setProgress(mediaPlayer.getCurrentPosition());
			}
		};
		mTimer.schedule(mTimerTask, 0, 10);// 执行定时TimerTask
		// 获取布局
		bt_start_video = (Button) this.findViewById(R.id.bt_start_video);
		bt_stop_video = (Button) this.findViewById(R.id.bt_stop_video);
		skb_video = (SeekBar) this.findViewById(R.id.skb_video);
		// 设置开始和结束播放的监听事件
		bt_start_video.setOnClickListener(new OnClickListenerEvent());
		bt_stop_video.setOnClickListener(new OnClickListenerEvent());
		// 设置进度条监听
		skb_video.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		surfaceView = (SurfaceView) findViewById(R.id.SurfaceView01);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.setFixedSize(100, 100);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/*
	 * 按键事件处理
	 */
	class OnClickListenerEvent implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			mediaPlayer.reset();// 恢复到未初始化的状态
			mediaPlayer = MediaPlayer.create(VideoPlayerActivity.this, R.raw.video);// 读取视频
			skb_video.setMax(mediaPlayer.getDuration());// 设置SeekBar的长度
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDisplay(surfaceHolder);// 设置屏幕
			try {
//				mediaPlayer.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mediaPlayer.start();
		}
	}

	/*
	 * SeekBar进度改变事件
	 */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

		}
		// 进度条拖动
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			isChanging = true;
		}
		// 进度条停止拖动
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			mediaPlayer.seekTo(seekBar.getProgress());
			isChanging = false;
		}
	}
}