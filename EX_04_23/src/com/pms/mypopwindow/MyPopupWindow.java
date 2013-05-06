package com.pms.mypopwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

public class MyPopupWindow extends Activity {
	 private GridView gv;
	 private Button bt1;
	 private Button bt2;
	 private Button bt3;
	 private Button bt4;
	 private Button bt5;
	 private int[] icons={R.drawable.menu_search,
				R.drawable.menu_filemanager, R.drawable.menu_downmanager,
				R.drawable.menu_fullscreen, R.drawable.menu_inputurl,
				R.drawable.menu_bookmark, R.drawable.menu_bookmark_sync_import,
				R.drawable.menu_sharepage, R.drawable.menu_quit,
				R.drawable.menu_nightmode, R.drawable.menu_refresh,
				R.drawable.menu_more };
	 private String[] items={"搜索", "文件管理", "下载管理", "全屏", "网址", "书签",
				"加入书签", "分享页面", "退出", "夜间模式", "刷新", "更多"};
	 private PopupWindow mPop;
	 private View layout;
	 
	 /*初始化一个弹窗*/
	 private void initPopWindow() {
		if (mPop == null) {
			mPop = new PopupWindow(layout,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		if (mPop.isShowing()) {
			mPop.dismiss();
		}
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*得到Button对象*/
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);
        bt4 = (Button)findViewById(R.id.bt4);
        bt5 = (Button)findViewById(R.id.bt5);
        /*导入布局*/
        layout = View.inflate(this, R.layout.window, null);
        /*得到GridView对象，并且绑定适配器*/
        gv = (GridView) layout.findViewById(R.id.gridview);
        MyAdapter adapter=new MyAdapter(this,items,icons);
        gv.setAdapter(adapter);
        /*为Button绑定监听器*/
        bt1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAsDropDown(v);//以这个Button为anchor（可以理解为锚，基准），在下方弹出
				
			}});
        
        bt2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAsDropDown(v,20,-20);//横轴偏移20，纵轴-20，一个状态栏的长度
				
			}});
        
        bt3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAtLocation(MyPopupWindow.this.findViewById(R.id.rl), 
						Gravity.CENTER, 0, 0);//在屏幕居中，无偏移
				
			}});
        
        
        bt4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAtLocation(MyPopupWindow.this.findViewById(R.id.rl), 
						Gravity.TOP | Gravity.LEFT, 20, 20);//在屏幕顶部|居右，带偏移
				
			}});
        
        bt5.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (mPop != null) {
					mPop.dismiss();//关闭对话框
				}
				
			}});
        
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	//截获按键事件
		if(keyCode == KeyEvent.KEYCODE_MENU){
				initPopWindow();
				mPop.showAtLocation(this.findViewById(R.id.rl),
						Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //在屏幕底部
		}else if(keyCode == KeyEvent.KEYCODE_BACK){
			if(mPop.isShowing()){
				mPop.dismiss();//关闭对话框
			}else{
				System.exit(0);
			}

		}
		return false;
		
	}
}