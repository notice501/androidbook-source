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
	
	private Paint mPaint;//����Canvas����

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();//��������
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ���ƻ����ı���
		canvas.drawColor(Color.RED);
		// ����û�о��
		mPaint.setAntiAlias(true);
		// ���û���Ϊ����
		mPaint.setStyle(Paint.Style.STROKE);
		
		//����һ����������ָ�������ĸ��ǵ�����
		Rect rect = new Rect();
		rect.left = 10;
		rect.right = 50;
		rect.top = 10;
		rect.bottom = 40;
		//���ƾ���
		canvas.drawRect(rect, mPaint);
		
		//�ٴ���һ����������
		Rect rect1 = new Rect();
		rect1.left = 60;
		rect1.right = 100;
		rect1.top = 10;
		rect1.bottom = 40;
		// ���û�����ɫΪ��ɫ
		mPaint.setColor(Color.BLUE);
		// ���û���Ϊʵ��
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawRect(rect1, mPaint);
		
		//����һ����Բ
		RectF rectf = new RectF();
		rectf.left = 10;
		rectf.right = 50;
		rectf.top = 50;
		rectf.bottom = 80;
		// ���û�����ɫΪ��ɫ
		mPaint.setColor(Color.GRAY);
		//���û���Ϊʵ��
		mPaint.setStyle(Paint.Style.FILL);
		//������Բ
		canvas.drawOval(rectf, mPaint);
		
		//��������һ����Բ
		RectF rectf1 = new RectF();
		rectf1.left = 60;
		rectf1.right = 100;
		rectf1.top = 50;
		rectf1.bottom = 80;
		//���û���Ϊ����
		mPaint.setStyle(Paint.Style.STROKE);
		//������Բ
		canvas.drawOval(rectf1, mPaint);
		
		//���ƶ���Σ�������һ�������Σ�,����һ��Path����
		Path path = new Path();
		
		//ͨ����ĺ������꣬���������εĵ�
		path.moveTo(100, 100);//���Ƴ�ʼ��
		path.lineTo(150, 100);//�ӳ�ʼ�㵽ָ�����껭��
		path.lineTo(200, 200);
		
		path.close();//���ӻ�δ��յĲ��֣�ʹ֮���ɶ����
		canvas.drawPath(path, mPaint);//���ƶ����
	}
	
	

}
