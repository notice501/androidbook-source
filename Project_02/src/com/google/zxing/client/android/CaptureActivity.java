/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.ClipboardManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.history.HistoryManager;
import com.google.zxing.client.android.result.ResultHandler;
import com.google.zxing.client.android.result.ResultHandlerFactory;
import com.pms.data.AppContext;
import com.pms.database.MessageTableManager;
import com.pms.database.SettingInfo;
import com.pms.logic.FileManager;
import com.pms.logic.Handle;
import com.pms.scan.R;

/**
 * The barcode reader activity itself. This is loosely based on the CameraPreview
 * example included in the Android SDK.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback,Handler.Callback {

	  private static final String TAG = CaptureActivity.class.getSimpleName();

	  private static final int SHARE_ID = Menu.FIRST;
	  private static final int HISTORY_ID = Menu.FIRST + 1;
	  private static final int SETTINGS_ID = Menu.FIRST + 2;
	  private static final int HELP_ID = Menu.FIRST + 3;
	  private static final int ABOUT_ID = Menu.FIRST + 4;

	  private static final long INTENT_RESULT_DURATION = 1500L;
	  private static final long BULK_MODE_SCAN_DELAY_MS = 1000L;
	  private static final float BEEP_VOLUME = 0.10f;
	  private static final long VIBRATE_DURATION = 200L;

	  private static final String PACKAGE_NAME = "com.google.zxing.client.android";
	  private static final String PRODUCT_SEARCH_URL_PREFIX = "http://www.google";
	  private static final String PRODUCT_SEARCH_URL_SUFFIX = "/m/products/scan";
	  private static final String ZXING_URL = "http://zxing.appspot.com/scan";
	  private static final String RETURN_CODE_PLACEHOLDER = "{CODE}";
	  private static final String RETURN_URL_PARAM = "ret";

	  private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES;
	  static {
	    DISPLAYABLE_METADATA_TYPES = new HashSet<ResultMetadataType>(5);
	    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ISSUE_NUMBER);
	    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.SUGGESTED_PRICE);
	    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ERROR_CORRECTION_LEVEL);
	    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.POSSIBLE_COUNTRY);
	  }

	  private enum Source {
	    NATIVE_APP_INTENT,
	    PRODUCT_SEARCH_LINK,
	    ZXING_LINK,
	    NONE
	  }

	  private CaptureActivityHandler handler;

	  private ViewfinderView viewfinderView;
	  private TextView statusView;
	  private View resultView;
	  private MediaPlayer mediaPlayer;
	  private Result lastResult;
	  private boolean hasSurface;
	  private boolean playBeep;
	  private boolean vibrate;
	  private boolean copyToClipboard;
	  private Source source;
	  private String sourceUrl;
	  private String returnUrlTemplate;
	  private Vector<BarcodeFormat> decodeFormats;
	  private String characterSet;
	  private String versionName;
	  private HistoryManager historyManager;
	  private InactivityTimer inactivityTimer;

	  /**
	   * When the beep has finished playing, rewind to queue up another one.
	   */
	  private final OnCompletionListener beepListener = new OnCompletionListener() {
	    public void onCompletion(MediaPlayer mediaPlayer) {
	      mediaPlayer.seekTo(0);
	    }
	  };

	  private final DialogInterface.OnClickListener aboutListener =
	      new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialogInterface, int i) {
	      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.zxing_url)));
	      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	      startActivity(intent);
	    }
	  };

	private Vector<com.pms.data.Message> messagelist;

	  ViewfinderView getViewfinderView() {
	    return viewfinderView;
	  }

	  public Handler getHandler() {
	    return handler;
	  }
	private String getStateString(){
		String str="";
		if(AppContext.getInstance().getCodeMode()==0){
			str=str+"单次模式/ 分组: "+AppContext.getInstance().getCurrGroup().groupid  
			+"  数量: "+messagelist.size()+"/"+AppContext.getInstance().getCurrGroup().maxNum;
		}else{
			str=str+"连续模式/ 分组: "+AppContext.getInstance().getCurrGroup().groupid  
			+"  数量: "+messagelist.size()+"/"+AppContext.getInstance().getCurrGroup().maxNum;
		}
		return str;
	}
	  @Override
	  public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
		try{
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕唤醒状态
			setContentView(R.layout.capture);
			MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
			messagelist=mtm.getMessageList();//从数据库获取当前分组的所有数据
			mtm=null;
			//CameraManger是一个唯一与相机对话的服务程序，通过对图片进行预览和解码。
			CameraManager.init(getApplication());
			viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
			resultView = findViewById(R.id.result_view);
			statusView = (TextView) findViewById(R.id.status_view);
			statusView.setText(getStateString());
			handler = null;
			lastResult = null;
			hasSurface = false;
			inactivityTimer = new InactivityTimer(this);//界面刷新的计时器
			uploadHandle=new Handler(this);//上传数据后，用于更新界面的handle
		}catch(Exception e){
			AlertOk(e.getMessage()).show();
		}catch(Error e){
			AlertOk(e.getMessage()).show();
		}
	    System.out.println("===========创建===============");
