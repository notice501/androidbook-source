package com.pms.myradiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MyRadioButton extends Activity {
	/*���������ؼ�������*/
	private RadioGroup  rg;
	private RadioButton rb1;
	private TextView    tv;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*�õ������ؼ�������*/
        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        tv = (TextView) findViewById(R.id.tv);
        
        /*ΪRadioGroup��Ӽ����¼�*/
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {       	
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == rb1.getId())
				{
					tv.setText("�ش���ȷ��");
				}
				else
				{
					tv.setText("�ش���󣬸Ͻ����鷭��ȥ��ѧ��");
				}
			}
		});
        
    }
}