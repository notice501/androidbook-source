package com.pms.mycheckbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MyCheckBox extends Activity {
	
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	private TextView tv;  //用来显示题目及用户的选择情况
	private Button   bt;
	private TextView tv_result;
	int sum = 0;          //用来对勾选的项目计数
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*得到各个控件的引用*/
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        tv  = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);
        tv_result = (TextView) findViewById(R.id.tvresult);
        
        /*为每个CheckBox绑定监听器来获得选择情况,根据选择情况设置显示文本内容，并且为选中的计数*/
        cb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(cb1.isChecked())
				{
					tv.setText("你刚刚选择了"+ cb1.getText());
					sum++;
				}
				else
				{
					tv.setText("你刚刚取消了选择"+cb1.getText());
					sum--;
				}
				
			}
		});
        
        cb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(cb2.isChecked())
				{
					tv.setText("你刚刚选择了"+ cb2.getText());
					sum++;
				}
				else
				{
					tv.setText("你刚刚取消了选择"+cb2.getText());
					sum--;
				}
				
			}
		});
        
        cb3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(cb3.isChecked())
				{
					tv.setText("你刚刚选择了"+ cb3.getText());
					sum++;
				}
				else
				{
					tv.setText("你刚刚取消了选择"+cb3.getText());
					sum--;
				}
				
			}
		});
        
        /*为Button绑定监听器*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String result = "";  //用来存放结果
				/*取出选中的内容，换行显示*/
				if(cb1.isChecked()) result += cb1.getText() + "\n";
				if(cb2.isChecked()) result += cb2.getText() + "\n";
				if(cb3.isChecked()) result += cb3.getText() + "\n";
				if(result!="")//判断是否为空值
				{
					tv_result.setText("你的目标是:"  + "\n" + result + "\n" + "你一共选择了" + sum + "个选项");
				}
				else
				{
					tv_result.setText("选择不能为空！年轻人要有点追求啊！");
				}
				
				
			}
		});
        
    }
}