//	    showHelpOnFirstLaunch();
	  }

	  @Override
	  protected void onResume() {
	    super.onResume();
	    System.out.println("===========onResume===============");
	    try{
	    	resetStatusView();
	    	
	    	SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
	    	SurfaceHolder surfaceHolder = surfaceView.getHolder();
	    	System.out.println("has face"+hasSurface);
	    	if (hasSurface) {
	    		// The activity was paused but not stopped, so the surface still exists. Therefore
	    		// surfaceCreated() won't be called, so init the camera here.
	    		initCamera(surfaceHolder);
	    	} else {
	    		// Install the callback and wait for surfaceCreated() to init the camera.
	    		surfaceHolder.addCallback(this);
	    		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    	}
	    
	    
	    	Intent intent = getIntent();
	    	String action = intent == null ? null : intent.getAction();
	    	String dataString = intent == null ? null : intent.getDataString();
	    	System.out.println("action======"+action);
	    	if (intent != null && action != null) {
	    		if (action.equals(Intents.Scan.ACTION)) {
	    			// Scan the formats the intent requested, and return the result to the calling activity.
	    			source = Source.NATIVE_APP_INTENT;
	    			decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
	    		} else if (dataString != null && dataString.contains(PRODUCT_SEARCH_URL_PREFIX) &&
	    				dataString.contains(PRODUCT_SEARCH_URL_SUFFIX)) {
	    			// Scan only products and send the result to mobile Product Search.
	    			source = Source.PRODUCT_SEARCH_LINK;
	    			sourceUrl = dataString;
	    			decodeFormats = DecodeFormatManager.PRODUCT_FORMATS;
	    		} else if (dataString != null && dataString.startsWith(ZXING_URL)) {
	    			// Scan formats requested in query string (all formats if none specified).
	    			// If a return URL is specified, send the results there. Otherwise, handle it ourselves.
	    			source = Source.ZXING_LINK;
	    			sourceUrl = dataString;
	    			Uri inputUri = Uri.parse(sourceUrl);
	    			returnUrlTemplate = inputUri.getQueryParameter(RETURN_URL_PARAM);
	    			decodeFormats = DecodeFormatManager.parseDecodeFormats(inputUri);
	    		} else {
	    			// Scan all formats and handle the results ourselves (launched from Home).
	    			source = Source.NONE;
	    			decodeFormats = null;
	    		}
	    		characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);
	    		
	    	} else {
	    		source = Source.NONE;
	    		decodeFormats = null;
	    		characterSet = null;
	    	}
	    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    	playBeep = prefs.getBoolean(PreferencesActivity.KEY_PLAY_BEEP, true);
	    	if (playBeep) {	    		
	    		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
	    		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
	    			playBeep = false;
	    		}
	    	}
	    	vibrate = prefs.getBoolean(PreferencesActivity.KEY_VIBRATE, false);
	    	copyToClipboard = prefs.getBoolean(PreferencesActivity.KEY_COPY_TO_CLIPBOARD, true);
	    	initBeepSound();
	    }catch(Exception e){
	    	AlertOk(e.getMessage()).show();
	    }
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    if (handler != null) { 
	      handler.quitSynchronously();
	      handler = null;
	    }
	    CameraManager.get().closeDriver();
	  }

	  @Override
	  protected void onDestroy() {
	    inactivityTimer.shutdown();
	    super.onDestroy();
	  }

	  @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	      if (source == Source.NATIVE_APP_INTENT) {
	        setResult(RESULT_CANCELED);
	        finish();
	        return true;
	      } else if ((source == Source.NONE || source == Source.ZXING_LINK) && lastResult != null) {
	        resetStatusView();
	        if (handler != null) {
	          handler.sendEmptyMessage(R.id.restart_preview);
	        }
	        return true;
	      }
	    } else if (keyCode == KeyEvent.KEYCODE_FOCUS || keyCode == KeyEvent.KEYCODE_CAMERA) {
	      // Handle these events so they don't launch the Camera app
	      return true;
	    }
	    return super.onKeyDown(keyCode, event);
	  }

	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
