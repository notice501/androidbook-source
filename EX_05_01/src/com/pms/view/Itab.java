package com.pms.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class Itab extends View{
	private Paint mPaint;//���ʶ���
	
	//��������View�¹����������ַ�ʽ����xml�����ñ���ʵ�����ַ�ʽ
	public Itab(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint = new Paint( );//��������
		mPaint.setStyle( Paint.Style.FILL );//���û��� Ϊʵ��
		         
		Rect r = new Rect( );//����һ����������
		this.getDrawingRect( r );//������ͼ�Ŀɼ��߽磬�����εĸ���λ������
		 
		canvas.drawColor( 0xFF000000 );
		mPaint.setColor( 0xFF434343 );
		//����һ���ߣ�ǰ��������Ϊֱ�߿�ʼ��ĺ������꣬�����Ϊ������ĺ������꣬�����о�������������
		canvas.drawLine( r.left, r.top + 1, r.right, r.top + 1, mPaint );
	}
	

}
