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
	//存放图标的数组
	private int[] icons={R.drawable.desktop_camera,R.drawable.desktop_upload,
            R.drawable.desktop_newblog,R.drawable.desktop_status,
            R.drawable.desktop_newsfeed,R.drawable.desktop_profile,
            R.drawable.desktop_friend,R.drawable.desktop_places,R.drawable.desktop_notice,R.drawable.desktop_message};
	//存放标题的数组
	private String[] items={"拍照上传","传照片","写日志","发状态","新鲜事","个人主页","好友","地点","消息","站内信"};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MyAdapter adapter=new MyAdapter(this,items,icons);//实现MyAdapter的构造函数
        
        gridview = (GridView) findViewById(R.id.gridview);//得到GridView对象
        gridview.setAdapter(adapter);//绑定适配器
        
        //添加点击事件，只添加了3个，不一一添加了
        gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2)
				{
				case 0:
					Log.v("MyGridView", "你点击了拍照上传");//在LogCat中输出信息
					break;
				case 1:
					Log.v("MyGridView", "你点击了传照片");
					break;
				case 2:
					Log.v("MyGridView", "你点击了写日志");
					break;
				}
				
			}
		});
        
    }
}