//	    menu.add(0, SHARE_ID, 0, R.string.menu_share).setIcon(android.R.drawable.ic_menu_share);
//	    menu.add(0, HISTORY_ID, 0, R.string.menu_history).setIcon(android.R.drawable.ic_menu_recent_history);
	    menu.add(0, SETTINGS_ID, 0, R.string.menu_settings).setIcon(android.R.drawable.ic_menu_preferences);
//	    menu.add(0, HELP_ID, 0, R.string.menu_help).setIcon(android.R.drawable.ic_menu_help);
//	    menu.add(0, ABOUT_ID, 0, R.string.menu_about).setIcon(android.R.drawable.ic_menu_info_details);
	    return true;
	  }

	  // Don't display the share menu item if the result overlay is showing.
	  @Override
	  public boolean onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
//	    menu.findItem(SHARE_ID).setVisible(lastResult == null);
	    return true;
	  }

	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case SHARE_ID: {
	       /* Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	        intent.setClassName(this, ShareActivity.class.getName());
	        startActivity(intent);*/
	        break;
	      }
	      case HISTORY_ID: {
	       /* AlertDialog historyAlert = historyManager.buildAlert();
	        historyAlert.show();*/
	        break;
	      }
	      case SETTINGS_ID: {
	        Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	        intent.setClassName(this, PreferencesActivity.class.getName());
	        startActivity(intent);
	        break;
	      }
	      case HELP_ID: {
	    /*    Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	        intent.setClassName(this, HelpActivity.class.getName());
	        startActivity(intent);*/
	        break;
	      }
	      case ABOUT_ID:
	        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(getString(R.string.title_about) + versionName);
	        builder.setMessage(getString(R.string.msg_about) + "\n\n" + getString(R.string.zxing_url));
	        builder.setIcon(R.drawable.launcher_icon);
	        builder.setPositiveButton(R.string.button_open_browser, aboutListener);
	        builder.setNegativeButton(R.string.button_cancel, null);
	        builder.show();*/
	        break;
	    }
	    return super.onOptionsItemSelected(item);
	  }

	  @Override
	  public void onConfigurationChanged(Configuration config) {
	    // Do nothing, this is to prevent the activity from being restarted when the keyboard opens.
	    super.onConfigurationChanged(config);
	  }
	  @Override
	  public void surfaceCreated(SurfaceHolder holder) {
		  System.out.println("==============surfaceCreated===================");
	    if (!hasSurface) {
	      hasSurface = true;
	      initCamera(holder);
	    }
	  }

	  public void surfaceDestroyed(SurfaceHolder holder) {
	    hasSurface = false;
	  }

	  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	  }

	  /**
	   * A valid barcode has been found, so give an indication of success and show the results.
	   *
	   * @param rawResult The contents of the barcode.
	   * @param barcode   A greyscale bitmap of the camera data which was decoded.
	   */
	  public void handleDecode(Result rawResult, Bitmap barcode) {
	    inactivityTimer.onActivity();
	    lastResult = rawResult;
	   if (barcode == null) {
	      handleDecodeInternally(rawResult, null);
	    } else {
	      playBeepSoundAndVibrate();
	      drawResultPoints(barcode, rawResult);
	     
	   switch (source) {
	        case NATIVE_APP_INTENT:
	        case PRODUCT_SEARCH_LINK:
	          handleDecodeExternally(rawResult, barcode);
	          break;
	        case ZXING_LINK:
	          if (returnUrlTemplate == null){
	            handleDecodeInternally(rawResult, barcode);
	          } else {
	            handleDecodeExternally(rawResult, barcode);
	          }
	          break;
	        case NONE:
	          if (SettingInfo.getInstance(this).getOperaStyle().equals("1")) {//连续扫码处理
	            Toast.makeText(this, R.string.msg_bulk_mode_scanned, Toast.LENGTH_SHORT).show();
	            // Wait a moment or else it will scan the same barcode continuously about 3 times
	            if(messagelist.size()>=Integer.parseInt(AppContext.getInstance().getCurrGroup().maxNum)){
	            	//连续扫码完，已达到该组最大数量
	            	buildDialog(this).show();
	            }else{
	            	if(messagelist.size()>0){
	            		((TextView)findViewById(R.id.topcontext1)).setVisibility(View.VISIBLE);
	            		((TextView)findViewById(R.id.topcontext1)).setText(messagelist.elementAt(messagelist.size()-1).codetext);
	            		if(messagelist.size()>1){
	            			((TextView)findViewById(R.id.topcontext2)).setVisibility(View.VISIBLE);
	            			((TextView)findViewById(R.id.topcontext2)).setText(messagelist.elementAt(messagelist.size()-2).codetext);
	            		}
	            	}
	            	ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
	            	com.pms.data.Message resultMsg=new com.pms.data.Message();
	        		resultMsg.codetext=resultHandler.getDisplayContents().toString();
	        		resultMsg.group=AppContext.getInstance().getCurrGroup().groupid;
	        		resultMsg.state=0;
	        		resultMsg.remark="";
	        		resultMsg.key=System.currentTimeMillis()+"";
	            	addCheck(resultMsg);
	            	
	            	
	            }
	          } else {
	            handleDecodeInternally(rawResult, barcode);
	          }
	          break;
	      }
	    }
	  }
	  int choiceIndex=0;
	  com.pms.data.Message choicemessage;

	  private View alertView;

	private ProgressDialog dialog;

	private com.pms.data.Message resultMsg;
	  /**
	   * OK事件
	   */
		private void handleOK(){
			resultMsg=new com.pms.data.Message();
			resultMsg.codetext=((TextView) findViewById(R.id.contents_text_view)).getText().toString();
			resultMsg.group=AppContext.getInstance().getCurrGroup().groupid;
			resultMsg.state=0;
			resultMsg.remark=((EditText) findViewById(R.id.contents_remark)).getText().toString();
			resultMsg.key=System.currentTimeMillis()+"";
			if(SettingInfo.getInstance(this).getUploadStyle().equals("0")){//本地存储 
				
				//保存条码
				addCheck(resultMsg);
			}else if( SettingInfo.getInstance(this).getUploadStyle().equals("1")){//GPRS上传
				Message localMessage = Message.obtain(uploadHandle, UPLOADMESSAGE, "");
				uploadHandle.sendMessage(localMessage);
				}else{
					
				}
		}
		private void uploadMessage(){
			loading("正在上传数据，请稍候");
			
			new Thread(){
				public void run(){
					try {
						int mode=getOperaMode();
						Object obj=(mode==0?Handle.sendMessage(resultMsg):Handle.sendMessageListOfGroup(messagelist));
						checkDeleteLocalFile();
						destroyDialog();
						if(mode==0){						
							handler.sendEmptyMessageDelayed(R.id.restart_preview, BULK_MODE_SCAN_DELAY_MS);
							resetStatusView();
						}else{
							finish();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		private int getOperaMode(){ 
			if(SettingInfo.getInstance(this).getOperaStyle().equals("0")){
				return 0;
			}else {
				return 1;
			}
		}
		/**
		 * 提示进度条
		 * @param msg
		 */
		public  void loading(String msg)
		{
			if(dialog==null)
			{
				dialog=new ProgressDialog(this);			
			}
			dialog.setTitle("系统信息");
			dialog.setMessage(msg);
			dialog.show();
		}
		public void destroyDialog(){
			if(dialog!=null&&dialog.isShowing()){
				dialog.cancel();
			}
		}
		/**
		 * 判断上传成功后，是否删除本地数据
		 */
		private void checkDeleteLocalFile(){
			if(SettingInfo.getInstance(this).getSaveStyle().equals("0")){//上传后清空
				//删除本地数据
				MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
				mtm.deleteTable();
				FileManager.delectGroup(AppContext.getInstance().getCurrGroup().groupid);
			}
		}
	  /**
	   * 保存一条信息
	   */
	  private void addCheck(com.pms.data.Message msg){
		  if(messagelist.size()<Integer.parseInt(AppContext.getInstance().getCurrGroup().maxNum)){
			  String mode=SettingInfo.getInstance(this).getJudgeMode();
			  boolean found=false;//是否重复
			  int index=0;
			  for(int i=0;i<messagelist.size();i++){
				  if(((com.pms.data.Message)messagelist.elementAt(i)).codetext.equals(msg.codetext)){
					  found=true;
					  index=i;
					  break;
				  }
			  }
			  if(found){
				  if(mode.equals("0")){//提示
					  choicemessage=msg;
					  choiceIndex=index;
					  AlertChoice().show();
				  }else if(mode.equals("1")){//新增
					  addOneMessage(msg);
				  }else{//替换
					  editOneMessage(msg,index);				  
				  }
				  
			  }else{
				  
				  addOneMessage(msg);
			  }
		  }else{
			  AlertOk("已经为分组最大数量").show();
		  }
	  }
	  /**
	   * 替换或编辑
	   * @param msg
	   * @param index
	   */
	  private void editOneMessage(com.pms.data.Message msg,int index){
		  com.pms.data.Message temp=messagelist.elementAt(index);
		  temp.codetext=msg.codetext;
		  temp.remark=msg.remark;
		  temp.state=msg.state;
		  messagelist.setElementAt(temp, index);
		  if(SettingInfo.getInstance(this).getOperaStyle().equals("0")){//单次扫码模式，才保存		  
			  MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
			  mtm.updateGroup(msg);
			  mtm=null;
			  FileManager.saveFileOfGroup(AppContext.getInstance().getCurrGroup().groupid, messagelist);
		  }
		  if (handler != null) {
		  		handler.sendEmptyMessageDelayed(R.id.restart_preview, BULK_MODE_SCAN_DELAY_MS);
		  	 }
		  resetStatusView();
	  }
	  /**
	   * 新增 分两种情况，一种是单次模式，新增，另一种连续模式，新增，不存入数据库
	   * @param msg
	   */
	  private void addOneMessage(com.pms.data.Message msg){
		  messagelist.add(msg);
		  if(SettingInfo.getInstance(this).getOperaStyle().equals("0")){//单次扫码模式，才保存		  
			  MessageTableManager mtm=new MessageTableManager(this, AppContext.getInstance().getCurrGroup().groupid);
			  mtm.addOneMessage(msg);
			  mtm=null;
			  FileManager.addOneMessageOfGroup(AppContext.getInstance().getCurrGroup().groupid, msg);
		  }
		  if (handler != null) {
	  		handler.sendEmptyMessageDelayed(R.id.restart_preview, BULK_MODE_SCAN_DELAY_MS);
	  	 }
	  	  resetStatusView();
	  }
	  /**
	   * 连续扫码成功后，提示框
	   * @param context
	   * @return
	   */
		private Dialog buildDialog(Context context){
			AlertDialog.Builder builder=new AlertDialog.Builder(context);		
			builder.setTitle("系统信息");
			alertView=LayoutInflater.from(context).inflate(R.layout.msg_context, null);
			((EditText)alertView.findViewById(R.id.context)).setVisibility(View.GONE);	
			((TextView)alertView.findViewById(R.id.lianxu)).setVisibility(View.VISIBLE);
			((TextView)alertView.findViewById(R.id.lianxu)).setText("恭喜您连续扫码成功了！ ");
			((TextView)alertView.findViewById(R.id.group)).setText("组号: "+AppContext.getInstance().getCurrGroup().groupid);
			builder.setView(alertView);	
			String name="";
			if(SettingInfo.getInstance(context).getUploadStyle().equals("0")){
				name="保存";
			}else{
				name="上传";
			}
			builder.setPositiveButton(name, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//保存信息
					if(SettingInfo.getInstance(CaptureActivity.this).getUploadStyle().equals("0")){//本地存储 
						saveAll();
						finish();
					
					}else{
						Message localMessage = Message.obtain(uploadHandle, UPLOADMESSAGE, "");
						uploadHandle.sendMessage(localMessage);
				
					}
				}
			});
			builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//取消当前弹出对话框
					finish();
				}
			});
			
			return builder.create();
		}
		private Handler uploadHandle;
		private void saveAll(){
			MessageTableManager mtm=new MessageTableManager(this,AppContext.getInstance().getCurrGroup().groupid);
			mtm.saveMessageList(messagelist);
			FileManager.saveFileOfGroup(AppContext.getInstance().getCurrGroup().groupid, messagelist);
			mtm=null;
		}
	  private AlertDialog.Builder AlertChoice(){
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("系统提示");
			builder.setMessage("数据录入重复，请选择操作");
			builder.setPositiveButton("新增", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					addOneMessage(choicemessage);
				}
			});
			builder.setNegativeButton("替换", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					editOneMessage(choicemessage,choiceIndex);				
					
				}
			});
			return builder;
		}
	  public AlertDialog.Builder AlertOk(String message){
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("系统提示");
			System.out.println("message================"+message);
			builder.setMessage(message);
			builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			return builder;
		}
	  /**
	   * Superimpose a line for 1D or dots for 2D to highlight the key features of the barcode.
	   *
	   * @param barcode   A bitmap of the captured image.
	   * @param rawResult The decoded results which contains the points to draw.
	   */
	  private void drawResultPoints(Bitmap barcode, Result rawResult) {
	    ResultPoint[] points = rawResult.getResultPoints();
	    if (points != null && points.length > 0) {
	      Canvas canvas = new Canvas(barcode);
	      Paint paint = new Paint();
	      paint.setColor(getResources().getColor(R.color.result_image_border));
	      paint.setStrokeWidth(3.0f);
	      paint.setStyle(Paint.Style.STROKE);
	      Rect border = new Rect(2, 2, barcode.getWidth() - 2, barcode.getHeight() - 2);
	      canvas.drawRect(border, paint);

	      paint.setColor(getResources().getColor(R.color.result_points));
	      if (points.length == 2) {
	        paint.setStrokeWidth(4.0f);
	        drawLine(canvas, paint, points[0], points[1]);
	      } else if (points.length == 4 &&
	                 (rawResult.getBarcodeFormat().equals(BarcodeFormat.UPC_A)) ||
	                 (rawResult.getBarcodeFormat().equals(BarcodeFormat.EAN_13))) {
	        // Hacky special case -- draw two lines, for the barcode and metadata
	        drawLine(canvas, paint, points[0], points[1]);
	        drawLine(canvas, paint, points[2], points[3]);
	      } else {
	        paint.setStrokeWidth(10.0f);
	        for (ResultPoint point : points) {
	          canvas.drawPoint(point.getX(), point.getY(), paint);
	        }
	      }
	    }
	  }

	  private static void drawLine(Canvas canvas, Paint paint, ResultPoint a, ResultPoint b) {
	    canvas.drawLine(a.getX(), a.getY(), b.getX(), b.getY(), paint);
	  }

	  // Put up our own UI for how to handle the decoded contents.
	  private void handleDecodeInternally(Result rawResult, Bitmap barcode) {
	    statusView.setVisibility(View.GONE);
	    viewfinderView.setVisibility(View.GONE);
	    resultView.setVisibility(View.VISIBLE);
	   ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
	    if (barcode == null) {
	      barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),
	          R.drawable.launcher_icon));
	    } else {
	      barcodeImageView.setImageBitmap(barcode);
	    }
	    TextView formatTextView = (TextView) findViewById(R.id.format_text_view);
	    formatTextView.setText(rawResult.getBarcodeFormat().toString());
	    ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
	    TextView typeTextView = (TextView) findViewById(R.id.type_text_view);
	    typeTextView.setText(resultHandler.getType().toString());
	    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	    String formattedTime = formatter.format(new Date(rawResult.getTimestamp()));
	    TextView timeTextView = (TextView) findViewById(R.id.time_text_view);
	    timeTextView.setText(formattedTime);	    
	    TextView group = (TextView) findViewById(R.id.contents_group);
	    group.setText("分组: "+AppContext.getInstance().getCurrGroup().groupid);	    
	    TextView metaTextView = (TextView) findViewById(R.id.meta_text_view);
	    View metaTextViewLabel = findViewById(R.id.meta_text_view_label);
	    metaTextView.setVisibility(View.GONE);
	    metaTextViewLabel.setVisibility(View.GONE);
	    Map<ResultMetadataType,Object> metadata =
	        (Map<ResultMetadataType,Object>) rawResult.getResultMetadata();
	    if (metadata != null) {
	      StringBuilder metadataText = new StringBuilder(20);
	      for (Map.Entry<ResultMetadataType,Object> entry : metadata.entrySet()) {
	        if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
	          metadataText.append(entry.getValue()).append('\n');
	        }
	      }
	      if (metadataText.length() > 0) {
	        metadataText.setLength(metadataText.length() - 1);
	        metaTextView.setText(metadataText);
	        metaTextView.setVisibility(View.VISIBLE);
	        metaTextViewLabel.setVisibility(View.VISIBLE);
	      }
	    }

	    TextView contentsTextView = (TextView) findViewById(R.id.contents_text_view);
	    CharSequence displayContents = resultHandler.getDisplayContents();
	    contentsTextView.setText(displayContents);
	    // Crudely scale betweeen 22 and 32 -- bigger font for shorter text
	    int scaledSize = Math.max(22, 32 - displayContents.length() / 4);
	    contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
	    ViewGroup buttonView = (ViewGroup) findViewById(R.id.result_button_view);
	    buttonView.requestFocus();
	    TextView save_btn = (TextView) buttonView.getChildAt(0);
	    save_btn.setVisibility(View.VISIBLE);
	    if(SettingInfo.getInstance(this).getUploadStyle().equals("0")){    	
	    	save_btn.setText("保存");
	    }else if(SettingInfo.getInstance(this).getUploadStyle().equals("1")){
	    	save_btn.setText("上传");
	    }else{
	    	save_btn.setText(R.string.button_ok);
	    }
	    save_btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				handleOK();
			}
		});	    
	    TextView save_cancel = (TextView) buttonView.getChildAt(1);
	    save_cancel.setVisibility(View.VISIBLE);
	    save_cancel.setText(R.string.button_cancel);
	    save_cancel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				handler.sendEmptyMessageDelayed(R.id.restart_preview, BULK_MODE_SCAN_DELAY_MS);
				resetStatusView();
				}
		});	
	  }

	  // Briefly show the contents of the barcode, then handle the result outside Barcode Scanner.
	  private void handleDecodeExternally(Result rawResult, Bitmap barcode) {
	    viewfinderView.drawResultBitmap(barcode);
	    // Since this message will only be shown for a second, just tell the user what kind of
	    // barcode was found (e.g. contact info) rather than the full contents, which they won't
	    // have time to read.
	    ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
	    statusView.setText(getString(resultHandler.getDisplayTitle()));
	    if (source == Source.NATIVE_APP_INTENT) {
	      // Hand back whatever action they requested - this can be changed to Intents.Scan.ACTION when
	      // the deprecated intent is retired.
	      Intent intent = new Intent(getIntent().getAction());
	      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	      intent.putExtra(Intents.Scan.RESULT, rawResult.toString());
	      intent.putExtra(Intents.Scan.RESULT_FORMAT, rawResult.getBarcodeFormat().toString());
	      Message message = Message.obtain(handler, R.id.return_scan_result);
	      message.obj = intent;
	      handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
	    } else if (source == Source.PRODUCT_SEARCH_LINK) {
	      // Reformulate the URL which triggered us into a query, so that the request goes to the same
	      // TLD as the scan URL.
	      Message message = Message.obtain(handler, R.id.launch_product_query);
	      int end = sourceUrl.lastIndexOf("/scan");
	      message.obj = sourceUrl.substring(0, end) + "?q=" +
	          resultHandler.getDisplayContents().toString() + "&source=zxing";
	      handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
	    } else if (source == Source.ZXING_LINK) {
	      // Replace each occurrence of RETURN_CODE_PLACEHOLDER in the returnUrlTemplate
	      // with the scanned code. This allows both queries and REST-style URLs to work.
	      Message message = Message.obtain(handler, R.id.launch_product_query);
	      message.obj = returnUrlTemplate.replace(RETURN_CODE_PLACEHOLDER,
	          resultHandler.getDisplayContents().toString());
	      handler.sendMessageDelayed(message, INTENT_RESULT_DURATION);
	    }
	  }

	  /**
	   * We want the help screen to be shown automatically the first time a new version of the app is
	   * run. The easiest way to do this is to check android:versionCode from the manifest, and compare
	   * it to a value stored as a preference.
	   */
	  private boolean showHelpOnFirstLaunch() {
	    try {
	      PackageInfo info = getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
	      int currentVersion = info.versionCode;
	      // Since we're paying to talk to the PackageManager anyway, it makes sense to cache the app
	      // version name here for display in the about box later.
	      this.versionName = info.versionName;
	      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	      int lastVersion = prefs.getInt(PreferencesActivity.KEY_HELP_VERSION_SHOWN, 0);
	      if (currentVersion > lastVersion) {
	        prefs.edit().putInt(PreferencesActivity.KEY_HELP_VERSION_SHOWN, currentVersion).commit();
	        Intent intent = new Intent(this, HelpActivity.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	        // Show the default page on a clean install, and the what's new page on an upgrade.
	        String page = (lastVersion == 0) ? HelpActivity.DEFAULT_PAGE : HelpActivity.WHATS_NEW_PAGE;
	        intent.putExtra(HelpActivity.REQUESTED_PAGE_KEY, page);
	        startActivity(intent);
	        return true;
	      }
	    } catch (PackageManager.NameNotFoundException e) {
	      Log.w(TAG, e);
	    }
	    return false;
	  }

	  /**
	   * Creates the beep MediaPlayer in advance so that the sound can be triggered with the least
	   * latency possible.
	   */
	  private void initBeepSound() {
		  System.out.println("===========initBeepSound===============");
	    if (playBeep && mediaPlayer == null) {
	      // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
	      // so we now play on the music stream.
	      setVolumeControlStream(AudioManager.STREAM_MUSIC);
	      mediaPlayer = new MediaPlayer();
	      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	      mediaPlayer.setOnCompletionListener(beepListener);

	      AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
	      try {
	        mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
	            file.getLength());
	        file.close();
	        mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
	        mediaPlayer.prepare();
	      } catch (IOException e) {
	        mediaPlayer = null;
	      }
	    }
	  }

	  private void playBeepSoundAndVibrate() {
	    if (playBeep && mediaPlayer != null) {
	      mediaPlayer.start();
	    }
	    if (vibrate) {
	      Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	      vibrator.vibrate(VIBRATE_DURATION);
	    }
	  }

	  private void initCamera(SurfaceHolder surfaceHolder) {
	    try {
	      CameraManager.get().openDriver(surfaceHolder);
	    } catch (IOException ioe) {
	      Log.w(TAG, ioe);
	      displayFrameworkBugMessageAndExit();
	      return;
	    } catch (RuntimeException e) {
	      // Barcode Scanner has seen crashes in the wild of this variety:
	      // java.?lang.?RuntimeException: Fail to connect to camera service
	      Log.w(TAG, "Unexpected error initializating camera", e);
	      displayFrameworkBugMessageAndExit();
	      return;
	    }
	    if (handler == null) {
	      handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
	    }
//		AlertOk(Consts.test.toString()).show();
	  }

	  private void displayFrameworkBugMessageAndExit() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(getString(R.string.app_name));
	    builder.setMessage(getString(R.string.msg_camera_framework_bug));
	    builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
	    builder.setOnCancelListener(new FinishListener(this));
	    builder.show();
	  }

	  private void resetStatusView() {
	    resultView.setVisibility(View.GONE);
	    statusView.setText(getStateString());
	    statusView.setVisibility(View.VISIBLE);
	    viewfinderView.setVisibility(View.VISIBLE);
	    lastResult = null;
	  }

	  public void drawViewfinder() {
	    viewfinderView.drawViewfinder();
	  }
	final int UPLOADMESSAGE=1000;
	final int UPLOADGROUP=2000;
	@Override
	public boolean handleMessage(Message msg) {
		switch(msg.what){
		case UPLOADMESSAGE:
			uploadMessage();
			break;
		case UPLOADGROUP:
			
			break;
		}
		return false;
	}
	}

