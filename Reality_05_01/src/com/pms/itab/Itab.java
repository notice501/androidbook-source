package com.pms.itab;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.pms.itab.Itab.TabView.OnTabClickListener;
import com.pms.itab.Itab.TabView.TabMember;


public class Itab extends Activity 
{
	/* Tab��ͼ */
	public static class TabView extends View 
	{
        private Paint					mPaint;//��������
        private Paint					mActiveTextPaint;//ѡ��
        private Paint					mInactiveTextPaint;//δѡ��
        private ArrayList<TabMember>	mTabMembers;//tab��Ա
        private int						mActiveTab; //��ʶѡ�е�tab
        private OnTabClickListener		mOnTabClickListener = null;//��ǩ����¼�
        
		public TabView( Context context, AttributeSet attrs ) //���������������ʼ��
		{
			super(context, attrs);
			
			//��ʼ��tab��Ա����
			mTabMembers = new ArrayList<Itab.TabView.TabMember>( );
			
			mPaint = new Paint( );
			mActiveTextPaint = new Paint( );
			mInactiveTextPaint = new Paint( );
			
			mPaint.setStyle( Paint.Style.FILL );//���ʷ��Ϊʵ��
			mPaint.setColor( 0xFFFFFF00 );//��ɫ
			mPaint.setAntiAlias(true);//�����
			
			mActiveTextPaint.setTextAlign( Align.CENTER );//���û����ı����뷽ʽ
			mActiveTextPaint.setTextSize( 12 );//���û����ı���С
			mActiveTextPaint.setColor( 0xFFFFFFFF );//��ɫ
			mActiveTextPaint.setAntiAlias(true);//�����
			
			
			mInactiveTextPaint.setTextAlign( Align.CENTER );
			mInactiveTextPaint.setTextSize( 12 );
			mInactiveTextPaint.setColor( 0xFF999999 );
			mInactiveTextPaint.setAntiAlias(true);
			
			mActiveTab = 0;//��ʼ��Ϊ0
			
		}
		
