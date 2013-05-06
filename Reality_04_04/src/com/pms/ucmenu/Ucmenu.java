package com.pms.ucmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Ucmenu extends Activity {
	
	GridView menuDialogGrid, menubarGrid;//底部菜单和对话框菜单都以GridView展示
	AlertDialog menuDialog;//菜单对话框
	View menuView;//menu视图
	private boolean isMore = false;// 控制菜单翻页
	/* MENU菜单选项标题*/
	private final int ITEM_SEARCH = 0;// 搜索
	private final int ITEM_FILE_MANAGER = 1;// 文件管理
	private final int ITEM_DOWN_MANAGER = 2;// 下载管理
	private final int ITEM_FULLSCREEN = 3;// 全屏
	private final int ITEM_MORE = 11;// 菜单

	private final int MENUBAR_ITEM_MENU = 4;// 菜单
	
	/*菜单图片资源 */
	int[] menu_image_array = { R.drawable.menu_search,
			R.drawable.menu_filemanager, R.drawable.menu_downmanager,
			R.drawable.menu_fullscreen, R.drawable.menu_inputurl,
			R.drawable.menu_bookmark, R.drawable.menu_bookmark_sync_import,
			R.drawable.menu_sharepage, R.drawable.menu_quit,
			R.drawable.menu_nightmode, R.drawable.menu_refresh,
			R.drawable.menu_more };
	
	/* 菜单标题 */
	String[] menu_name_array = { "搜索", "文件管理", "下载管理", "全屏", "网址", "书签",
			"加入书签", "分享页面", "退出", "夜间模式", "刷新", "更多" };
	
	/* 菜单图片2 */
	int[] menu_image_array2 = { R.drawable.menu_auto_landscape,
			R.drawable.menu_penselectmodel, R.drawable.menu_page_attr,
			R.drawable.menu_novel_mode, R.drawable.menu_page_updown,
			R.drawable.menu_checkupdate, R.drawable.menu_checknet,
			R.drawable.menu_refreshtimer, R.drawable.menu_syssettings,
			R.drawable.menu_help, R.drawable.menu_about, R.drawable.menu_return };
	
	/*菜单标题2*/
	String[] menu_name_array2 = { "自动横屏", "笔选模式", "阅读模式", "浏览模式", "快捷翻页",
			"检查更新", "检查网络", "定时刷新", "设置", "帮助", "关于", "返回" };

	/*底部菜单图片*/
	int[] menu_toolbar_image_array = { R.drawable.controlbar_homepage,
			R.drawable.controlbar_backward_enable,
			R.drawable.controlbar_forward_enable, R.drawable.controlbar_window,
			R.drawable.controlbar_showtype_list };
	
	/*底部菜单文字*/
	String[] menu_toolbar_name_array = { "首页", "后退", "前进", "创建", "菜单" };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //导入布局
        menuView = View.inflate(this, R.layout.gridview_menu, null);
        //创建自定义AlertDialog,这里要注意使用creat()方法来创建AlertDialog对象
         menuDialog = new AlertDialog.Builder(this).create();
         menuDialog.setView(menuView);
         /*设置按键监听事件，监听menu键*/
         menuDialog.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_MENU){
					menuDialog.dismiss();//监听menu键，如果按下menu键，则关闭对话框
				}
				return false;
			}
		});

        //要在相对应的布局对象中取得控件对象
         menuDialogGrid = (GridView) menuView.findViewById(R.id.gridview);
         //绑定监听器
         menuDialogGrid.setAdapter(getAdapter(this, menu_name_array, menu_image_array));
         menuDialogGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch(position){
				case ITEM_SEARCH:// 搜索
					Toast.makeText(Ucmenu.this, "你点击了搜索", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_FILE_MANAGER:// 文件管理
					Toast.makeText(Ucmenu.this, "你点击了文件管理", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_DOWN_MANAGER:// 下载管理
					Toast.makeText(Ucmenu.this, "你点击了下载管理", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_FULLSCREEN:// 全屏
					Toast.makeText(Ucmenu.this, "你点击了全屏", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_MORE:// 翻页
					if (isMore) {
						//重新绑定新的内容
						menuDialogGrid.setAdapter(getAdapter(Ucmenu.this, menu_name_array, menu_image_array));
						isMore = false;//
					} else {// 首页
						menuDialogGrid.setAdapter(getAdapter(Ucmenu.this, menu_name_array2, menu_image_array2));
						isMore = true;
					}
					menuDialogGrid.invalidate();// 更新视图
					menuDialogGrid.setSelection(ITEM_MORE);//设置当前选择项
					break;
				}
				
			}
		});
         /*底部菜单*/
         menubarGrid = (GridView) findViewById(R.id.menuBar_gv);//得到底部菜单GridView对象
         //绑定适配器
         menubarGrid.setAdapter(getAdapter(Ucmenu.this,menu_toolbar_name_array,menu_toolbar_image_array));
 		 menubarGrid.setBackgroundResource(R.drawable.channelgallery_bg);// 设置背景
         //绑定点击事件监听器
         menubarGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*点击即弹出相应的Toast消息*/
				Toast.makeText(Ucmenu.this,
						menu_toolbar_name_array[position], Toast.LENGTH_SHORT)
						.show();
				//
				if(position == MENUBAR_ITEM_MENU)
					menuDialog.show();
			}
		});
        
    }
    /*创建一个menu，否则不能截获menu，menu的使用后面会学习*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");//至少增加一个menu
		return super.onCreateOptionsMenu(menu);
	}
	
	//处理menu事件
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (menuDialog == null) {
			//如果menuDialog为空，则创建一个并显示
			menuDialog = new AlertDialog.Builder(this).
			setView(menuView).
			show();
		} else {
			menuDialog.show();//menuDialog不为空，则直接显示
		}
		return false;// 必须返回false，返回为true，则显示系统menu
	}

	/*增加一个方法，返回MyAdapter对象，方便使用*/
    private MyAdapter getAdapter(Context ct,String[] items,int[] icons){
    	MyAdapter mAdapter = new MyAdapter(ct, items, icons);
    	return  mAdapter;
    }
}