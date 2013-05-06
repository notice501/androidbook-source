package com.pms.main;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private Button bt_start = null;
	private Button bt_stop = null;
	private ImageView iv_img = null;
	private AnimationDrawable localAnimationDrawable = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获取布局
		bt_start = (Button) this.findViewById(R.id.bt_start);
		bt_stop = (Button) this.findViewById(R.id.bt_stop);
		iv_img = (ImageView) this.findViewById(R.id.iv_img);
		//将XML资源定义装载到ImageView中
		iv_img.setBackgroundResource(R.anim.m_gif);
		//构建动画
		localAnimationDrawable = (AnimationDrawable) iv_img.getBackground();
		//开始动画监听事件
		bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				localAnimationDrawable.start();
			}
		});
		//停止动画监听事件
		bt_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				localAnimationDrawable.stop();
			}
		});
	}
}