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
    /*���Ǹ÷���������menu*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "����")//ͨ��add����������һ��MenuItem����
			.setIcon(android.R.drawable.ic_menu_share);//����ͼ��
		menu.add(0, 2, 2, "����")
			.setIcon(android.R.drawable.ic_menu_save);
		/*�����Ӳ˵�*/
		SubMenu subMenu = menu.addSubMenu(1, 10, 10, "�༭");
		subMenu.setIcon(android.R.drawable.ic_menu_edit);//����ͼ��
		/*�����Ӳ˵���Ŀ*/
		subMenu.add(2, 11, 11, "����");
		subMenu.add(2, 11, 11, "����");
		return super.onCreateOptionsMenu(menu);
	}
	/*����menu����¼�*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*����id�жϵ��λ��*/
		if(item.getItemId() == 1 )
		{
			Toast.makeText(this, "�����˷���", Toast.LENGTH_LONG).show();
		}
		else if(item.getItemId() == 2)
		{
			Toast.makeText(this, "�����˱���", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}
	
}