        @Override
        protected void onDraw( Canvas canvas )
        {
        	super.onDraw( canvas );
        	
        	Rect r = new Rect( );//����һ��������
        	this.getDrawingRect( r );//������ͼ�Ŀɼ��߽磬�����εĸ���λ������
        	
        	// ����ÿ����ǩ��ʹ�ö�������
        	int singleTabWidth = r.right / ( mTabMembers.size( ) != 0 ? mTabMembers.size( ) : 1 );
        	
        	
        	/* ���Ʊ���*/
        	canvas.drawColor( 0xFF000000 );//������ɫ
        	mPaint.setColor( 0xFF434343 );//���û�����ɫ
        	canvas.drawLine( r.left, r.top + 1, r.right, r.top + 1, mPaint );//����
        	
        	int color = 46;
        	//ͨ��ѭ���Ļ��ƣ���������Ч��
        	for( int i = 0; i < 24; i++ )
        	{
        		mPaint.setARGB( 255, color, color, color );
        		canvas.drawRect( r.left, r.top + i + 1, r.right, r.top + i + 2, mPaint );
        		color--;
        	}

        	// ����ÿһ��tab
        	for( int i = 0; i < mTabMembers.size( ); i++ )
        	{
        		TabMember tabMember = mTabMembers.get( i );
        		//ȡ��Bitmap����
        		Bitmap icon = BitmapFactory.decodeResource( getResources( ), tabMember.getIconResourceId( ) );
        		//���ݴ�����Bitmap����Ŀ�ߴ����ɱ��Bitmap����
    			Bitmap iconColored = Bitmap.createBitmap( icon.getWidth(), icon.getHeight(), Bitmap.Config.ARGB_8888 );
    			//�������ʣ������ô򿪿���ݺ͹���Ч��
    			Paint p = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    			//��������
    			Canvas iconCanvas = new Canvas( );
    			//����ͼƬ
    			iconCanvas.setBitmap( iconColored );
 
    			/* Ϊ��ѡ�е�tab����һ�������Ľ���ɫ��δѡ�еĻ���һ���׻ҵĽ���ɫ */
    			if( mActiveTab == i )
    			{
    				p.setShader( new LinearGradient( 0, 0, icon.getWidth(), icon.getHeight(),
    						0xFFFFFFFF, 0xFF54C7E1, Shader.TileMode.CLAMP ) );
    			}
    			else {    
    				p.setShader( new LinearGradient( 0, 0, icon.getWidth(), icon.getHeight(), 
    						0xFFA2A2A2, 0xFF5F5F5F, Shader.TileMode.CLAMP ) );
    			}
    			
    			iconCanvas.drawRect( 0, 0, icon.getWidth( ), icon.getHeight( ), p );
    			
    			for( int x = 0; x < icon.getWidth(); x++ )
    			{
    				for( int y = 0; y < icon.getHeight(); y++ )
    				{
    					if( ( icon.getPixel(x, y) & 0xFF000000 ) == 0 )
    					{
    						iconColored.setPixel( x, y, 0x00000000 );
    					}
    				}
    			}
    			
        		// ����tabͼƬ��λ��
        		int tabImgX = singleTabWidth * i + ( singleTabWidth / 2 - icon.getWidth( ) / 2 );
        		
        		// ����tabͼƬ ѡ�еĺ�δѡ�е�
        		if( mActiveTab == i )
        		{		
        			mPaint.setARGB( 37, 255, 255, 255 );
        			canvas.drawRoundRect(  new RectF( r.left + singleTabWidth * i + 3, r.top + 3, 
        					r.left + singleTabWidth * ( i + 1 ) - 3, r.bottom - 2 ), 5, 5, mPaint );
        			canvas.drawBitmap( iconColored, tabImgX , r.top + 5, null );
        			canvas.drawText( tabMember.getText( ), 
        					singleTabWidth * i + ( singleTabWidth / 2), r.bottom - 2, mActiveTextPaint );
        		} else
        		{
        			canvas.drawBitmap( iconColored, tabImgX , r.top + 5, null );
        			canvas.drawText( tabMember.getText( ),
        					singleTabWidth * i + ( singleTabWidth / 2), r.bottom - 2, mInactiveTextPaint );
        		}
        	}

        }
        /*
         * ���崥���¼�
         */
        @Override
        public boolean onTouchEvent( MotionEvent motionEvent )
        {
        	Rect r = new Rect( );
        	this.getDrawingRect( r ); 
        	//����tab�Ŀ��
        	float singleTabWidth = r.right / ( mTabMembers.size( ) != 0 ? mTabMembers.size( ) : 1 );
        	
        	//��ʾѡ�е�tab
        	int pressedTab = (int) ( ( motionEvent.getX( ) / singleTabWidth ) - ( motionEvent.getX( ) / singleTabWidth ) % 1 );
        	
        	mActiveTab = pressedTab;//��ѡ�е�tab�����tab��ʾ����
        	
        	if( this.mOnTabClickListener != null)
        	{
        		this.mOnTabClickListener.onTabClick( mTabMembers.get( pressedTab ).getId( ) );        	
        	}
        	
        	this.invalidate();//ˢ��View
        	
        	return super.onTouchEvent( motionEvent );
        }
        
        //����һ��tab����
        void addTabMember( TabMember tabMember )
        {
        	mTabMembers.add( tabMember );
        }
        
        //��ӵ��������
        void setOnTabClickListener( OnTabClickListener onTabClickListener )
        {
        	mOnTabClickListener = onTabClickListener;
        }
        
        /* tab��Ա�� */
        public static class TabMember
        {
        	protected int		mId;                //id
        	protected String	mText;              //tab��ʾ���ı�
        	protected int 		mIconResourceId;    //ͼƬ��Դid
        	
        	/* ���캯�� */
        	TabMember( int Id, String Text, int iconResourceId )
        	{
        		mId = Id;
        		mIconResourceId = iconResourceId;
        		mText = Text;
        	}
        	//����id
        	public int getId( )
        	{
        		return mId;
        	}
        	
        	//�����ı�
        	public String getText( )
        	{
        		return mText;
        	}
        	
        	//����ͼ����Դid
        	public int getIconResourceId( )
        	{
        		return mIconResourceId;
        	}
        	
        	//�����ı�
        	public void setText( String Text )
        	{
        		mText = Text;
        	}
        	
        	//����ͼ����Դid
        	public void setIconResourceId( int iconResourceId )
        	{
        		mIconResourceId = iconResourceId;
        	}
        }
        
        //�������¼������ӿ�
        public static interface OnTabClickListener
        {	
        	//����¼�
        	public abstract void onTabClick( int tabId );
        }
	}
	
	/* ����һ���Զ�����Բ��֣����ƴ����Ʊ���*/
	public static class iRelativeLayout extends RelativeLayout
	{
		private Paint	mPaint;
		private Rect	mRect;
		
