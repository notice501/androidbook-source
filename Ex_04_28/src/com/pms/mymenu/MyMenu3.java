package com.pms.mymenu;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MyMenu3 extends ListActivity {
	
    private static final String[] strs = new String[] {
    	"first", "second", "third", "fourth", "fifth"};
    
    public static final int CONTEXT_MENU_1 = Menu.FIRST;
    public static final int CONTEXT_MENU_2 = Menu.FIRST + 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //��������
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
        
        registerForContextMenu(getListView());
        
    }
    /*����menu*/
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CONTEXT_MENU_1, 1, "Context Menu 1");//���menuItem
		menu.add(0, CONTEXT_MENU_2, 2, "Context Menu 2");
		menu.setHeaderTitle("Context Menu Title");//���ò˵�����
	}
	
	/* ��Ӧmenuѡ���¼� */ 
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//���ListView��ItemId
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		  switch (item.getItemId()) 
		  {
		  	case 1:
		  		Toast.makeText(this, "�����˵�һ��", Toast.LENGTH_LONG).show();
		  	case 2:
		  		Toast.makeText(this, "�����˵ڶ�����", Toast.LENGTH_LONG).show();
		  }
		return super.onContextItemSelected(item);
	}
}