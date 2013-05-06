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
	
	/*��������*/
	private ListView lv;
	private TextView tv;
	private DbHelper db;
	private Cursor   cursor;
	private static final int ADD = Menu.FIRST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notelist);
		
		/*ȡ�ÿؼ�����*/
		lv = (ListView) findViewById(R.id.lv);
		tv = (TextView) findViewById(R.id.tv);
		
		registerForContextMenu(lv);         //ע��һ�������Ĳ˵�
		db = new DbHelper(this);            //������ݿ����
		cursor = db.selectNotes();				//ȡ�����ݿ��еļ���
		refreshListView();
	
		
		/*�󶨵���¼�*/
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cursor.moveToPosition(position);                        //��cursorָ��position
				Intent intent = new Intent(NoteList.this, Edit.class);  //����һ��Intent������ת��Edit
				/*���ݼ��µ�id�����⣬����,��Щ���ݴ����ݿ�����Ӧ���ֶ���ȡ��*/
				intent.putExtra(DbHelper.NOTE_ID, String.valueOf(id));
				intent.putExtra(DbHelper.NOTE_TITLE, cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NOTE_TITLE)));
				intent.putExtra(DbHelper.NOTE_CONTENT, cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NOTE_CONTENT)));
				startActivityForResult(intent, 0);						//��ת                     
			}
			
		});
		
		
		
	}

	/**ˢ��ListView
	  */
	public void refreshListView() {
		cursor = db.selectNotes();
		if(cursor.moveToFirst()){
			startManagingCursor(cursor);			//��Activity������cursor
			String[] from = {DbHelper.NOTE_TITLE};	//���ݿ��е�����
			int[] to = {R.id.itemtv};				//���ݿ����е����ݰ󶨵���ͼ
			//SimpleCursorAdapter�����ݿ��е�ֵ�󶨵�listview
			//�����ֱ�Ϊ�����ģ�listview�Ĳ��֣��α꣬���ݿ����е�ֵ��ֵ��Ҫ�󶨵���ͼ
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(NoteList.this, 
				R.layout.listitem,cursor, from, to);
			lv.setAdapter(adapter);                 //listview��������
			tv.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}
		else{
			tv.setVisibility(View.VISIBLE); //����Ϊ�ɼ�
			//������ʾ���ı�
			tv.setText("��û�����˽�˱ʼǣ����menu���");
			lv.setVisibility(View.GONE);
		}
			
		}
	
	
	/*���������Ĳ˵�*/
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 1, "ɾ��");
		
	}

	/*�����˵�*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(0, ADD, 1, "��ӱʼ�")
			.setIcon(R.drawable.menu_add);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	/*���������Ĳ˵�����¼�*/
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		if(item.getItemId()==1){
			new AlertDialog.Builder(this)                  //�½��Ի���
				.setTitle("����")	
				.setMessage("ȷ��Ҫɾ��ô��")
				.setIcon(R.drawable.plugin_notice)		   //����ͼ��
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						db.deleteNote(String.valueOf(info.id)); //ɾ�����ݿ��и�������
						refreshListView();            //ˢ��listview
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.show();
		}
		return super.onContextItemSelected(item);
	}

	/*����menuѡ���¼�*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==ADD)
		{
			Intent intent = new Intent(NoteList.this, Edit.class);
			startActivityForResult(intent, 1);//��ת��Edit
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*��Edit���Activity�����ǵ���*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		refreshListView();        		//ˢ��listview   
	}

}
