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
	
	ArrayList<HashMap<String, Object>> listItem;//声明一个动态数组的引用
	private EditText et1;
	private EditText et2;
	private Button   bt;
	SimpleAdapter    mSimpleAdapter;//声明一个SimpleAdapter的引用
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        bt = (Button) findViewById(R.id.bt);
        
        listItem = new ArrayList<HashMap<String, Object>>();
        
        mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据   
                R.layout.item,//每一行的布局
                //动态数组中的数据源的键对应到定义布局的View中        
                new String[] {"ItemImage","ItemTitle", "ItemText"},   
                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}  
            );
        
        setListAdapter(mSimpleAdapter);//绑定适配器
        //设置按钮监听事件
        bt.setOnClickListener(new OnClickListener() {
    
			@Override
			public void onClick(View v) {
		        String etTitle = et1.getText().toString();//取得EditText中的值
		        String etText = et2.getText().toString();
				addItem(etTitle, etText);//调用方法来将数据插入
				
			}
		});
    }
    /*添加一个用来为ListView增加数据的方法*/
    private void addItem(String text1, String text2)
    {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("ItemImage", R.drawable.icon);
    	map.put("ItemTitle", text1);
    	map.put("ItemText", text2);
    	listItem.add(map);
    	mSimpleAdapter.notifyDataSetChanged();//刷新数据
    }
}