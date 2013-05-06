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
	
	private EditText et_title, et_content;  //��������
	private Button   bt;					//��ť����
	private DbHelper db;					//���ݿ����
	private String   id;					//��NoteList��������id
	private String   listTitle, listContent;//��NoteList�������ı��������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		/*ȡ�ö���*/
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);
		bt = (Button) findViewById(R.id.bt);
		db = new DbHelper(this);
		
		Intent intent = getIntent();    	//�õ�Intent����
		/*ȡ����NoteList������ֵ*/
		id = intent.getStringExtra(DbHelper.NOTE_ID);
		listTitle = intent.getStringExtra(DbHelper.NOTE_TITLE);
		listContent = intent.getStringExtra(DbHelper.NOTE_CONTENT);
		
		if(listTitle!= null){           	//�������ݲ�Ϊ��
			et_title.setText(listTitle);	//��ʾ�ñ���
		
			
		}
		if(listContent!=null){				//�������ݲ�Ϊ��
			et_content.setText(listContent);//��ʾ������
		}
		
		/*�󶨰�ť����¼�*/
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*ȡ���û�����������*/
				String title = et_title.getText().toString();
				String content = et_content.getText().toString();
				
				//�ж��Ƿ�Ϊ��
				if(title.equals("")){
					Toast.makeText(Edit.this, "���ⲻ��Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}
				else if(id!=null){              			//�ж��Ƿ���ڸ�id�ļ���
					if(db.updateNote(id, title, content)>0){//��������£�����ֵ����0�����³ɹ�
						Toast.makeText(Edit.this, "�༭�ɹ���", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(Edit.this, "�༭ʧ��", Toast.LENGTH_SHORT).show();
					}
				}
				else if(db.insertNote(title, content)!= -1)//����������룬����ֵ������-1������ɹ�
					{
						Toast.makeText(Edit.this, "��ӳɹ���", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(Edit.this, "���ʧ�ܣ�", Toast.LENGTH_SHORT).show();
					}
				/*������Ҫ���ص�NoteList������*/
				Intent intent = new Intent();
				setResult(RESULT_OK,intent);
				finish();    //�ر����Activity
			}
		
		});
		
		}
	
	

}
