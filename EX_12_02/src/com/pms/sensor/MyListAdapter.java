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
 * 自定义 列表布局,对于较复杂的布局,我们一般采用自定义的数据操纵者与UI绑定
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
			((TextView)itemView.findViewById(R.id.sensor_type)).setText("类型:"+sensor.getType());
			((TextView)itemView.findViewById(R.id.sensor_version)).setText("版本:"+sensor.getVersion());
			((TextView)itemView.findViewById(R.id.sensor_name)).setText(""+getSensorNameByType(sensor.getType()));
			((TextView)itemView.findViewById(R.id.sensor_max_range)).setText("最大值:"+sensor.getMaximumRange());
			((TextView)itemView.findViewById(R.id.sensor_power)).setText("功率:"+sensor.getPower());
			((TextView)itemView.findViewById(R.id.sensor_resolution)).setText("精度:"+sensor.getResolution());
			((TextView)itemView.findViewById(R.id.sensor_vendor)).setText("供应商:"+sensor.getVendor());
			return itemView;
		}else{
			return null;
		}
	}
	/**
	 * 返回Sensor类型
	 * @param sensorType
	 * @return
	 */
	private String getSensorNameByType(int sensorType){
		String name="";
		switch(sensorType){
		case Sensor.TYPE_ACCELEROMETER:
			name="加速器";
			break;
		case Sensor.TYPE_GYROSCOPE:
			name="陀螺仪";
			break;
		case Sensor.TYPE_LIGHT:
			name="光照";
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			name="磁力计";
			break;
		case Sensor.TYPE_ORIENTATION:
			name="方位传感器";
			break;
		case Sensor.TYPE_PROXIMITY:
			name="距离传感器";
			break;
		case Sensor.TYPE_TEMPERATURE:
			name="温度传感器";
			break;
		case Sensor.TYPE_PRESSURE:
			name="压力传感器";
			break;
		}
		return name;
	}

}
