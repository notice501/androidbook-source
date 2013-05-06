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
	 private String[] items={"����", "�ļ�����", "���ع���", "ȫ��", "��ַ", "��ǩ",
				"������ǩ", "����ҳ��", "�˳�", "ҹ��ģʽ", "ˢ��", "����"};
	 private PopupWindow mPop;
	 private View layout;
	 
	 /*��ʼ��һ������*/
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
        /*�õ�Button����*/
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);
        bt4 = (Button)findViewById(R.id.bt4);
        bt5 = (Button)findViewById(R.id.bt5);
        /*���벼��*/
        layout = View.inflate(this, R.layout.window, null);
        /*�õ�GridView���󣬲��Ұ�������*/
        gv = (GridView) layout.findViewById(R.id.gridview);
        MyAdapter adapter=new MyAdapter(this,items,icons);
        gv.setAdapter(adapter);
        /*ΪButton�󶨼�����*/
        bt1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAsDropDown(v);//�����ButtonΪanchor���������Ϊê����׼�������·�����
				
			}});
        
        bt2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAsDropDown(v,20,-20);//����ƫ��20������-20��һ��״̬���ĳ���
				
			}});
        
        bt3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAtLocation(MyPopupWindow.this.findViewById(R.id.rl), 
						Gravity.CENTER, 0, 0);//����Ļ���У���ƫ��
				
			}});
        
        
        bt4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initPopWindow();
				mPop.showAtLocation(MyPopupWindow.this.findViewById(R.id.rl), 
						Gravity.TOP | Gravity.LEFT, 20, 20);//����Ļ����|���ң���ƫ��
				
			}});
        
        bt5.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (mPop != null) {
					mPop.dismiss();//�رնԻ���
				}
				
			}});
        
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	//�ػ񰴼��¼�
		if(keyCode == KeyEvent.KEYCODE_MENU){
				initPopWindow();
				mPop.showAtLocation(this.findViewById(R.id.rl),
						Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //����Ļ�ײ�
		}else if(keyCode == KeyEvent.KEYCODE_BACK){
			if(mPop.isShowing()){
				mPop.dismiss();//�رնԻ���
			}else{
				System.exit(0);
			}

		}
		return false;
		
	}
}