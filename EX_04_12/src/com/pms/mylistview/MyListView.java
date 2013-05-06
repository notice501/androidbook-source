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
    };//����һ��String����������ʾListView������
    private ListView lv;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lv = (ListView) findViewById(R.id.lv);//�õ�ListView���������
        /*ΪListView����Adapter��������*/
        lv.setAdapter(new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1,strs));
        
        /*����Ϊ������������ʾģʽ*/
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
        /*�󶨵��������*/
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					//������ڱ�ǩ����ʾ����˵ڼ���
					setTitle("�����˵�"+arg2+"��");
			}
		});
    }
}