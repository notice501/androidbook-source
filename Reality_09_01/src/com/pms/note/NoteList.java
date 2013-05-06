package com.pms.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.pms.db.DbHelper;


public class NoteList extends Activity{
	
	/*变量声明*/
	private ListView lv;
	private TextView tv;
	private DbHelper db;
	private Cursor   cursor;
	private static final int ADD = Menu.FIRST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notelist);
		
		/*取得控件对象*/
		lv = (ListView) findViewById(R.id.lv);
		tv = (TextView) findViewById(R.id.tv);
		
		registerForContextMenu(lv);         //注册一个上下文菜单
		db = new DbHelper(this);            //获得数据库对象
		cursor = db.selectNotes();				//取得数据库中的记事
		refreshListView();
	
		
		/*绑定点击事件*/
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cursor.moveToPosition(position);                        //将cursor指向position
				Intent intent = new Intent(NoteList.this, Edit.class);  //创建一个Intent对象，跳转到Edit
				/*传递记事的id，标题，内容,这些内容从数据库中相应的字段中取得*/
				intent.putExtra(DbHelper.NOTE_ID, String.valueOf(id));
				intent.putExtra(DbHelper.NOTE_TITLE, cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NOTE_TITLE)));
				intent.putExtra(DbHelper.NOTE_CONTENT, cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NOTE_CONTENT)));
				startActivityForResult(intent, 0);						//跳转                     
			}
			
		});
		
		
		
	}

	/**刷新ListView
	  */
	public void refreshListView() {
		cursor = db.selectNotes();
		if(cursor.moveToFirst()){
			startManagingCursor(cursor);			//让Activity来管理cursor
			String[] from = {DbHelper.NOTE_TITLE};	//数据库中的列名
			int[] to = {R.id.itemtv};				//数据库中列的内容绑定的视图
			//SimpleCursorAdapter将数据库中的值绑定到listview
			//参数分别为上下文，listview的布局，游标，数据库中列的值，值所要绑定的视图
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(NoteList.this, 
				R.layout.listitem,cursor, from, to);
			lv.setAdapter(adapter);                 //listview绑定适配器
			tv.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}
		else{
			tv.setVisibility(View.VISIBLE); //设置为可见
			//设置显示的文本
			tv.setText("还没有你的私人笔记，点击menu添加");
			lv.setVisibility(View.GONE);
		}
			
		}
	
	
	/*创建上下文菜单*/
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 1, "删除");
		
	}

	/*创建菜单*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(0, ADD, 1, "添加笔记")
			.setIcon(R.drawable.menu_add);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	/*设置上下文菜单点击事件*/
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		if(item.getItemId()==1){
			new AlertDialog.Builder(this)                  //新建对话框
				.setTitle("警告")	
				.setMessage("确定要删除么？")
				.setIcon(R.drawable.plugin_notice)		   //设置图标
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						db.deleteNote(String.valueOf(info.id)); //删除数据库中该项内容
						refreshListView();            //刷新listview
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.show();
		}
		return super.onContextItemSelected(item);
	}

	/*设置menu选择事件*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==ADD)
		{
			Intent intent = new Intent(NoteList.this, Edit.class);
			startActivityForResult(intent, 1);//跳转到Edit
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*从Edit这个Activity返回是调用*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		refreshListView();        		//刷新listview   
	}

}
