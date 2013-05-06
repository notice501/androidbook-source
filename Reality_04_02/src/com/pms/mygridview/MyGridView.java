package com.pms.mygridview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MyGridView extends Activity {
	
	private GridView gridview;
	//���ͼ�������
	private int[] icons={R.drawable.desktop_camera,R.drawable.desktop_upload,
            R.drawable.desktop_newblog,R.drawable.desktop_status,
            R.drawable.desktop_newsfeed,R.drawable.desktop_profile,
            R.drawable.desktop_friend,R.drawable.desktop_places,R.drawable.desktop_notice,R.drawable.desktop_message};
	//��ű��������
	private String[] items={"�����ϴ�","����Ƭ","д��־","��״̬","������","������ҳ","����","�ص�","��Ϣ","վ����"};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MyAdapter adapter=new MyAdapter(this,items,icons);//ʵ��MyAdapter�Ĺ��캯��
        
        gridview = (GridView) findViewById(R.id.gridview);//�õ�GridView����
        gridview.setAdapter(adapter);//��������
        
        //��ӵ���¼���ֻ�����3������һһ�����
        gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2)
				{
				case 0:
					Log.v("MyGridView", "�����������ϴ�");//��LogCat�������Ϣ
					break;
				case 1:
					Log.v("MyGridView", "�����˴���Ƭ");
					break;
				case 2:
					Log.v("MyGridView", "������д��־");
					break;
				}
				
			}
		});
        
    }
}