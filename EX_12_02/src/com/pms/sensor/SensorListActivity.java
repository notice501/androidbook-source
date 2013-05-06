package com.pms.sensor;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;

public class SensorListActivity extends Activity {
	private ListView localListView=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //生成传感器管理实例
        List<Sensor> sensorList=sm.getSensorList(Sensor.TYPE_ALL);//获取系统所有支持的传感器
        MyListAdapter adapter=new MyListAdapter(this, sensorList);//构造自定义数据桥接类
        localListView=new ListView(this);//new 一个列表控件
        localListView.setAdapter(adapter);//将自定义的数据桥接类,连接绑定到列表
        setContentView(localListView);//设定界面布局为当前的列表
        
    }
}