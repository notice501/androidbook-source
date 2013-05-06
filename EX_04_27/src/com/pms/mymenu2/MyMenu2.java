package com.pms.mymenu2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

public class MyMenu2 extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    /*覆盖该方法，创建menu*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "分享")//通过add方法，创建一个MenuItem对象
			.setIcon(android.R.drawable.ic_menu_share);//设置图标
		menu.add(0, 2, 2, "保存")
			.setIcon(android.R.drawable.ic_menu_save);
		/*创建子菜单*/
		SubMenu subMenu = menu.addSubMenu(1, 10, 10, "编辑");
		subMenu.setIcon(android.R.drawable.ic_menu_edit);//设置图标
		/*增加子菜单条目*/
		subMenu.add(2, 11, 11, "增加");
		subMenu.add(2, 11, 11, "减少");
		return super.onCreateOptionsMenu(menu);
	}
	/*监听menu点击事件*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*根据id判断点击位置*/
		if(item.getItemId() == 1 )
		{
			Toast.makeText(this, "你点击了分享", Toast.LENGTH_LONG).show();
		}
		else if(item.getItemId() == 2)
		{
			Toast.makeText(this, "你点击了保存", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}
	
}