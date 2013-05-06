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
	private TextView tv;  //������ʾ��Ŀ���û���ѡ�����
	private Button   bt;
	private TextView tv_result;
	int sum = 0;          //�����Թ�ѡ����Ŀ����
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*�õ������ؼ�������*/
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        tv  = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);
        tv_result = (TextView) findViewById(R.id.tvresult);
        
        /*Ϊÿ��CheckBox�󶨼����������ѡ�����,����ѡ�����������ʾ�ı����ݣ�����Ϊѡ�еļ���*/
        cb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(cb1.isChecked())
				{
					tv.setText("��ո�ѡ����"+ cb1.getText());
					sum++;
				}
				else
				{
					tv.setText("��ո�ȡ����ѡ��"+cb1.getText());
					sum--;
				}
				
			}
		});
        
        cb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(cb2.isChecked())
				{
					tv.setText("��ո�ѡ����"+ cb2.getText());
					sum++;
				}
				else
				{
					tv.setText("��ո�ȡ����ѡ��"+cb2.getText());
					sum--;
				}
				
			}
		});
        
        cb3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(cb3.isChecked())
				{
					tv.setText("��ո�ѡ����"+ cb3.getText());
					sum++;
				}
				else
				{
					tv.setText("��ո�ȡ����ѡ��"+cb3.getText());
					sum--;
				}
				
			}
		});
        
        /*ΪButton�󶨼�����*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String result = "";  //������Ž��
				/*ȡ��ѡ�е����ݣ�������ʾ*/
				if(cb1.isChecked()) result += cb1.getText() + "\n";
				if(cb2.isChecked()) result += cb2.getText() + "\n";
				if(cb3.isChecked()) result += cb3.getText() + "\n";
				if(result!="")//�ж��Ƿ�Ϊ��ֵ
				{
					tv_result.setText("���Ŀ����:"  + "\n" + result + "\n" + "��һ��ѡ����" + sum + "��ѡ��");
				}
				else
				{
					tv_result.setText("ѡ����Ϊ�գ�������Ҫ�е�׷�󰡣�");
				}
				
				
			}
		});
        
    }
}