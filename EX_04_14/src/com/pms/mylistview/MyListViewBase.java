package com.pms.mylistview;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyListViewBase extends Activity {
	
	private ListView lv;
	/*定义一个动态数组*/
    ArrayList<HashMap<String, Object>> listItem;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lv = (ListView) findViewById(R.id.lv);
        MyAdapter mAdapter = new MyAdapter(this);//得到一个MyAdapter对象
        lv.setAdapter(mAdapter);//为ListView绑定Adapter
        /*为ListView添加点击事件*/
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.v("MyListViewBase", "你点击了ListView条目" + arg2);//在LogCat中输出信息
				
			}
		});
        
    }
    /*添加一个得到数据的方法，方便使用*/
    private ArrayList<HashMap<String, Object>> getDate(){
    	
    	 ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    	 /*为动态数组添加数据*/
    	 for(int i=0;i<30;i++)  
         {  
             HashMap<String, Object> map = new HashMap<String, Object>();  
             map.put("ItemTitle", "第"+i+"行");  
             map.put("ItemText", "这是第"+i+"行");  
             listItem.add(map);  
         } 
		return listItem;
    	
    }
    /*
     * 新建一个类继承BaseAdapter，实现视图与数据的绑定
     */
    private class MyAdapter extends BaseAdapter {
    	
        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        /*构造函数*/
        public MyAdapter(Context context) {
        	this.mInflater = LayoutInflater.from(context);
        }

		@Override
		public int getCount() {
			
			return getDate().size();//返回数组的长度
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		/*书中详细解释该方法*/
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			 ViewHolder holder;
			 //观察convertView随ListView滚动情况
			 Log.v("MyListViewBase", "getView " + position + " " + convertView);
			 if (convertView == null) {
	                convertView = mInflater.inflate(R.layout.item,
	                        null);
	                holder = new ViewHolder();
	                /*得到各个控件的对象*/
	                holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
	                holder.text = (TextView) convertView.findViewById(R.id.ItemText);
	                holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
	                convertView.setTag(holder);//绑定ViewHolder对象
	            }
	            else{
	                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
	            }
			 /*设置TextView显示的内容为我们存放在动态数组中的数据*/
			 holder.title.setText(getDate().get(position).get("ItemTitle").toString());
			 holder.text.setText(getDate().get(position).get("ItemText").toString());
			 
			 /*为Button添加点击事件*/
			 holder.bt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.v("MyListViewBase", "你点击了按钮" + position);//打印Button的点击信息
					
				}
			});
			 
			return convertView;
		}
    	
    }
    /*存放控件*/
    public final class ViewHolder{
    	public TextView title;
    	public TextView text;
    	public Button   bt;
    }
}