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
        // Projection�ӿ�������Ļ���ص�����ϵͳ�͵�����澭γ�ȵ�����ϵͳ֮��ı任  
        Projection projection = mapView.getProjection();  
        // �������е�OverlayItem  
        for (int index = this.size() - 1; index >= 0; index--) {  
            // �õ�����������item  
            OverlayItem overLayItem = getItem(index);  
  
            // �Ѿ�γ�ȱ任�������MapView���Ͻǵ���Ļ��������  
            Point point = projection.toPixels(overLayItem.getPoint(), null);  
  
            Paint paintText = new Paint();  
            paintText.setColor(Color.RED);  
            paintText.setTextSize(13);  
            // �����ı�  
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
