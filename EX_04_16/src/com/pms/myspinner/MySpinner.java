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
        
        sp1 = (Spinner) findViewById(R.id.sp);//�õ�Spinner����
        /*������Դ�ļ��е����飬���ҵ�������������Ϊչ����Spinner��ʽ*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.mobile_arry, android.R.layout.simple_spinner_item);
        /*Spinner�бȽ���Ҫ�ķ���������Spinner��չ����ʽ����������Ϊ��RaioButton��ʽ*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);//��������
        /*�����Ŀ����¼�*/
        sp1.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setTitle("Spinner1: ѡ���˵�" + position + "���� id=" + id + 
                        		"����Ϊ" + sp1.getSelectedItem().toString());//��������ñ�������
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        setTitle("Spinner1: û�е��");//δ������ñ�������
                    }
                });
        
        sp2 = (Spinner) findViewById(R.id.sp2);
        /*������Դ�ļ��е����飬���ҵ�������������Ϊչ����Spinner��ʽ*/
        adapter = ArrayAdapter.createFromResource(
                this, R.array.phone_arry, android.R.layout.simple_spinner_item);
        /*Spinner�бȽ���Ҫ�ķ���������Spinner��չ����ʽ,��������Ϊֻ�����ָ�ʽ*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp2.setAdapter(adapter);//��������
        /*�����Ŀ����¼�*/
        sp2.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setTitle("Spinner2: ѡ���˵�" + position + "���� id=" + id + 
                        		"����Ϊ" + sp1.getSelectedItem().toString());//��������ñ�������
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                       //δ����Ĳ�����������
                    }
                });
    }
}