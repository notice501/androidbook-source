package com.pms.myradiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MyRadioButton extends Activity {
	/*声明各个控件的引用*/
	private RadioGroup  rg;
	private RadioButton rb1;
	private TextView    tv;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*得到各个控件的引用*/
        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        tv = (TextView) findViewById(R.id.tv);
        
        /*为RadioGroup添加监听事件*/
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {       	
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == rb1.getId())
				{
					tv.setText("回答正确！");
				}
				else
				{
					tv.setText("回答错误，赶紧把书翻回去重学！");
				}
			}
		});
        
    }
}