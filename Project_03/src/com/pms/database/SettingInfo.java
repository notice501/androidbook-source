package com.pms.database;

import java.util.Vector;

import com.pms.network.Consts;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingInfo {
	final String SETTINGINFO="SETTINGINFO";
	/*********服务器IP*************/
	final String SERVICEIP="SERVICEIP";
	/*********服务器端口*****************/
	final String SERVICEPORT="SERVICEPORT";
	SharedPreferences sp=null;
	static SettingInfo setInfo;
	public SettingInfo(Context context){
		sp=context.getSharedPreferences(SETTINGINFO, 0);
		if(sp.getString(SERVICEIP, "")==null ||sp.getString(SERVICEIP, "").equals("")){
			init();
		}
	}
	private void init(){
		SharedPreferences.Editor editor = sp.edit();
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
	
}
