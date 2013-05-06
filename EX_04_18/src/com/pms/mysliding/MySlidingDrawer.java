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
	 /*ͼ����Դ���飬Ԥ����ͼ����Դ*/
	 private int[] icons={R.drawable.browser,R.drawable.gallery,
	                       R.drawable.camera,R.drawable.email,
	                       R.drawable.music,R.drawable.downloads,
	                       R.drawable.phone,R.drawable.message,R.drawable.setting};
	 /*���ֱ�ǩ����*/
	 private String[] items={"�����","ͼƬ","���","�ʼ�","����","����","����","��Ϣ","����"};
	    
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
       /*ȡ�ø����ؼ�����*/
       gv = (GridView)findViewById(R.id.myContent); 
       sd = (SlidingDrawer)findViewById(R.id.sd);
       iv=(ImageView)findViewById(R.id.iv);
       //�õ�MyAdapter����
       MyAdapter adapter=new MyAdapter(this,items,icons);
       gv.setAdapter(adapter);//��MyAdapter����
       //������
       sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()
       {
         @Override
         public void onDrawerOpened()
         {
           iv.setImageResource(R.drawable.close);//��Ӧ�������¼� ����ͼƬ��Ϊ���µ�
         }
       });
       //�س���
       sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener()
       {
         @Override
         public void onDrawerClosed()
         {
           iv.setImageResource(R.drawable.open);//��Ӧ�س����¼�
         }
       });
   }
}