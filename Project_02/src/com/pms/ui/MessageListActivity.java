package com.pms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pms.data.AppContext;
import com.pms.data.Message;
import com.pms.database.MessageTableManager;
import com.pms.logic.FileManager;
import com.pms.network.Consts;
import com.pms.scan.R;

public class MessageListActivity extends Activity{
	final int MESSAGE_CREATE=0;
	final int MESSAGE_DEL=1;
	final int MESSAGE_CLEAR=2;
	final int MESSAGE_VIEW=3;
	private View localView;
	private int selectNum=0;
	MyMessageListAdapter adapter;
	List<String> arraylist=new ArrayList<String>();
	ListView listview=null;
	Vector<Message> messagelist=new Vector<Message>();
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listview=new ListView(this);
		context=this;
		localView=LayoutInflater.from(this).inflate(R.layout.app_frame_layout, null);
		getData();
		initUI();
	}
	int msgStyle=0;
	View alertView=null;
	private ProgressDialog dialog;
	private Dialog buildDialog(Context context,String title,int msgStyle){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		this.msgStyle=msgStyle;
		builder.setTitle(title);
//		builder.setTitle("系统信息");
		if(msgStyle==0||msgStyle==1){
			
			alertView=LayoutInflater.from(context).inflate(R.layout.msg_context, null);
			if(msgStyle==0){
				Message msg=messagelist.elementAt(selectNum);
				
				((EditText)alertView.findViewById(R.id.context)).setText(msg.codetext);
				((EditText)alertView.findViewById(R.id.remark_context)).setText(msg.remark);
			}
			
			/*********************************/
//			((EditText)alertView.findViewById(R.id.context)).setVisibility(View.GONE);	
//			((TextView)alertView.findViewById(R.id.lianxu)).setVisibility(View.VISIBLE);
//			((TextView)alertView.findViewById(R.id.lianxu)).setText("恭喜你成功了！http://www.effect.cn-------将你的EMAIL留下来，你将收到我的gmail邀请！名额有限，先到先得！");
			
			
			
			((TextView)alertView.findViewById(R.id.group)).setText("组号: "+AppContext.getInstance().getCurrGroup().groupid);
			builder.setView(alertView);
		}else{
			if(msgStyle==2){
				builder.setMessage("确定删除该信息吗");
			}else{
				builder.setMessage("确定清空该分组所有信息吗");
			}		
		}
		builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//保存信息
				okHandle();
			}
		});
		builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//取消当前弹出对话框
				
			}
		});
		
		return builder.create();
	}
	private void okHandle(){
		switch(msgStyle){
		case 0:	
			Message msg=messagelist.elementAt(selectNum);
			if(((EditText)alertView.findViewById(R.id.context)).getText().equals(msg.codetext)&&((EditText)alertView.findViewById(R.id.remark_context)).getText().equals(msg.remark) ){
				//没有做任何改变，不保存
			}else{
				edit();
			}
			break;
		case 1:
			if(((EditText)alertView.findViewById(R.id.context)).getText().toString().equals("")){
				AlertOk("信息不能为空，请重新输入").show();
			}else{
				
				add();
			}
			
			break;
		case 2:
			del();
			break;
		case 3:
			delAll();
			break;
		}
	}
	private void add(){
		Message msg=new Message();
		msg.codetext=((EditText)alertView.findViewById(R.id.context)).getText().toString();
		boolean found=false;
		for(int i=0;i<messagelist.size();i++){
			if(((Message)messagelist.elementAt(i)).codetext.equals(msg.codetext)){
				found=true;
				break;
			}
		}
		if(found){
			AlertOk("信息重复，请重新输入").show();
		}else if( messagelist.size()>=Integer.parseInt(AppContext.getInstance().getCurrGroup().maxNum)){
			AlertOk("信息已经为最大数量").show();
		}else{
			
			msg.group=AppContext.getInstance().getCurrGroup().groupid;
			msg.state=1;
			msg.remark=((EditText)alertView.findViewById(R.id.remark_context)).getText().toString();
			msg.key=System.currentTimeMillis()+"";
			messagelist.add(msg);
			MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
			mtm.addOneMessage(msg);
			mtm=null;
			FileManager.addOneMessageOfGroup(AppContext.getInstance().getCurrGroup().groupid, msg);
			refreshUI();
			/*loading("清稍候");
			try {
				Handle.sendMessage(msg);
				destroyDialog();
				AlertOk("上传成功").show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
		
		
	}
	private AlertDialog.Builder AlertOk(String message){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle("系统提示");
		builder.setMessage(message);
		builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		return builder;
	}
	private void edit(){
		((Message)messagelist.elementAt(selectNum)).codetext=((EditText)alertView.findViewById(R.id.context)).getText().toString();
		((Message)messagelist.elementAt(selectNum)).remark=((EditText)alertView.findViewById(R.id.remark_context)).getText().toString();
		((Message)messagelist.elementAt(selectNum)).state=1;
		MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
		mtm.updateGroup(((Message)messagelist.elementAt(selectNum)));
		mtm=null;
//		MessageTableManager.getInstance(this, AppContext.getInstance().getCurrGroup().groupid).updateGroup(((Message)messagelist.elementAt(selectNum)));
		refreshUI();
		FileManager.saveFileOfGroup(AppContext.getInstance().getCurrGroup().groupid, messagelist);
		
	}
	/**
	 * 提示进度条
	 * @param msg
	 */
	public  void loading(String msg)
	{
		if(dialog==null||!dialog.isShowing())
		{
			dialog=new ProgressDialog(this);
			dialog.setTitle("系统信息");
			dialog.setMessage(msg);
			dialog.show();
			
		}
	}
	public void destroyDialog(){
		if(dialog!=null&&dialog.isShowing()){
			dialog.cancel();
		}
	}

	private void del(){
		((Message)messagelist.elementAt(selectNum)).state=2;
		MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
		mtm.updateGroup(((Message)messagelist.elementAt(selectNum)));
		mtm=null;
//		MessageTableManager.getInstance(this, AppContext.getInstance().getCurrGroup().groupid).updateGroup(((Message)messagelist.elementAt(selectNum)));
		refreshUI();
		FileManager.saveFileOfGroup(AppContext.getInstance().getCurrGroup().groupid, messagelist);
	}
	private void delAll(){
		messagelist.removeAllElements();
		MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
		mtm.deleteTable();
		mtm=null;
//		MessageTableManager.getInstance(this, AppContext.getInstance().getCurrGroup().groupid).deleteTable();
		refreshUI();
		FileManager.delectGroup(AppContext.getInstance().getCurrGroup().groupid);
	}
	private void getData(){
		MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
		messagelist=mtm.getMessageList();
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		Consts.IMEI=tm.getDeviceId();
		Consts.PHONENUMBER=tm.getLine1Number();
		System.out.println("imei==="+Consts.IMEI);
		tm=null;
	}
	private void refreshUI(){
		View topgroup=	(View)localView.findViewById(R.id.my_top);
		((TextView)topgroup.findViewById(R.id.title)).setText(getBarTitle());
		adapter=new MyMessageListAdapter(this,messagelist);
		listview.setAdapter(adapter);
	}
	private String getBarTitle(){
		String title="分组:  ";
		title=title+AppContext.getInstance().getCurrGroup().groupid+"        数量:  "+messagelist.size()+"/"+AppContext.getInstance().getCurrGroup().maxNum;
		return title;
	}
	private void initUI(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,   
		              WindowManager.LayoutParams. FLAG_FULLSCREEN);
		
		View topgroup=	(View)localView.findViewById(R.id.my_top);
		((TextView)topgroup.findViewById(R.id.title)).setText(getBarTitle());
		LinearLayout body=(LinearLayout)localView.findViewById(R.id.my_body);
		body.removeAllViews();
		LinearLayout.LayoutParams layout=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		body.addView(listview,layout);
		
		adapter=new MyMessageListAdapter(this,messagelist);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long id) {
				selectNum=index;
					
				buildDialog(context,"查看",0).show();
			}
		});
		listview.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				selectNum=index;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setContentView(localView);
		ImageButton  option=(ImageButton)findViewById(R.id.login_option);
		option.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openOptionsMenu();//弹出菜单选项
			}
		});
		
	   ImageButton  break_btn=(ImageButton)findViewById(R.id.break_btn);
	    break_btn.setOnClickListener(new OnClickListener() 
	    {
			public void onClick(View v) {
				finish();
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.addSubMenu(Menu.NONE, MESSAGE_VIEW, 0, "查看信息");
		menu.addSubMenu(Menu.NONE, MESSAGE_CREATE, 0, "新建信息");
		menu.addSubMenu(Menu.NONE, MESSAGE_DEL, 0, "删除信息");
		menu.addSubMenu(Menu.NONE, MESSAGE_CLEAR, 0, "清空分组");
		menu.addSubMenu(0, 8, 0, "导出到存储卡");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case MESSAGE_VIEW://查看
			if(messagelist.size()==0){
				AlertOk("当前分组为空").show();
			}else{
				
				buildDialog(this,"查看",0).show();
			}
			break;
		case MESSAGE_CREATE://新建
			buildDialog(this,"新建",1).show();
			break;
		case MESSAGE_DEL://删除单条记录
			if(messagelist.size()==0){
				AlertOk("当前分组为空").show();
			}else{
				
				buildDialog(this,"删除",2).show();
			}
			break;
		case MESSAGE_CLEAR://清空整个分组
			if(messagelist.size()==0){
				AlertOk("当前分组为空").show();
			}else{
				buildDialog(this,"清空分组",3).show();
				
			}
			break;
		case 8:
			FileManager.saveFileOfGroup(AppContext.getInstance().getCurrGroup().groupid, messagelist);
			AlertOk("导出成功").show();
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}

}
