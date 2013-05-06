package com.pms.gtab;

import java.util.Collections;
import java.util.List;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;

public class TabAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<String> mList;//�������tab�ַ�
	private int mSelectedTab;//��ʾ��ǰѡ�е�tab

	/*������*/
	public TabAdapter(Context context, List<String> list) {
		mContext = context;
	  /*ʹ��attrs��� <declare-styleable>����*/
		TypedArray a = context.obtainStyledAttributes(R.styleable.Gallery);
		a.recycle();//�ظ�ʹ�ö����styleable����
		if (list == null)
			list = Collections.emptyList();
		mList = list; 
	}
	
	//����ѡ�е�tab
	public void setSelectedTab(int tab) {
		if (tab != mSelectedTab) {
			mSelectedTab = tab;
			notifyDataSetChanged();//ˢ��
		}
	}
	
	@Override
	public int getCount() {
		return  Integer.MAX_VALUE;//�������ֵ
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);//����tab�ַ�
	}

	@Override
	public long getItemId(int position) {
		return position;//����tab��id
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text = null;//����ֻ��һ��TextView�����Ը�����Ҫ������
		if (convertView == null ) {
			 text = new TextView(mContext);
		} else {
			text = (TextView) convertView;
		}
		
		text.setTextColor(Color.WHITE);//����������ɫ
		text.setText(mList.get(position % mList.size()));//ѭ��ȡ��������ʾ����
		/*����layout���*/
		text.setLayoutParams(new Gallery.LayoutParams(102, 40));
		/*����Ϊ������ʾ*/
		text.setGravity(Gravity.CENTER);
		
		/*
		 * ����ѡ�е�Tab������һ��ѡ�еı���
		 */
		if (position == mSelectedTab)
			text.setBackgroundResource(R.drawable.tab_button_select);
		else
			text.setBackgroundResource(R.drawable.tab_button_unselect);//δѡ�б���
		
		return text;//�����ı�
	}

}
