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
	/* Tab视图 */
	public static class TabView extends View 
	{
        private Paint					mPaint;//背景画笔
        private Paint					mActiveTextPaint;//选中
        private Paint					mInactiveTextPaint;//未选中
        private ArrayList<TabMember>	mTabMembers;//tab成员
        private int						mActiveTab; //标识选中的tab
        private OnTabClickListener		mOnTabClickListener = null;//标签点击事件
        
		public TabView( Context context, AttributeSet attrs ) //构造器，在里面初始化
		{
			super(context, attrs);
			
			//初始化tab成员数组
			mTabMembers = new ArrayList<Itab.TabView.TabMember>( );
			
			mPaint = new Paint( );
			mActiveTextPaint = new Paint( );
			mInactiveTextPaint = new Paint( );
			
			mPaint.setStyle( Paint.Style.FILL );//画笔风格为实心
			mPaint.setColor( 0xFFFFFF00 );//颜色
			mPaint.setAntiAlias(true);//抗锯齿
			
			mActiveTextPaint.setTextAlign( Align.CENTER );//设置画笔文本对齐方式
			mActiveTextPaint.setTextSize( 12 );//设置画笔文本大小
			mActiveTextPaint.setColor( 0xFFFFFFFF );//颜色
			mActiveTextPaint.setAntiAlias(true);//抗锯齿
			
			
			mInactiveTextPaint.setTextAlign( Align.CENTER );
			mInactiveTextPaint.setTextSize( 12 );
			mInactiveTextPaint.setColor( 0xFF999999 );
			mInactiveTextPaint.setAntiAlias(true);
			
			mActiveTab = 0;//初始化为0
			
		}
		
        @Override
        protected void onDraw( Canvas canvas )
        {
        	super.onDraw( canvas );
        	
        	Rect r = new Rect( );//创建一个矩形区
        	this.getDrawingRect( r );//返回视图的可见边界，填充矩形的各个位置属性
        	
        	// 计算每个标签能使用多少像素
        	int singleTabWidth = r.right / ( mTabMembers.size( ) != 0 ? mTabMembers.size( ) : 1 );
        	
        	
        	/* 绘制背景*/
        	canvas.drawColor( 0xFF000000 );//画布底色
        	mPaint.setColor( 0xFF434343 );//设置画笔颜色
        	canvas.drawLine( r.left, r.top + 1, r.right, r.top + 1, mPaint );//画线
        	
        	int color = 46;
        	//通过循环的绘制，画出渐变效果
        	for( int i = 0; i < 24; i++ )
        	{
        		mPaint.setARGB( 255, color, color, color );
        		canvas.drawRect( r.left, r.top + i + 1, r.right, r.top + i + 2, mPaint );
        		color--;
        	}

        	// 绘制每一个tab
        	for( int i = 0; i < mTabMembers.size( ); i++ )
        	{
        		TabMember tabMember = mTabMembers.get( i );
        		//取得Bitmap对象
        		Bitmap icon = BitmapFactory.decodeResource( getResources( ), tabMember.getIconResourceId( ) );
        		//根据创建的Bitmap对象的宽高创建可变的Bitmap对象
    			Bitmap iconColored = Bitmap.createBitmap( icon.getWidth(), icon.getHeight(), Bitmap.Config.ARGB_8888 );
    			//创建画笔，并设置打开抗锯齿和过滤效果
    			Paint p = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    			//创建画布
    			Canvas iconCanvas = new Canvas( );
    			//绘制图片
    			iconCanvas.setBitmap( iconColored );
 
    			/* 为已选中的tab绘制一个白蓝的渐变色，未选中的绘制一个白灰的渐变色 */
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
    			
        		// 计算tab图片的位置
        		int tabImgX = singleTabWidth * i + ( singleTabWidth / 2 - icon.getWidth( ) / 2 );
        		
        		// 绘制tab图片 选中的和未选中的
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
         * 定义触摸事件
         */
        @Override
        public boolean onTouchEvent( MotionEvent motionEvent )
        {
        	Rect r = new Rect( );
        	this.getDrawingRect( r ); 
        	//计算tab的宽度
        	float singleTabWidth = r.right / ( mTabMembers.size( ) != 0 ? mTabMembers.size( ) : 1 );
        	
        	//表示选中的tab
        	int pressedTab = (int) ( ( motionEvent.getX( ) / singleTabWidth ) - ( motionEvent.getX( ) / singleTabWidth ) % 1 );
        	
        	mActiveTab = pressedTab;//将选中的tab赋给活动tab标示变量
        	
        	if( this.mOnTabClickListener != null)
        	{
        		this.mOnTabClickListener.onTabClick( mTabMembers.get( pressedTab ).getId( ) );        	
        	}
        	
        	this.invalidate();//刷新View
        	
        	return super.onTouchEvent( motionEvent );
        }
        
        //增加一个tab对象
        void addTabMember( TabMember tabMember )
        {
        	mTabMembers.add( tabMember );
        }
        
        //添加点击监听器
        void setOnTabClickListener( OnTabClickListener onTabClickListener )
        {
        	mOnTabClickListener = onTabClickListener;
        }
        
        /* tab成员类 */
        public static class TabMember
        {
        	protected int		mId;                //id
        	protected String	mText;              //tab显示的文本
        	protected int 		mIconResourceId;    //图片资源id
        	
        	/* 构造函数 */
        	TabMember( int Id, String Text, int iconResourceId )
        	{
        		mId = Id;
        		mIconResourceId = iconResourceId;
        		mText = Text;
        	}
        	//返回id
        	public int getId( )
        	{
        		return mId;
        	}
        	
        	//返回文本
        	public String getText( )
        	{
        		return mText;
        	}
        	
        	//返回图标资源id
        	public int getIconResourceId( )
        	{
        		return mIconResourceId;
        	}
        	
        	//设置文本
        	public void setText( String Text )
        	{
        		mText = Text;
        	}
        	
        	//设置图标资源id
        	public void setIconResourceId( int iconResourceId )
        	{
        		mIconResourceId = iconResourceId;
        	}
        }
        
        //定义点击事件监听接口
        public static interface OnTabClickListener
        {	
        	//点击事件
        	public abstract void onTabClick( int tabId );
        }
	}
	
	/* 创建一个自定义相对布局，绘制带条纹背景*/
	public static class iRelativeLayout extends RelativeLayout
	{
		private Paint	mPaint;
		private Rect	mRect;
		
		public iRelativeLayout( Context context, AttributeSet attrs ) 
		{
			super(context, attrs);
			
			mRect = new Rect( );
			mPaint = new Paint( );//创建画笔
			
			mPaint.setStyle( Paint.Style.FILL_AND_STROKE );
			mPaint.setColor( 0xFFCBD2D8 );
		}
		
		@Override
		protected void onDraw( Canvas canvas )
		{
			super.onDraw( canvas );

			canvas.drawColor( 0xFFC5CCD4 );
			
			this.getDrawingRect( mRect );
			
			//绘制布局背景的纹理效果
			for( int i = 0; i < mRect.right; i += 7 )
			{
				canvas.drawRect( mRect.left + i, mRect.top, mRect.left + i + 2, mRect.bottom, mPaint );
			}

		}
	}
	
	/* 定义图标ID常量*/
	private static final int TAB_HIGHLIGHT = 1;
	private static final int TAB_CHAT = 2;
	private static final int TAB_RANK = 3;
	private static final int TAB_SEARCH = 4;
	private static final int TAB_REDO = 5;
	
	private TabView			mTabs;//自定义tab对象
	/* 声明布局对象 */
	private LinearLayout 	mTabLayout_One;
	private LinearLayout 	mTabLayout_Two;
	private LinearLayout 	mTabLayout_Three;
	private LinearLayout 	mTabLayout_Four;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 

        /* 获得各个布局对象 */
        mTabs = (TabView) this.findViewById( R.id.Tabs );
        mTabLayout_One = (LinearLayout) this.findViewById( R.id.TabLayout_One );
        mTabLayout_Two = (LinearLayout) this.findViewById( R.id.TabLayout_Two );
        mTabLayout_Three = (LinearLayout) this.findViewById( R.id.TabLayout_Three );
        mTabLayout_Four = (LinearLayout) this.findViewById( R.id.TabLayout_Four );
        
        /* 添加tab成员 */
        mTabs.addTabMember( new TabMember( TAB_HIGHLIGHT, "精选", R.drawable.jingxuan ) );
        mTabs.addTabMember( new TabMember( TAB_CHAT, "类别", R.drawable.cat ) );
        mTabs.addTabMember( new TabMember( TAB_RANK, "25大排行榜", R.drawable.rank ) );
        mTabs.addTabMember( new TabMember( TAB_SEARCH, "搜索", R.drawable.search ) );
        mTabs.addTabMember( new TabMember( TAB_REDO, "更新", R.drawable.download ) );
        
        /*初始显示第一个界面，将第一个布局可见，其他设为不可见*/
        mTabLayout_One.setVisibility( View.VISIBLE );
        mTabLayout_Two.setVisibility( View.GONE );
        mTabLayout_Three.setVisibility( View.GONE );
        mTabLayout_Four.setVisibility( View.GONE );
        
        mTabs.setOnTabClickListener( new OnTabClickListener( ) {
        	@Override
        	public void onTabClick( int tabId )//实现点击事件
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