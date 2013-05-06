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
	//����tab��ʾ����
	private static final String[] TAB_NAMES = {
		
		"���ĸ�",
		"��һ��",
		"�ڶ���",
		"������",
	};//ע�⿪ʼ��λ�ڵ�2�����棬������������˳������Եĸı�
	
	/*����4�����Բ��ֶ���*/
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
		textAdapter = new TabAdapter(this, Arrays.asList(TAB_NAMES));//�õ�TabAdapter����
		gallery.setAdapter(textAdapter);//��������
		gallery.setSelection(34);//����������Tab���Լ���һ�£�����ߵ���΢��һ�㣬��Ҫһ���ͻ���ͷ
		/*�õ����ֶ���*/
        mTabLayout_One = (LinearLayout) this.findViewById( R.id.TabLayout_One );
        mTabLayout_Two = (LinearLayout) this.findViewById( R.id.TabLayout_Two );
        mTabLayout_Three = (LinearLayout) this.findViewById( R.id.TabLayout_Three );
        mTabLayout_Four = (LinearLayout) this.findViewById( R.id.TabLayout_Four );
		/*��һ��tab��Ӧ���ֶ������ò��ֶ���ʾ������������*/
        mTabLayout_One.setVisibility( View.GONE );
        mTabLayout_Two.setVisibility( View.VISIBLE );
        mTabLayout_Three.setVisibility( View.GONE );
        mTabLayout_Four.setVisibility( View.GONE );
        
        /*���õ���¼���ʵ���л�*/
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//�õ�TabAdapter����
				TabAdapter adapter = (TabAdapter)parent.getAdapter();
				adapter.setSelectedTab(position);//����ѡ�е�tab
				/*��position����tab����ȡ�࣬�ֱ�������Ӧ�Ĳ��ֿɼ�*/
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