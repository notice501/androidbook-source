package com.pms.gtab;

import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class Gtab extends Activity {
	
	private Gallery gallery;
	private TabAdapter textAdapter;
	//定义tab显示内容
	private static final String[] TAB_NAMES = {
		
		"第四个",
		"第一个",
		"第二个",
		"第三个",
	};//注意开始定位在第2个上面，所以这边数组的顺序针对性的改变
	
	/*声明4个线性布局对象*/
	private LinearLayout 	mTabLayout_One;
	private LinearLayout 	mTabLayout_Two;
	private LinearLayout 	mTabLayout_Three;
	private LinearLayout 	mTabLayout_Four;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gallery = (Gallery) findViewById(R.id.gallery);
		textAdapter = new TabAdapter(this, Arrays.asList(TAB_NAMES));//得到TabAdapter对象
		gallery.setAdapter(textAdapter);//绑定适配器
		gallery.setSelection(34);//这里根据你的Tab数自己算一下，让左边的稍微多一点，不要一滑就滑到头
		/*得到布局对象*/
        mTabLayout_One = (LinearLayout) this.findViewById( R.id.TabLayout_One );
        mTabLayout_Two = (LinearLayout) this.findViewById( R.id.TabLayout_Two );
        mTabLayout_Three = (LinearLayout) this.findViewById( R.id.TabLayout_Three );
        mTabLayout_Four = (LinearLayout) this.findViewById( R.id.TabLayout_Four );
		/*第一个tab对应布局二，设置布局二显示，其他的隐藏*/
        mTabLayout_One.setVisibility( View.GONE );
        mTabLayout_Two.setVisibility( View.VISIBLE );
        mTabLayout_Three.setVisibility( View.GONE );
        mTabLayout_Four.setVisibility( View.GONE );
        
        /*设置点击事件，实现切换*/
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//得到TabAdapter对象
				TabAdapter adapter = (TabAdapter)parent.getAdapter();
				adapter.setSelectedTab(position);//设置选中的tab
				/*对position根据tab长度取余，分别设置相应的布局可见*/
				switch(position %TAB_NAMES.length ){
				case 0:
					mTabLayout_One.setVisibility( View.VISIBLE );
	    			mTabLayout_Two.setVisibility( View.GONE );
	    			mTabLayout_Three.setVisibility( View.GONE );
	    			mTabLayout_Four.setVisibility( View.GONE );
	    			break;
				case 1:
					mTabLayout_One.setVisibility( View.GONE );
	    			mTabLayout_Two.setVisibility( View.VISIBLE );
	    			mTabLayout_Three.setVisibility( View.GONE );
	    			mTabLayout_Four.setVisibility( View.GONE );
	    			break;
				case 2:
					mTabLayout_One.setVisibility( View.GONE );
	    			mTabLayout_Two.setVisibility( View.GONE );
	    			mTabLayout_Three.setVisibility( View.VISIBLE );
	    			mTabLayout_Four.setVisibility( View.GONE );
	    			break;
				case 3:
					mTabLayout_One.setVisibility( View.GONE );
	    			mTabLayout_Two.setVisibility( View.GONE );
	    			mTabLayout_Three.setVisibility( View.GONE );
	    			mTabLayout_Four.setVisibility( View.VISIBLE );
				}

			}
			
		});
		
    }
}