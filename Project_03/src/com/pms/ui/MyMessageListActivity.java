package com.pms.ui;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pms.data.Data;
import com.pms.data.IntentData;
import com.pms.data.ReceiveMessage;
import com.pms.database.MessageTableManager;
import com.pms.database.SettingInfo;
import com.pms.gps.R;
import com.pms.network.Connect;
import com.pms.network.Consts;

public class MyMessageListActivity extends Activity implements Handler.Callback{
	private ListView listview;
	private View localView;
	private View setServiceView;
	private View editView;
	private int currPostion = 0;
	private Context context;
	LocationManager locationManager;
	private Handler uiHandler=null;
	static final int ADDMESSAGE=1001;//添加任务消息
	static final int EDITMESSAGE=1002;//编辑某任务消息
	Connect heart_beat=null;
	static boolean stop=false;
	/**维持心跳时间**/
	static int WATITIME=30000;
	Vector<ReceiveMessage> messageList = new Vector<ReceiveMessage>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listview = new ListView(this);
		localView = LayoutInflater.from(this).inflate(
				R.layout.app_frame_layout, null);
		context = this;
		uiHandler=new Handler(this);
		Consts.SERVICE_IP = SettingInfo.getInstance(this).getService_IP();
		Consts.SERVICE_PORT = Integer.parseInt(SettingInfo.getInstance(this)
				.getService_PORT());
		MessageTableManager mtmt=new MessageTableManager(this, getDetailsTime());//获取当天的任务
		messageList=mtmt.getMessageList();
		initUI();
		getSystemInfo();//获取imei码，lac,cellid
		startLocation();//开启定位功能
		startListenerServerMessage();//开启心跳接收数据功能
	}

	private void initUI() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		updateListView();
		setContentView(localView);
		findViewById(R.id.setting_btn).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						setServiceIP(context).show();
					}
				});

		findViewById(R.id.edit_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				editItem(currPostion);
			}
		});
		findViewById(R.id.exit_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	/**
	 * 加载ListView数据
	 */
	private void updateListView(){
		currPostion=0;
		LinearLayout body = (LinearLayout) localView.findViewById(R.id.my_body);
		body.removeAllViews();
		LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		body.addView(listview, layout);
		for (int i = 0; i < 10; i++) {
			ReceiveMessage msg = new ReceiveMessage();
			msg.message = "编码,目的地,状态,时间,货号,取货地址,发货地址,手机号 ";
			if (i % 2 == 1)
				msg.state = 1;
			msg.time = getDetailsTime();
			messageList.add(msg);

		}
		int num=0;
		for(int i=0;i<messageList.size();i++){
			if(messageList.get(i).state==1){
				num++;
			}
		}
		((TextView)localView. findViewById(R.id.title)).setText("任务列表  "+num+"/"+messageList.size());
		MyListAdapter myAdapter = new MyListAdapter(this, messageList);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adpater, View v,
					int positon, long arg3) {
				currPostion = positon;
				editItem(positon);
			}

		});
		listview.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adpater, View v,
					int positon, long arg3) {
				currPostion = positon;
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 设置服务器IP对话框
	 * 
	 * @param context
	 * @return
	 */
	private Dialog setServiceIP(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		setServiceView = LayoutInflater.from(context).inflate(R.layout.server, null);
		((TextView) setServiceView.findViewById(R.id.ip)).setText(Consts.SERVICE_IP);
		((TextView) setServiceView.findViewById(R.id.port)).setText(Consts.SERVICE_PORT
				+ "");
		builder.setTitle("服务器设置");
		builder.setView(setServiceView);
		builder.setPositiveButton(R.string.button_ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						String ip = ((TextView) setServiceView.findViewById(R.id.ip))
								.getText().toString();
						boolean error = false;
						if (!ip.equals("")) {
							Consts.SERVICE_IP = ip;
							setServiceIP(ip);
						} else {
							AlertOk("服务器地址不能为空").show();
							error = true;
						}
						String port = ((TextView) setServiceView.findViewById(R.id.port))
								.getText().toString();
						if (!port.equals("")) {
							Consts.SERVICE_PORT = Integer.parseInt(port);
							setServicePort(port);
						} else {
							if (!error)
								AlertOk("端口不能为空").show();
						}
					}
				});
		builder.setNegativeButton(R.string.button_cancel,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}

				});
		return builder.create();
	}

	private void setServiceIP(String ip) {
		SettingInfo.getInstance(this).setService_IP(ip);
	}

	private void setServicePort(String port) {
		SettingInfo.getInstance(this).setService_PORT(port);
	}

	public AlertDialog.Builder AlertOk(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("系统提示");
		builder.setMessage(message);
		builder.setPositiveButton(R.string.button_ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}

				});
		return builder;
	}

	/**
	 * 编辑详细列表 对话框
	 */
	private Dialog openEditView(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		editView = LayoutInflater.from(context).inflate(R.layout.editdaloag,
				null);
		final String messages[] = message.split(",");
		((TextView) editView.findViewById(R.id.code)).setText(messages[4]);
		((TextView) editView.findViewById(R.id.sAddress)).setText(messages[5]);
		((TextView) editView.findViewById(R.id.dAddress)).setText(messages[6]);
		((TextView) editView.findViewById(R.id.phone)).setText(messages[7]);
		builder.setView(editView);
		builder.setTitle("填写详细信息");
		builder.setPositiveButton("上传", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if(messageList.get(currPostion).state==1){
					Toast.makeText(context, "该任务已经上传！", Toast.LENGTH_SHORT).show();
					return;
				}
				String code = ((TextView) editView.findViewById(R.id.code))
						.getText().toString();
				String sAddress = ((TextView) editView
						.findViewById(R.id.sAddress)).getText().toString();
				String dAddress = ((TextView) editView
						.findViewById(R.id.dAddress)).getText().toString();
				String phone = ((TextView) editView.findViewById(R.id.phone))
						.getText().toString();
				IntentData data=new IntentData();
				data.dAddress=messages[1];
				data.fCode=code;
				data.phone=phone;
				data.receiveAddress=sAddress;
				data.sendAddress=dAddress;
				data.state=messages[2];
				data.time=messages[3];
				data.taskcode=messages[0];
				final String upData=data.taskcode+","+data.dAddress+","+data.state+","
				+data.time+","+data.fCode+","+data.receiveAddress+","+data.sendAddress+","+data.phone;
				new Thread()	{
					public void run(){
						uploadCode(upData);
					}
				}.start();

			}
		});
		builder.setNegativeButton(R.string.button_cancel,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}

				});
		return builder.create();
	}

	private void editItem(int position) {
		openEditView( messageList.elementAt(position).message).show();
	}
	  /**
	   * 详细时间格式
	   * @return
	   */
	   private static String getDetailsTime(){
	    	TimeZone   t   =   TimeZone.getTimeZone("GMT+08:00");//获取东8区TimeZone
			Calendar calendar = Calendar.getInstance( t );
			calendar.getTime();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;//月份是从0开始，所以加1
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int min = calendar.get(Calendar.MINUTE);
			int ss=calendar.get(Calendar.SECOND);
			String time = year + "-" + (month<10?"0":"")+month + '-' + (day<10?"0":"")+day+ ' ' + (hour<10?"0":"")+hour + ':' + (min<10?"0":"")+min+":"+(ss<10?"0":"")+ss;
			return time;
	    }
	   
	
	 
	public  void uploadCode( String data){
				Connect uploadCode=new Connect(Consts.SERVICE_IP,Consts.UPLOAD_PORT);
				uploadCode.openChannel();
				try {
					uploadCode.getTcpChannel().connect(uploadCode.getTimeout());
					uploadCode.getTcpChannel().send(uploadCode.getTcpChannel().getOutputStream(), data.getBytes("utf-8"));
					byte[] buffer=uploadCode.getTcpChannel().receive(uploadCode.getTcpChannel().getInputStream());
					if(buffer!=null){
						String message=new String(buffer,"UTF-8");
						System.out.println("上传返回的信息"+message);
						uploadCode.close();
						if(message.indexOf("OK")!=-1){
							Message msg=Message.obtain(uiHandler, EDITMESSAGE, data);
							uiHandler.sendMessage(msg);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	/***
	 * 开启后台定位线程
	 * 
	 */
	public void startLocation() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // 查找到服务信息
						Criteria criteria = new Criteria();
						criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
						criteria.setAltitudeRequired(false);
						criteria.setBearingRequired(false);
						criteria.setCostAllowed(true);
						criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
						String provider = locationManager.getBestProvider(
								criteria, true); // 获取GPS信息
						Location location = locationManager
								.getLastKnownLocation(provider); // 通过GPS获取位置

						if (location != null) {
							Data.Latitude = location.getLatitude() + "";
							Data.Longitude = location.getLongitude() + "";
							Data.altitude = location.getAltitude() + "";
							Data.speed = location.getSpeed() + "";

							getSystemInfo();
							String 	data = "IMEI:" + Data.imei + "," + "Lon:"
									+ Data.Longitude + "," + "Lat:"
									+ Data.Latitude + "," + "Alt:"
									+ Data.altitude + "," + "Cell:"
									+ Data.cellid + "," + "Lac:" + Data.lac
									+ "," + "Spd:" + Data.speed + ",";
							uploadLocationMessage( data);

						} else {
						}
					} catch (Exception e) {

					}

					try {
						sleep(60000);// 休息1分钟
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}.start();

	}

	/**
	 * 上传定位信息
	 * 
	 * @param port
	 * @param data
	 */
	private void uploadLocationMessage(String data) {
		Connect uploadMessage = new Connect(Consts.SERVICE_IP,
				Consts.LOCATE_PORT);
		try {
			uploadMessage.openChannel();
			uploadMessage.getTcpChannel().connect(uploadMessage.getTimeout());
			byte[] b = data.getBytes();
			uploadMessage.getTcpChannel().send(
					uploadMessage.getTcpChannel().getOutputStream(), b);
			uploadMessage.close();
			System.out.println("发送定位信息:" + data);
		} catch (Exception e) {
			
		}
	}

	/**
	 * 心跳接收消息线程
	 *
	 */
	public  void startListenerServerMessage(){			
		heart_beat=new Connect(Consts.SERVICE_IP,Consts.HEART_PORT);			
		new Thread(){
			public void run(){					
					boolean start=false;
					/************************心跳连接**********************************/
						try{
							start=true;
							heart_beat.openChannel();
							heart_beat.getTcpChannel().connect(heart_beat.getTimeout());
						}catch(Exception e){
							start=false;
							startListenerServerMessage();//重新连接
						}
						stop=false;
						if(start){
							/************************心跳发送数据**************************/
							Thread send=new Thread(new Runnable(){
								public void run() {
								while(!stop){
									try {
										String message=Data.imei;
										byte[] b=message.getBytes("utf-8");
										heart_beat.getTcpChannel().send(heart_beat.getTcpChannel().getOutputStream(),b);
										System.out.println("心跳");
										sleep(WATITIME);						
										
									} catch (Exception e) {
										if(heart_beat!=null)
											heart_beat.close();
										stop=true;
										startListenerServerMessage();
									}
								}
								}
							});
							send.start();
							/************************心跳接受数据***************************/
							Thread receive=new Thread(new Runnable(){
								public void run(){
									while(!stop){
										try{
											byte[] data=heart_beat.getTcpChannel().receive(heart_beat.getTcpChannel().getInputStream());
											if(data!=null){
												stop=true;
												System.out.println("数据长度"+data.length);
												String message=new String(data,"UTF-8");
												if(message.substring(message.length()-1, message.length()).equals("E")){
													message=message.substring(0, message.length()-2);
												}
												ReceiveMessage rm=new ReceiveMessage();
												rm.message=message;
												rm.state=0;
												rm.time=getDetailsTime();
												String l="CK:"+data.length+"";
												heart_beat.getTcpChannel().send(heart_beat.getTcpChannel().getOutputStream(),l.getBytes("utf-8"));
												Message msg=Message.obtain(uiHandler, ADDMESSAGE, rm);
												uiHandler.sendMessage(msg);
											}
										}catch(Exception e){
											if(heart_beat!=null)
												heart_beat.close();
											stop=true;
											startListenerServerMessage();
										}
									}
								}
								
							});
							receive.start();
						}
				
			}
		}.start();
	}
	/**
	 * 获取系统IMEI, LAC CELLID码
	 */
	private void getSystemInfo() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
		Data.imei = tm.getDeviceId();
		Data.lac = location.getLac() + "";
		Data.cellid = location.getCid() + "";
	}
	
	public boolean handleMessage(Message msg) {
		switch(msg.what){
		case ADDMESSAGE://接受到新任务，添加任务到当前列表，并存储到数据库
			ReceiveMessage message=(ReceiveMessage)msg.obj;
			messageList.add(message);
			updateListView();
			MessageTableManager mtm=new MessageTableManager(context, message.time);
			mtm.addOneMessage(message);
			Toast.makeText(context, "您有新的任务，请注意查收", Toast.LENGTH_SHORT).show();
			break;
		case EDITMESSAGE://上传成功后 ，更新列表，并同步到数据库
			String uploadMessage=msg.obj.toString();
			ReceiveMessage rm=messageList.elementAt(currPostion);
			rm.message=uploadMessage;
			rm.state=1;
			messageList.setElementAt(rm, currPostion);
			updateListView();
			MessageTableManager mtm2=new MessageTableManager(context, rm.time);
			mtm2.updateMessage(rm);
			Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
			break;
		}
		return false;
	}

	
}
