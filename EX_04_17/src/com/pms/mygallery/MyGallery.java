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
        // 为gallery对象绑定自定义的适配器
        g.setAdapter(new MyImageAdapter(this));
        
        // 设置点击事件
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                setTitle("你点击了第" + position + "张图片");//点击后设置标题为相应的内容
            }
        });
    }
}