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
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //���ɴ���������ʵ��
        List<Sensor> sensorList=sm.getSensorList(Sensor.TYPE_ALL);//��ȡϵͳ����֧�ֵĴ�����
        MyListAdapter adapter=new MyListAdapter(this, sensorList);//�����Զ��������Ž���
        localListView=new ListView(this);//new һ���б�ؼ�
        localListView.setAdapter(adapter);//���Զ���������Ž���,���Ӱ󶨵��б�
        setContentView(localListView);//�趨���沼��Ϊ��ǰ���б�
        
    }
}