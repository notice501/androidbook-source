package com.pms.mygridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter 
{ 
  private Context _ct;//上下文
  private String[] _items;//文字标题数组
  private int[] _icons;//图标数组

  /*构造函数*/
  public MyAdapter(Context ct,String[] items,int[] icons) 
  {
    _ct=ct;
    _items=items;
    _icons=icons;
  }

  @Override
  public int getCount()
  {
    return _items.length;//返回数组长度
  }

  @Override
  public Object getItem(int arg0)
  {
    return _items[arg0];
  }

  @Override
  public long getItemId(int position)
  {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    LayoutInflater factory = LayoutInflater.from(_ct);//导入布局
    View v = (View) factory.inflate(R.layout.gv, null);//绑定自定义的layout
    ImageView iv = (ImageView) v.findViewById(R.id.icon);
    TextView tv = (TextView) v.findViewById(R.id.text);
    iv.setImageResource(_icons[position]);//设置图片
    tv.setText(_items[position]);//设置标题
    return v;
  } 
} 

