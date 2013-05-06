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
	/*����һ����̬����*/
    ArrayList<HashMap<String, Object>> listItem;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lv = (ListView) findViewById(R.id.lv);
        MyAdapter mAdapter = new MyAdapter(this);//�õ�һ��MyAdapter����
        lv.setAdapter(mAdapter);//ΪListView��Adapter
        /*ΪListView��ӵ���¼�*/
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.v("MyListViewBase", "������ListView��Ŀ" + arg2);//��LogCat�������Ϣ
				
			}
		});
        
    }
    /*���һ���õ����ݵķ���������ʹ��*/
    private ArrayList<HashMap<String, Object>> getDate(){
    	
    	 ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    	 /*Ϊ��̬�����������*/
    	 for(int i=0;i<30;i++)  
         {  
             HashMap<String, Object> map = new HashMap<String, Object>();  
             map.put("ItemTitle", "��"+i+"��");  
             map.put("ItemText", "���ǵ�"+i+"��");  
             listItem.add(map);  
         } 
		return listItem;
    	
    }
    /*
     * �½�һ����̳�BaseAdapter��ʵ����ͼ�����ݵİ�
     */
    private class MyAdapter extends BaseAdapter {
    	
        private LayoutInflater mInflater;//�õ�һ��LayoutInfalter�����������벼��

        /*���캯��*/
        public MyAdapter(Context context) {
        	this.mInflater = LayoutInflater.from(context);
        }

		@Override
		public int getCount() {
			
			return getDate().size();//��������ĳ���
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		/*������ϸ���͸÷���*/
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			 ViewHolder holder;
			 //�۲�convertView��ListView�������
			 Log.v("MyListViewBase", "getView " + position + " " + convertView);
			 if (convertView == null) {
	                convertView = mInflater.inflate(R.layout.item,
	                        null);
	                holder = new ViewHolder();
	                /*�õ������ؼ��Ķ���*/
	                holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
	                holder.text = (TextView) convertView.findViewById(R.id.ItemText);
	                holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
	                convertView.setTag(holder);//��ViewHolder����
	            }
	            else{
	                holder = (ViewHolder)convertView.getTag();//ȡ��ViewHolder����
	            }
			 /*����TextView��ʾ������Ϊ���Ǵ���ڶ�̬�����е�����*/
			 holder.title.setText(getDate().get(position).get("ItemTitle").toString());
			 holder.text.setText(getDate().get(position).get("ItemText").toString());
			 
			 /*ΪButton��ӵ���¼�*/
			 holder.bt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.v("MyListViewBase", "�����˰�ť" + position);//��ӡButton�ĵ����Ϣ
					
				}
			});
			 
			return convertView;
		}
    	
    }
    /*��ſؼ�*/
    public final class ViewHolder{
    	public TextView title;
    	public TextView text;
    	public Button   bt;
    }
}