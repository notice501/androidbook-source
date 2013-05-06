package com.pms.mylistactivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;

public class MyListActivity extends ListActivity {
	
	ArrayList<HashMap<String, Object>> listItem;//����һ����̬���������
	private EditText et1;
	private EditText et2;
	private Button   bt;
	SimpleAdapter    mSimpleAdapter;//����һ��SimpleAdapter������
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        bt = (Button) findViewById(R.id.bt);
        
        listItem = new ArrayList<HashMap<String, Object>>();
        
        mSimpleAdapter = new SimpleAdapter(this,listItem,//��Ҫ�󶨵�����   
                R.layout.item,//ÿһ�еĲ���
                //��̬�����е�����Դ�ļ���Ӧ�����岼�ֵ�View��        
                new String[] {"ItemImage","ItemTitle", "ItemText"},   
                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}  
            );
        
        setListAdapter(mSimpleAdapter);//��������
        //���ð�ť�����¼�
        bt.setOnClickListener(new OnClickListener() {
    
			@Override
			public void onClick(View v) {
		        String etTitle = et1.getText().toString();//ȡ��EditText�е�ֵ
		        String etText = et2.getText().toString();
				addItem(etTitle, etText);//���÷����������ݲ���
				
			}
		});
    }
    /*���һ������ΪListView�������ݵķ���*/
    private void addItem(String text1, String text2)
    {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("ItemImage", R.drawable.icon);
    	map.put("ItemTitle", text1);
    	map.put("ItemText", text2);
    	listItem.add(map);
    	mSimpleAdapter.notifyDataSetChanged();//ˢ������
    }
}