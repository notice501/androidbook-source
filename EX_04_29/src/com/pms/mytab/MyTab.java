package com.pms.mytab;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class MyTab extends TabActivity {
	
	//声明TabHost对象
	private TabHost mTabHost;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //取得TabHost对象
        mTabHost=this.getTabHost();
        /* 通过addTab添加tab，参数为TabSpec对象实例，再通过TabSpec的方法设置该TabSpec对象的标签，图标和显示的内容*/
        mTabHost.addTab(mTabHost.newTabSpec("tab_1")//创建一个TabSpec对象，设置它的tag，该tag即为标签切换事件中的tabId
        	.setIndicator("类别",getResources().getDrawable(R.drawable.cat))//设置TabWidget的文字和标签
        	.setContent(R.id.tv1));//设置该tab对应的内容
        mTabHost.addTab(mTabHost.newTabSpec("tab_2")
        	.setIndicator("下载", getResources().getDrawable(R.drawable.download))
        	.setContent(R.id.tv2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_3")
        	.setIndicator("刷新", getResources().getDrawable(R.drawable.redo))
        	.setContent(R.id.tv3));
        
        mTabHost.setBackgroundColor(0xFF434343); //设置TabHost的背景颜色
        mTabHost.setCurrentTab(0);//设置初始选中的tab
        
        
        /* 监听标签切换事件 */
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				//切换标签即弹出Toast消息
				Toast.makeText(MyTab.this, "当前选中了" + tabId + "标签", Toast.LENGTH_SHORT)
				.show();
			}
		});
        
        
        
        
    }
}