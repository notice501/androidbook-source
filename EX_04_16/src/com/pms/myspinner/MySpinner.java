package com.pms.myspinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MySpinner extends Activity {
	
	private Spinner sp1;
	private Spinner sp2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sp1 = (Spinner) findViewById(R.id.sp);//得到Spinner对象
        /*导入资源文件中的数组，并且第三个参数设置为展开的Spinner格式*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.mobile_arry, android.R.layout.simple_spinner_item);
        /*Spinner中比较重要的方法，设置Spinner的展开格式，这里设置为带RaioButton格式*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);//绑定适配器
        /*添加条目点击事件*/
        sp1.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setTitle("Spinner1: 选择了第" + position + "个， id=" + id + 
                        		"内容为" + sp1.getSelectedItem().toString());//点击后设置标题内容
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        setTitle("Spinner1: 没有点击");//未点击设置标题内容
                    }
                });
        
        sp2 = (Spinner) findViewById(R.id.sp2);
        /*导入资源文件中的数组，并且第三个参数设置为展开的Spinner格式*/
        adapter = ArrayAdapter.createFromResource(
                this, R.array.phone_arry, android.R.layout.simple_spinner_item);
        /*Spinner中比较重要的方法，设置Spinner的展开格式,这里设置为只有文字格式*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp2.setAdapter(adapter);//绑定适配器
        /*添加条目点击事件*/
        sp2.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setTitle("Spinner2: 选择了第" + position + "个， id=" + id + 
                        		"内容为" + sp1.getSelectedItem().toString());//点击后设置标题内容
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                       //未点击的操作放在这里
                    }
                });
    }
}