package com.pms.myshared;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MySharedPrefereces extends Activity {
	
	private EditText et_name, et_pwd;
	private TextView count;
	private SharedPreferences sp;//����SharedPreferences����
	private int ct;//��������
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*�õ������ؼ�����*/
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        count = (TextView) findViewById(R.id.tv_count);
        

        //ȡ��SharedPreferences����
        sp = getSharedPreferences("my_data", Context.MODE_PRIVATE);
        //��my_data�ļ���ȡ����Ӧ�ļ���Ӧ��ֵ��Ϊ�����ʼ��Ϊ�ڶ���������ֵ
        String name = sp.getString("name", "");
        String pwd = sp.getString("pwd", "");
        ct = sp.getInt("count", 0);
        
        //���ø����ؼ���ʾ������
        count.setText("���ǵ�" + ct +"�δ򿪸�Ӧ��");
        et_name.setText(name);
        et_pwd.setText(pwd);
        
    }
    /*��onPause�б�������*/
	@Override
	protected void onPause() {
		super.onPause();
		/*ȡ������������*/
        String etname = et_name.getText().toString();
        String etpwd = et_pwd.getText().toString();
        //ȡ��SharedPreferences.Editor����
		SharedPreferences.Editor editor = sp.edit();
		//�洢����
		editor.putString("name", etname);
		editor.putString("pwd", etpwd);
		editor.putInt("count", ++ct);
		//�ύ
		editor.commit();
		
	}
}