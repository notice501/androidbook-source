package com.pms.sensor;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * �Զ��� �б���,���ڽϸ��ӵĲ���,����һ������Զ�������ݲ�������UI��
 * 
 */
public class MyListAdapter extends BaseAdapter{
	List<Sensor> sensorListV=null;
	Context context;
	public MyListAdapter(Context context,List<Sensor>sensorListV){
		this.context=context;
		this.sensorListV=sensorListV;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sensorListV.size();
	}

	@Override
	public Sensor getItem(int position) {
		// TODO Auto-generated method stub
		return sensorListV.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(sensorListV!=null){
			View itemView=LinearLayout.inflate(context, R.layout.sensor_list, null);
			Sensor sensor=sensorListV.get(position);
			((TextView)itemView.findViewById(R.id.sensor_type)).setText("����:"+sensor.getType());
			((TextView)itemView.findViewById(R.id.sensor_version)).setText("�汾:"+sensor.getVersion());
			((TextView)itemView.findViewById(R.id.sensor_name)).setText(""+getSensorNameByType(sensor.getType()));
			((TextView)itemView.findViewById(R.id.sensor_max_range)).setText("���ֵ:"+sensor.getMaximumRange());
			((TextView)itemView.findViewById(R.id.sensor_power)).setText("����:"+sensor.getPower());
			((TextView)itemView.findViewById(R.id.sensor_resolution)).setText("����:"+sensor.getResolution());
			((TextView)itemView.findViewById(R.id.sensor_vendor)).setText("��Ӧ��:"+sensor.getVendor());
			return itemView;
		}else{
			return null;
		}
	}
	/**
	 * ����Sensor����
	 * @param sensorType
	 * @return
	 */
	private String getSensorNameByType(int sensorType){
		String name="";
		switch(sensorType){
		case Sensor.TYPE_ACCELEROMETER:
			name="������";
			break;
		case Sensor.TYPE_GYROSCOPE:
			name="������";
			break;
		case Sensor.TYPE_LIGHT:
			name="����";
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			name="������";
			break;
		case Sensor.TYPE_ORIENTATION:
			name="��λ������";
			break;
		case Sensor.TYPE_PROXIMITY:
			name="���봫����";
			break;
		case Sensor.TYPE_TEMPERATURE:
			name="�¶ȴ�����";
			break;
		case Sensor.TYPE_PRESSURE:
			name="ѹ��������";
			break;
		}
		return name;
	}

}
