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
    
	int mGalleryItemBackground;//����һ�����������gallery�ı�����Դid
	
	//����������ͼƬ��Դid��ȡ��ͼƬ��Դ
    private Integer[] mImageIds = {
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo5,
    };
    //������
    public MyImageAdapter(Context c) {
        mContext = c;
        /* ��Ҫ��res/values/attrs.xml ���<declare-styleable> ��ǩ�ﶨ��һ��MyGallery������gallery�ı���*/
        TypedArray a = c.obtainStyledAttributes(R.styleable.MyGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.MyGallery_android_galleryItemBackground, 0);//���������Դid
        a.recycle();//�ظ�ʹ�ö����styleable����
    }
	@Override
	public int getCount() {
        return mImageIds.length;//����ͼƬ���鳤��
	}

	@Override
	public Object getItem(int arg0) {
        return arg0;
	}

	@Override
	public long getItemId(int position) {
        return position;//����ͼ������id
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView i = new ImageView(mContext);//����ImageView����
        i.setImageResource(mImageIds[position]);//����ͼƬ
        i.setScaleType(ImageView.ScaleType.FIT_XY);//����ͼƬ�����ʽΪ����layout
        i.setLayoutParams(new Gallery.LayoutParams(136, 88));//����layout�Ŀ��
        
        // ����gallery��item����
        i.setBackgroundResource(mGalleryItemBackground);
        
        return i;//����ImageView����
	}

}
