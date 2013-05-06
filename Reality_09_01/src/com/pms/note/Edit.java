package com.pms.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pms.db.DbHelper;

public class Edit extends Activity{
	
	private EditText et_title, et_content;  //输入框对象
	private Button   bt;					//按钮对象
	private DbHelper db;					//数据库对象
	private String   id;					//从NoteList传过来的id
	private String   listTitle, listContent;//从NoteList传过来的标题和内容

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		/*取得对象*/
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);
		bt = (Button) findViewById(R.id.bt);
		db = new DbHelper(this);
		
		Intent intent = getIntent();    	//得到Intent对象
		/*取出从NoteList传来的值*/
		id = intent.getStringExtra(DbHelper.NOTE_ID);
		listTitle = intent.getStringExtra(DbHelper.NOTE_TITLE);
		listContent = intent.getStringExtra(DbHelper.NOTE_CONTENT);
		
		if(listTitle!= null){           	//标题内容不为空
			et_title.setText(listTitle);	//显示该标题
		
			
		}
		if(listContent!=null){				//正文内容不为空
			et_content.setText(listContent);//显示该正文
		}
		
		/*绑定按钮点击事件*/
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*取得用户的输入内容*/
				String title = et_title.getText().toString();
				String content = et_content.getText().toString();
				
				//判断是否为空
				if(title.equals("")){
					Toast.makeText(Edit.this, "标题不能为空！", Toast.LENGTH_SHORT).show();
				}
				else if(id!=null){              			//判断是否存在该id的记事
					if(db.updateNote(id, title, content)>0){//存在则更新，返回值大于0，更新成功
						Toast.makeText(Edit.this, "编辑成功！", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(Edit.this, "编辑失败", Toast.LENGTH_SHORT).show();
					}
				}
				else if(db.insertNote(title, content)!= -1)//不存在则插入，返回值不等于-1，插入成功
					{
						Toast.makeText(Edit.this, "添加成功！", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(Edit.this, "添加失败！", Toast.LENGTH_SHORT).show();
					}
				/*设置需要返回到NoteList的内容*/
				Intent intent = new Intent();
				setResult(RESULT_OK,intent);
				finish();    //关闭这个Activity
			}
		
		});
		
		}
	
	

}
