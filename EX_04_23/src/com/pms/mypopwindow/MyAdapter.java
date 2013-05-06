package com.pms.mypopwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter 
{ 
  private Context _ct;
  private String[] _items;
  private int[] _icons;

  public MyAdapter(Context ct,String[] items,int[] icons) 
  {
    _ct=ct;
    _items=items;
    _icons=icons;
  }

  @Override
  public int getCount()
  {
    return _items.length;
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
    LayoutInflater factory = LayoutInflater.from(_ct);
    View v = (View) factory.inflate(R.layout.gv, null);//绑定自定义的layout
    ImageView iv = (ImageView) v.findViewById(R.id.icon);
    TextView tv = (TextView) v.findViewById(R.id.text);
    iv.setImageResource(_icons[position]);
    tv.setText(_items[position]);
    return v;
  } 
} 

