package com.pms.demo;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem>{
	 private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();  
	    private Context context;  
	public MyItemizedOverlay(Context context,Drawable defaultMarker) {
		super(defaultMarker);
		this.context=context;
		
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
        // Projection接口用于屏幕像素点坐标系统和地球表面经纬度点坐标系统之间的变换  
        Projection projection = mapView.getProjection();  
        // 遍历所有的OverlayItem  
        for (int index = this.size() - 1; index >= 0; index--) {  
            // 得到给定索引的item  
            OverlayItem overLayItem = getItem(index);  
  
            // 把经纬度变换到相对于MapView左上角的屏幕像素坐标  
            Point point = projection.toPixels(overLayItem.getPoint(), null);  
  
            Paint paintText = new Paint();  
            paintText.setColor(Color.RED);  
            paintText.setTextSize(13);  
            // 绘制文本  
            canvas.drawText(overLayItem.getTitle(), point.x + 10, point.y - 15, paintText);  
        }
		
	}

	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		setFocus(overlayItemList.get(index)); 
		return super.onTap(index);
	}

    @Override  
    protected OverlayItem createItem(int i) {  
        return overlayItemList.get(i);  
    }  
  
    @Override  
    public int size() {  
        return overlayItemList.size();  
    }  
  
    public void addOverlay(OverlayItem overlayItem) {  
        overlayItemList.add(overlayItem);  
        this.populate();  
    }

}
