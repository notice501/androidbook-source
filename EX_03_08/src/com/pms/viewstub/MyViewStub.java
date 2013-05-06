package com.pms.viewstub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyViewStub extends Activity {
	
	private ViewStub mViewStub;
	private Button   showButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mViewStub = (ViewStub)findViewById(R.id.viewStub);//ʵ����ViewStub�ؼ���������Կ������Ǳ���ΪViewStub�趨id
        showButton = (Button)findViewById(R.id.show);
        
        /*Ϊ��ť��ӵ�������¼���������½ڻ����*/         				
        showButton.setOnClickListener(new OnClickListener(){
        	
        	@Override
        	public void onClick(View v) {
        		if (mViewStub != null) {  
                   mViewStub.inflate();  //���������ViewStub��ǩ������
                  }  
        		
        	}
        });
    }
}