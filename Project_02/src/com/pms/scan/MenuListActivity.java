package com.pms.scan;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.pms.data.AppContext;
import com.pms.data.Group;
import com.pms.database.GroupTableManager;
import com.pms.database.SettingInfo;
import com.pms.network.Consts;
import com.pms.ui.GroupManagerActivity;
import com.pms.ui.MessageListActivity;
import com.pms.ui.UploadSettingActivity;

public class MenuListActivity extends Activity{
	
	
	private View localView;
	List<String> arraylist=new ArrayList<String>();
	ListView listview=null;
	List<String> spinnerList=new ArrayList<String>();
	private Vector<Group> grouplist;
	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listview=new ListView(this);
		localView=LayoutInflater.from(this).inflate(R.layout.app_frame_layout, null);
		initSetting();
		initUI();
	}
	private void initSetting(){
		AppContext.getInstance().setCodeMode(Integer.parseInt(SettingInfo.getInstance(this).getOperaStyle()));
		Group group=null;
		String groupid=SettingInfo.getInstance(this).getDefaultGroup();
		Vector<Group> grouplist=GroupTableManager.getInsance(this).getGroupList();
		for(int i=0;i<grouplist.size();i++){
			if(((Group)grouplist.elementAt(i)).groupid.equals(groupid)){
				group=(Group)grouplist.elementAt(i);
				break;
			}
		}
		Consts.SERVICE_IP=SettingInfo.getInstance(this).getService_IP();
		try{			
			Consts.SERVICE_PORT=Integer.parseInt(SettingInfo.getInstance(this).getService_PORT());
		}catch(Exception e){
			
		}
		AppContext.getInstance().setCurrGroup(group);
	}
	private void initUI(){
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,   
		              WindowManager.LayoutParams. FLAG_FULLSCREEN);
		arraylist.add("开始扫码");
		arraylist.add("信息列表");
		arraylist.add("分组管理");
		arraylist.add("扫码设置");
		arraylist.add("上传设置");
		arraylist.add("服务器设置");
		View topgroup=	(View)localView.findViewById(R.id.my_top);
		((TextView)topgroup.findViewById(R.id.title)).setText("条码扫描器");
		LinearLayout body=(LinearLayout)localView.findViewById(R.id.my_body);
		body.removeAllViews();
		LinearLayout.LayoutParams layout=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		body.addView(listview,layout);//将ListView添加到框架的主体里
		listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist));//android系统默认列表布局
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,long id) {
				openItem(position);//执行列表项某功能
			}
		});
		setContentView(localView);
		ImageButton  option=(ImageButton)findViewById(R.id.login_option);
		option.setVisibility(View.INVISIBLE);
		option.setEnabled(false);
	   ImageButton  break_btn=(ImageButton)findViewById(R.id.break_btn);
	    break_btn.setVisibility(View.VISIBLE);
	    break_btn.setOnClickListener(new OnClickListener() 
	    {
			public void onClick(View v) {
				finish();//退出程序
			}
		});
		
		
	}
	private void openItem(int position){
		switch(position){
		case 0:
			Intent intent0=new Intent(this,CaptureActivity.class);
			startActivity(intent0);
			break;
		case 1:
			Intent intent1=new Intent(this,MessageListActivity.class);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2=new Intent(this,GroupManagerActivity.class);
			startActivity(intent2);
			break;
		case 3:
			getSpinnerList();
			buildDialog(this).show();
			break;
		case 4:
			Intent intent4=new Intent(this,UploadSettingActivity.class);
			startActivity(intent4);
			break;
		case 5:
			setServiceIP(this).show();
			break;
		}
	}
	  public AlertDialog.Builder AlertOk(String message){
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("系统提示");
			builder.setMessage(message);
			builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			return builder;
		}
	private void getSpinnerList(){
		if(spinnerList.size()==0){
			
			grouplist=GroupTableManager.getInsance(this).getGroupList();
			for(int i=0;i<grouplist.size();i++){
				spinnerList.add(((Group)grouplist.elementAt(i)).groupid);
			}
		}
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.addSubMenu(0, 1, 0, "一键导出到卡");
		return super.onCreateOptionsMenu(menu);
	}
	private void saveSetting(int spinnerSelected,String text){
		int mode=0;
		if(!text.equals("单次扫描")){
			mode=1;
		}
		AppContext.getInstance().setCodeMode(mode);
		Group currgroup=(Group)grouplist.elementAt(spinnerSelected);
		AppContext.getInstance().setCurrGroup(currgroup);
		System.out.println("当前group id=="+currgroup.groupid);
		SettingInfo.getInstance(this).setDefaultGroup(currgroup.groupid);
		SettingInfo.getInstance(this).setOperaStyle(mode+"");
	}
	private Dialog setServiceIP(Context context){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		view=LayoutInflater.from(context).inflate(R.layout.server, null);
		((TextView)view.findViewById(R.id.ip)).setText(Consts.SERVICE_IP);
		((TextView)view.findViewById(R.id.port)).setText(Consts.SERVICE_PORT+"");
		builder.setTitle("服务器设置");
		builder.setView(view);
		builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String ip=((TextView)view.findViewById(R.id.ip)).getText().toString();
				boolean error=false;
				if(!ip.equals("")){
					Consts.SERVICE_IP=ip;	
					setServiceIP(ip);
				}else{
					AlertOk("服务器地址不能为空").show();
					error=true;
				}
				String port=((TextView)view.findViewById(R.id.port)).getText().toString();
				if(!port.equals("")){					
					Consts.SERVICE_PORT=Integer.parseInt(port);
					setServicePort(port);
				}else{
					if(!error)
					AlertOk("端口不能为空").show();
				}
			}
		});
		builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//取消当前弹出对话框
				dialog.cancel();
			}
		});
		return builder.create();
	}
	private void setServiceIP(String ip){
		SettingInfo.getInstance(this).setService_IP(ip);
	}
	private void setServicePort(String port){
		SettingInfo.getInstance(this).setService_PORT(port);
	}
	
	private Dialog buildDialog(Context context){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		view=LayoutInflater.from(context).inflate(R.layout.setting_barcode, null);
		RadioGroup  ra=(RadioGroup)view.findViewById(R.id.set_barcode);
		int index=AppContext.getInstance().getCodeMode();
		((RadioButton)ra.getChildAt(index)).setChecked(true);
		((RadioButton)ra.getChildAt(index)).setSelected(true);
		String name=AppContext.getInstance().getCurrGroup().groupid;
		
		builder.setTitle("扫码设置");
		Spinner spinner=(Spinner)view.findViewById(R.id.select_group);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinnerList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		for(int i=0;i<spinnerList.size();i++){
			if(name.equals(spinnerList.get(i))){
				spinner.setSelection(i);				
				break;
			}
		}
		builder.setView(view);
		builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//保存设置
				
				Spinner spinner=(Spinner)view.findViewById(R.id.select_group);
				int index=spinner.getSelectedItemPosition();
				String text=null;
				RadioGroup ra=((RadioGroup)view.findViewById(R.id.set_barcode));
				for(int i=0;i<ra.getChildCount();i++){
					RadioButton rb=(RadioButton)ra.getChildAt(i);
					if(rb.isChecked()){
						text=rb.getText().toString();
						break;
					}
				}
				saveSetting(index,text);
			}
		});
		builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//取消当前弹出对话框
				dialog.cancel();
			}
		});
		
		return builder.create();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1){
			//保存
			
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
