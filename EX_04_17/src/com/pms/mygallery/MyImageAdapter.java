package com.pms.mygallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MyImageAdapter extends BaseAdapter {
	
    private Context mContext;
    
	int mGalleryItemBackground;//定义一个整型来存放gallery的背景资源id
	
	//定义数组存放图片资源id并取得图片资源
    private Integer[] mImageIds = {
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo5,
    };
    //构造器
    public MyImageAdapter(Context c) {
        mContext = c;
        /* 需要在res/values/attrs.xml 里的<declare-styleable> 标签里定义一个MyGallery来设置gallery的背景*/
        TypedArray a = c.obtainStyledAttributes(R.styleable.MyGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.MyGallery_android_galleryItemBackground, 0);//获得属性资源id
        a.recycle();//重复使用对象的styleable属性
    }
	@Override
	public int getCount() {
        return mImageIds.length;//返回图片数组长度
	}

	@Override
	public Object getItem(int arg0) {
        return arg0;
	}

	@Override
	public long getItemId(int position) {
        return position;//返回图像数组id
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView i = new ImageView(mContext);//生成ImageView对象
        i.setImageResource(mImageIds[position]);//设置图片
        i.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片拉伸格式为充满layout
        i.setLayoutParams(new Gallery.LayoutParams(136, 88));//设置layout的宽高
        
        // 设置gallery的item背景
        i.setBackgroundResource(mGalleryItemBackground);
        
        return i;//返回ImageView对象
	}

}
