package com.pms.mygraphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View{
	
	private Paint mPaint;//声明Canvas对象

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();//创建画笔
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制画布的背景
		canvas.drawColor(Color.RED);
		// 设置没有锯齿
		mPaint.setAntiAlias(true);
		// 设置画笔为空心
		mPaint.setStyle(Paint.Style.STROKE);
		
		//创建一个矩形区域，指定它的四个角的坐标
		Rect rect = new Rect();
		rect.left = 10;
		rect.right = 50;
		rect.top = 10;
		rect.bottom = 40;
		//绘制矩形
		canvas.drawRect(rect, mPaint);
		
		//再创建一个矩形区域
		Rect rect1 = new Rect();
		rect1.left = 60;
		rect1.right = 100;
		rect1.top = 10;
		rect1.bottom = 40;
		// 设置画笔颜色为蓝色
		mPaint.setColor(Color.BLUE);
		// 设置画笔为实心
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawRect(rect1, mPaint);
		
		//创建一个椭圆
		RectF rectf = new RectF();
		rectf.left = 10;
		rectf.right = 50;
		rectf.top = 50;
		rectf.bottom = 80;
		// 设置画笔颜色为灰色
		mPaint.setColor(Color.GRAY);
		//设置画笔为实心
		mPaint.setStyle(Paint.Style.FILL);
		//绘制椭圆
		canvas.drawOval(rectf, mPaint);
		
		//继续创建一个椭圆
		RectF rectf1 = new RectF();
		rectf1.left = 60;
		rectf1.right = 100;
		rectf1.top = 50;
		rectf1.bottom = 80;
		//设置画笔为空心
		mPaint.setStyle(Paint.Style.STROKE);
		//绘制椭圆
		canvas.drawOval(rectf1, mPaint);
		
		//绘制多边形（这里是一个三角形）,创建一个Path对象
		Path path = new Path();
		
		//通过点的横纵坐标，绘制三角形的点
		path.moveTo(100, 100);//绘制初始点
		path.lineTo(150, 100);//从初始点到指定坐标画线
		path.lineTo(200, 200);
		
		path.close();//连接还未封闭的部分，使之构成多边形
		canvas.drawPath(path, mPaint);//绘制多边形
	}
	
	

}
