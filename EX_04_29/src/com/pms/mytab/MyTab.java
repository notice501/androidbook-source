package com.pms.mytab;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class MyTab extends TabActivity {
	
	//����TabHost����
	private TabHost mTabHost;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //ȡ��TabHost����
        mTabHost=this.getTabHost();
        /* ͨ��addTab���tab������ΪTabSpec����ʵ������ͨ��TabSpec�ķ������ø�TabSpec����ı�ǩ��ͼ�����ʾ������*/
        mTabHost.addTab(mTabHost.newTabSpec("tab_1")//����һ��TabSpec������������tag����tag��Ϊ��ǩ�л��¼��е�tabId
        	.setIndicator("���",getResources().getDrawable(R.drawable.cat))//����TabWidget�����ֺͱ�ǩ
        	.setContent(R.id.tv1));//���ø�tab��Ӧ������
        mTabHost.addTab(mTabHost.newTabSpec("tab_2")
        	.setIndicator("����", getResources().getDrawable(R.drawable.download))
        	.setContent(R.id.tv2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_3")
        	.setIndicator("ˢ��", getResources().getDrawable(R.drawable.redo))
        	.setContent(R.id.tv3));
        
        mTabHost.setBackgroundColor(0xFF434343); //����TabHost�ı�����ɫ
        mTabHost.setCurrentTab(0);//���ó�ʼѡ�е�tab
        
        
        /* ������ǩ�л��¼� */
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				//�л���ǩ������Toast��Ϣ
				Toast.makeText(MyTab.this, "��ǰѡ����" + tabId + "��ǩ", Toast.LENGTH_SHORT)
				.show();
			}
		});
        
        
        
        
    }
}