package com.pms.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pms.database.SettingInfo;
import com.pms.scan.R;

public class UploadSettingActivity extends Activity {
	View localView = null;
	RadioGroup upload;
	RadioGroup localsava;
	RadioGroup repeat;
	View setting = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initUI();
	}

	private void initUI() {
		localView = LayoutInflater.from(this).inflate(
				R.layout.app_frame_layout, null);
		setting = LayoutInflater.from(this).inflate(R.layout.radiobutton, null);
		View topgroup = (View) localView.findViewById(R.id.my_top);
		((TextView) topgroup.findViewById(R.id.title)).setText("上传设置");
		LinearLayout layout = (LinearLayout) localView
				.findViewById(R.id.my_body);
		layout.setOrientation(LinearLayout.VERTICAL);
		RadioGroup ra = (RadioGroup) setting.findViewById(R.id.uploadrgoup);
		int index = Integer.parseInt(SettingInfo.getInstance(this)
				.getUploadStyle());
		//循环，分别对三选项组，进行初始值设定
		for (int i = 0; i < ra.getChildCount(); i++) {
			if (i == index) {
				((RadioButton) ra.getChildAt(i)).setSelected(true);
				((RadioButton) ra.getChildAt(i)).setChecked(true);
				break;

			}
		}
		ra = (RadioGroup) setting.findViewById(R.id.savergoup);
		index = Integer.parseInt(SettingInfo.getInstance(this).getSaveStyle());
		for (int i = 0; i < ra.getChildCount(); i++) {
			if (i == index) {
				((RadioButton) ra.getChildAt(i)).setSelected(true);
				((RadioButton) ra.getChildAt(i)).setChecked(true);
				break;
			}
		}
		ra = (RadioGroup) setting.findViewById(R.id.repeatrgoup);
		index = Integer.parseInt(SettingInfo.getInstance(this).getJudgeMode());
		for (int i = 0; i < ra.getChildCount(); i++) {
			if (i == index) {
				((RadioButton) ra.getChildAt(i)).setSelected(true);
				((RadioButton) ra.getChildAt(i)).setChecked(true);
				break;
			}
		}
		ScrollView sv = new ScrollView(this);
		sv.addView(setting);//将布局添加到ScrollView，如果超出一屏，可滚动显示
		layout.addView(sv);
		setContentView(localView);
		findViewById(R.id.login_option).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						openOptionsMenu();//弹出选项菜单
					}
				});
		ImageButton break_btn = (ImageButton) findViewById(R.id.break_btn);
		break_btn.setVisibility(1);
		break_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.addSubMenu(Menu.NONE, 1, Menu.NONE, "保存");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			// 保存
			RadioGroup ra = (RadioGroup) setting.findViewById(R.id.uploadrgoup);
			for (int i = 0; i < ra.getChildCount(); i++) {
				if (((RadioButton) ra.getChildAt(i)).isChecked()) {
					SettingInfo.getInstance(this).setUploadStyle(i + "");
					System.out.println("i===================" + i);
					break;

				}
			}
			ra = (RadioGroup) setting.findViewById(R.id.savergoup);
			for (int i = 0; i < ra.getChildCount(); i++) {
				if (((RadioButton) ra.getChildAt(i)).isChecked()) {
					SettingInfo.getInstance(this).setSaveStyle(i + "");
					break;

				}
			}
			ra = (RadioGroup) setting.findViewById(R.id.repeatrgoup);
			for (int i = 0; i < ra.getChildCount(); i++) {
				if (((RadioButton) ra.getChildAt(i)).isChecked()) {
					SettingInfo.getInstance(this).setJudegMode(i + "");
					System.out.println("i2===================" + i);
					break;

				}
			}
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
