package com.pms.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class Itab extends View{
	private Paint mPaint;//画笔对象
	
	//构造器，View下构造器有三种方式，在xml中配置必须实现这种方式
	public Itab(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint = new Paint( );//创建画笔
		mPaint.setStyle( Paint.Style.FILL );//设置画笔 为实心
		         
		Rect r = new Rect( );//创建一个矩形区域
		this.getDrawingRect( r );//返回视图的可见边界，填充矩形的各个位置属性
		 
		canvas.drawColor( 0xFF000000 );
		mPaint.setColor( 0xFF434343 );
		//绘制一条线，前两个坐标为直线开始点的横纵坐标，后面的为结束点的横纵坐标，坐标有矩形区域来决定
		canvas.drawLine( r.left, r.top + 1, r.right, r.top + 1, mPaint );
	}
	

}