		public iRelativeLayout( Context context, AttributeSet attrs ) 
		{
			super(context, attrs);
			
			mRect = new Rect( );
			mPaint = new Paint( );//��������
			
			mPaint.setStyle( Paint.Style.FILL_AND_STROKE );
			mPaint.setColor( 0xFFCBD2D8 );
		}
		
		@Override
		protected void onDraw( Canvas canvas )
		{
			super.onDraw( canvas );

			canvas.drawColor( 0xFFC5CCD4 );
			
			this.getDrawingRect( mRect );
			
			//���Ʋ��ֱ���������Ч��
			for( int i = 0; i < mRect.right; i += 7 )
			{
				canvas.drawRect( mRect.left + i, mRect.top, mRect.left + i + 2, mRect.bottom, mPaint );
			}

		}
	}
	
	/* ����ͼ��ID����*/
	private static final int TAB_HIGHLIGHT = 1;
	private static final int TAB_CHAT = 2;
	private static final int TAB_RANK = 3;
	private static final int TAB_SEARCH = 4;
	private static final int TAB_REDO = 5;
	
	private TabView			mTabs;//�Զ���tab����
	/* �������ֶ��� */
	private LinearLayout 	mTabLayout_One;
	private LinearLayout 	mTabLayout_Two;
	private LinearLayout 	mTabLayout_Three;
	private LinearLayout 	mTabLayout_Four;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 

        /* ��ø������ֶ��� */
        mTabs = (TabView) this.findViewById( R.id.Tabs );
        mTabLayout_One = (LinearLayout) this.findViewById( R.id.TabLayout_One );
        mTabLayout_Two = (LinearLayout) this.findViewById( R.id.TabLayout_Two );
        mTabLayout_Three = (LinearLayout) this.findViewById( R.id.TabLayout_Three );
        mTabLayout_Four = (LinearLayout) this.findViewById( R.id.TabLayout_Four );
        
        /* ���tab��Ա */
        mTabs.addTabMember( new TabMember( TAB_HIGHLIGHT, "��ѡ", R.drawable.jingxuan ) );
        mTabs.addTabMember( new TabMember( TAB_CHAT, "���", R.drawable.cat ) );
        mTabs.addTabMember( new TabMember( TAB_RANK, "25�����а�", R.drawable.rank ) );
        mTabs.addTabMember( new TabMember( TAB_SEARCH, "����", R.drawable.search ) );
        mTabs.addTabMember( new TabMember( TAB_REDO, "����", R.drawable.download ) );
        
        /*��ʼ��ʾ��һ�����棬����һ�����ֿɼ���������Ϊ���ɼ�*/
        mTabLayout_One.setVisibility( View.VISIBLE );
        mTabLayout_Two.setVisibility( View.GONE );
        mTabLayout_Three.setVisibility( View.GONE );
        mTabLayout_Four.setVisibility( View.GONE );
        
        mTabs.setOnTabClickListener( new OnTabClickListener( ) {
        	@Override
        	public void onTabClick( int tabId )//ʵ�ֵ���¼�
        	{
        		if( tabId == TAB_HIGHLIGHT )
        		{
        			mTabLayout_One.setVisibility( View.VISIBLE );
        			mTabLayout_Two.setVisibility( View.GONE );
        			mTabLayout_Three.setVisibility( View.GONE );
        			mTabLayout_Four.setVisibility( View.GONE );
        		} else if( tabId == TAB_CHAT )
        		{
        			mTabLayout_One.setVisibility( View.GONE );
        			mTabLayout_Two.setVisibility( View.VISIBLE );
        			mTabLayout_Three.setVisibility( View.GONE );
        			mTabLayout_Four.setVisibility( View.GONE );
        		} else if( tabId == TAB_RANK )
        		{
        			mTabLayout_One.setVisibility( View.GONE );
        			mTabLayout_Two.setVisibility( View.GONE );
        			mTabLayout_Three.setVisibility( View.VISIBLE );
        			mTabLayout_Four.setVisibility( View.GONE );
        		} else if( tabId == TAB_SEARCH )
        		{
        			mTabLayout_One.setVisibility( View.GONE );
        			mTabLayout_Two.setVisibility( View.GONE );
        			mTabLayout_Three.setVisibility( View.GONE );
        			mTabLayout_Four.setVisibility( View.VISIBLE );
        		}
        		else if(tabId == TAB_REDO)
        		{
        			mTabLayout_One.setVisibility( View.GONE );
        			mTabLayout_Two.setVisibility( View.GONE );
        			mTabLayout_Three.setVisibility( View.GONE );
        			mTabLayout_Four.setVisibility( View.VISIBLE );
        		}
        	}
        });
    }
}