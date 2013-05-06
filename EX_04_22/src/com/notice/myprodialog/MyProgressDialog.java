package com.notice.myprodialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyProgressDialog extends Activity {
	
	/*�����ؼ�����*/
	private Button bt1;
	private Button bt2;
	private ProgressDialog pd;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*�õ��ؼ�����*/
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        /*ΪButton�󶨵��������*/
        bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 // ����ProgressDialog����  
                pd = new ProgressDialog(MyProgressDialog.this);  
                // ���ý��������ΪԲ��
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
                // ���ý���������  
                pd.setTitle("����");  
                // ���ý����� ��ʾ��Ϣ  
                pd.setMessage("Բ�ν������Ի���");  
                // ���ý�����ͼ��  
                pd.setIcon(R.drawable.icon);  
                // ���ý������Ľ������Ƿ�ȷ�� 
                pd.setIndeterminate(true);  
                // ���öԻ����Ƿ���԰��˻ؼ�ȡ��  
                pd.setCancelable(true);
                // ����һ��Button
                pd.setButton("�ر�", new DialogInterface.OnClickListener(){
                	@Override
                	public void onClick(DialogInterface dialog, int which) {
                		pd.dismiss();//�رնԻ���
                		
                	}
                }
                	);
                // ����ʾ�Ի���  
                pd.show();  
            }  
		});
        /*ΪButton�󶨵��������*/
        bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ����ProgressDialog����  
                pd = new ProgressDialog(MyProgressDialog.this);  
                // ���ý��������Ϊˮƽ������
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
                // ���ý���������  
                pd.setTitle("����");  
                // ���ý�������ʾ��Ϣ 
                pd.setMessage("����һ�������ν������Ի���");  
                // ���ý�����ͼ��  
                pd.setIcon(R.drawable.icon);  
                // ���ý������Ƿ���ȷ   
                pd.setIndeterminate(false);  
                // ���ý�����������  
                pd.setMax(100);
                // ���õײ����������                
                pd.setSecondaryProgress(50);
                // ���öԻ����Ƿ���԰��˻ؼ�ȡ��  
                pd.setCancelable(true);
                // ����һ��Button
                pd.setButton("�ر�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						pd.dismiss();//�رնԻ���
						
					}
				});
                // ��ProgressDialog��ʾ  
                pd.show();  
				
			}
        });
    }
}