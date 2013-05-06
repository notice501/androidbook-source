package com.pms.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
/**
 * Animation动画XML例子
 * @author Administrator
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button bt_alpha = null;//透明度渐变
	private Button bt_scale = null;//尺寸渐变
	private Button bt_translate = null;//位置移动
	private Button bt_rotate = null;//图片旋转
	private ImageView iv_img = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bt_alpha = (Button) this.findViewById(R.id.bt_alpha);
		bt_scale = (Button) this.findViewById(R.id.bt_scale);
		bt_translate = (Button) this.findViewById(R.id.bt_translate);
		bt_rotate = (Button) this.findViewById(R.id.bt_rotate);
		iv_img = (ImageView) this.findViewById(R.id.iv_img);
		bt_alpha.setOnClickListener(this);
		bt_scale.setOnClickListener(this);
		bt_translate.setOnClickListener(this);
		bt_rotate.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_alpha://透明度渐变事件响应
			Animation alphaAnim = AnimationUtils.loadAnimation(
					MainActivity.this, R.anim.m_alpha);
			iv_img.startAnimation(alphaAnim);
			break;
		case R.id.bt_scale://尺寸渐变事件响应
			Animation scaleAnim = AnimationUtils.loadAnimation(
					MainActivity.this, R.anim.m_scale);
			iv_img.startAnimation(scaleAnim);
			break;
		case R.id.bt_translate://位置移动事件响应
			Animation translateAnim = AnimationUtils.loadAnimation(
					MainActivity.this, R.anim.m_translate);
			iv_img.startAnimation(translateAnim);
			break;
		case R.id.bt_rotate://图片旋转事件响应
			Animation rotateAnim = AnimationUtils.loadAnimation(
					MainActivity.this, R.anim.m_rotate);
			iv_img.startAnimation(rotateAnim);
			break;

		default:
			break;
		}
	}
}