package com.pms.mygallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyGallery extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery g = (Gallery) findViewById(R.id.gallery);
        // Ϊgallery������Զ����������
        g.setAdapter(new MyImageAdapter(this));
        
        // ���õ���¼�
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                setTitle("�����˵�" + position + "��ͼƬ");//��������ñ���Ϊ��Ӧ������
            }
        });
    }
}