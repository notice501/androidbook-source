package com.pms.mylistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyListView extends Activity {
	
    private static final String[] strs = new String[] {
    	"first", "second", "third", "fourth", "fifth"
    };//定义一个String数组用来显示ListView的内容
    private ListView lv;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用
        /*为ListView设置Adapter来绑定数据*/
        lv.setAdapter(new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1,strs));
        
        /*以下为三种其他的显示模式*/
        /*
        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, strs));
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        */
        /*
        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, strs));
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        */  
        /*
        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, strs));
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        */
        /*绑定点击监听器*/
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					//点击后在标签上显示点击了第几行
					setTitle("你点击了第"+arg2+"行");
			}
		});
    }
}