package com.pms.main;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class AudioPlayerActivity extends Activity {
	Button bt_start = null;//开始播放
	Button bt_end = null;//停止播放
	Button bt_pause = null;//暂停播放
	Button bt_continue = null;//继续播放
	MediaPlayer mediaPlayer = null;//MediaPlayer对象
	ProgressBar progress_horizontal = null;//水平进度条对象
	Handler handler = null;//布局处理器
	boolean isPlaying = true;//是否播放 true播放状态、fasle停止状态

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audioplayer);
		setTitle("播放MP3");
		//得到布局文件布局对象（四个Button和ProgressBar）
		bt_start = (Button) this.findViewById(R.id.bt_start);
		bt_end = (Button) this.findViewById(R.id.bt_end);
		bt_pause = (Button) this.findViewById(R.id.bt_pause);
		bt_continue = (Button) this.findViewById(R.id.bt_continue);
		progress_horizontal = (ProgressBar) this
				.findViewById(R.id.progress_horizontal);
		//初始化设置水平进度条不可见
		progress_horizontal.setVisibility(View.INVISIBLE);
		//开始播放
		bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					//创建MediaPlayer，Mp3资源从资源库中获取
					mediaPlayer = MediaPlayer.create(AudioPlayerActivity.this,
							R.raw.video);
					//设置水平进度条可见
					progress_horizontal.setVisibility(View.VISIBLE);
					//设置进度条最大进度
					progress_horizontal.setMax(mediaPlayer.getDuration());
					//设置当前进度
					progress_horizontal.setProgress(-1);
					//发消息给Handler处理UI
					handler.sendEmptyMessage(1);
					//开始播放
					mediaPlayer.start();
					//设置当前为播放状态
					isPlaying = true;
					//监听mp3播放完毕
					mediaPlayer
							.setOnCompletionListener(new OnCompletionListener() {
								@Override
								public void onCompletion(MediaPlayer arg0) {
									isPlaying = false;//设置为不播放状态
									mediaPlayer.release();//播放完毕释放资源
								}
							});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//停止播放
		bt_end.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//判断mediaPlayer不为null且在播放状态
				if (mediaPlayer != null && mediaPlayer.isPlaying()) {
					//停止播放
					mediaPlayer.stop();
					isPlaying = false;
				}
			}
		});
		//暂停播放
		bt_pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mediaPlayer != null && mediaPlayer.isPlaying()) {
					//暂停播放
					mediaPlayer.pause();
				}
			}
		});
		//继续播放（针对于暂停播放而言）
		bt_continue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mediaPlayer != null){
					//由暂停状态的重新播放
					mediaPlayer.start();
				}
			}
		});
		//更新UI
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (mediaPlayer != null && mediaPlayer.isPlaying()) {
						//设置当前播放位置
						progress_horizontal.setProgress(mediaPlayer.getCurrentPosition());
					}
					try {
						Thread.sleep(2000);//睡眠2秒钟
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (isPlaying){
						//发送堆栈消息与case 1循环更新UI
						handler.sendEmptyMessage(1);
					}else{
						//如果处于停止状态则进度条进度归0
						progress_horizontal.setProgress(-1);
					}
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	}
}