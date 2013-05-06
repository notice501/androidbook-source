package com.pms.database;

import java.util.Vector;

import com.pms.database.SettingInfo;
import com.pms.network.Consts;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingInfo {
	final String SETTINGINFO="SETTINGINFO";
	static SettingInfo setInfo=null;
	/******扫码模式 单次/连续*******/
	final String OPERASTYLE="OPERASTYLE";
	/***默认分组**/
	final String DEFAULTGROUP="DEFAULTGROUP";
	/*****上传方式******/
	final String UPLOADSTYLE="UPLOADSTYLE";
	/*****存储方式*****/
	final String SAVESTYLE="SAVESTYLE";
	/****重复判断模式*****/
	final String JUDGEMODE="JUDGEMODE";
	/*********服务器IP*************/
	final String SERVICEIP="SERVICEIP";
	/*********服务器端口*****************/
	final String SERVICEPORT="SERVICEPORT";
	SharedPreferences sp=null;
	
	public SettingInfo(Context context){
		sp=context.getSharedPreferences(SETTINGINFO, 0);
		if(sp.getString(DEFAULTGROUP, "")==null ||sp.getString(DEFAULTGROUP, "").equals("")){
			init();
		}
	}
	private void init(){
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(DEFAULTGROUP, "默认");
		editor.putString(JUDGEMODE, "0");
		editor.putString(OPERASTYLE, "0");
		editor.putString(SAVESTYLE, "0");
		editor.putString(UPLOADSTYLE, "0");
		editor.putString(SERVICEIP, Consts.SERVICE_IP);
		editor.putString(SERVICEPORT, Consts.SERVICE_PORT+"");
		editor.commit();
	}
	public static SettingInfo getInstance(Context context){
		if(setInfo==null){
			setInfo=new SettingInfo(context);
		}
		return setInfo;
	}
	/**
	 * 字段和value同步，对应
	 * @param Field
	 * @param value
	 */
	public void saveSetting(Vector<String> Field,Vector<String> value){
		SharedPreferences.Editor editor = sp.edit();
		for(int i=0;i<Field.size();i++){
			
			editor.putString(Field.elementAt(i), value.elementAt(i));
		}
		editor.commit();
	}
	
	public void setService_PORT(String value){
		sp.edit().putString(SERVICEPORT, value).commit();
	}
	public String getService_PORT(){
		return sp.getString(SERVICEPORT, "");
	}
	
	public void setService_IP(String value){
		sp.edit().putString(SERVICEIP, value).commit();
	}
	public String getService_IP(){
		return sp.getString(SERVICEIP, "");
	}
	public String getUploadStyle(){
		return sp.getString(UPLOADSTYLE, "");		
	}
	public void setUploadStyle(String value){
		sp.edit().putString(UPLOADSTYLE, value).commit();
	}
	public String  getSaveStyle(){
		return sp.getString(SAVESTYLE, "");
	}
	public void setSaveStyle(String value){
		sp.edit().putString(SAVESTYLE, value).commit();
	}
	public String getDefaultGroup(){
		return sp.getString(this.DEFAULTGROUP, "");
	}
	public void setDefaultGroup( String value){
		sp.edit().putString(this.DEFAULTGROUP, value).commit();
	}
	/**重复时*0 提示  1 新增   2替换**********/
	public String getJudgeMode(){
		return sp.getString(JUDGEMODE, "");
	}
	public void setJudegMode(String value){
		sp.edit().putString(JUDGEMODE, value).commit();
	}
	public String getOperaStyle(){
		return sp.getString(OPERASTYLE, "");
	}
	public void setOperaStyle(String value){
		sp.edit().putString(OPERASTYLE, value).commit();
	}
	
}
