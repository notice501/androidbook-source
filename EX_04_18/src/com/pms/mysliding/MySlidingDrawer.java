package com.pms.mysliding;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

public class MySlidingDrawer extends Activity {
	 private GridView gv;
	 private SlidingDrawer sd;
	 private ImageView iv;
	 /*图标资源数组，预加载图标资源*/
	 private int[] icons={R.drawable.browser,R.drawable.gallery,
	                       R.drawable.camera,R.drawable.email,
	                       R.drawable.music,R.drawable.downloads,
	                       R.drawable.phone,R.drawable.message,R.drawable.setting};
	 /*文字标签数组*/
	 private String[] items={"浏览器","图片","相机","邮件","音乐","下载","拨号","信息","设置"};
	    
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
       /*取得各个控件对象*/
       gv = (GridView)findViewById(R.id.myContent); 
       sd = (SlidingDrawer)findViewById(R.id.sd);
       iv=(ImageView)findViewById(R.id.iv);
       //得到MyAdapter对象
       MyAdapter adapter=new MyAdapter(this,items,icons);
       gv.setAdapter(adapter);//绑定MyAdapter对象
       //开抽屉
       sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()
       {
         @Override
         public void onDrawerOpened()
         {
           iv.setImageResource(R.drawable.close);//响应开抽屉事件 ，把图片设为向下的
         }
       });
       //关抽屉
       sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener()
       {
         @Override
         public void onDrawerClosed()
         {
           iv.setImageResource(R.drawable.open);//响应关抽屉事件
         }
       });
   }
}