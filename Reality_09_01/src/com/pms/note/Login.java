package com.pms.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pms.db.DbHelper;

public class Login extends Activity {
	
	/*�õ������ؼ�����*/
	private EditText et;
	private Button   bt_login, bt_update;
	
	private DbHelper db;     //���ݿ����
	private String   pwd;    //����
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*ȡ�ø����ؼ�*/
        et = (EditText) findViewById(R.id.password_edit);
        bt_login = (Button) findViewById(R.id.login_button);
        bt_update = (Button) findViewById(R.id.update_button);
        
        db = new DbHelper(this); //�õ�DbHelper����
        
        pwd = db.getPwd();       //�����ݿ���ȡ������
        
        if(pwd.equals(""))       //Ϊ��
        {
        	Toast.makeText(Login.this, "�������һ�ε�¼�����ʼ�����룡", Toast.LENGTH_LONG)
        	.show();//����Toast��Ϣ
        	bt_login.setText("ע��");    //��Button��ʾ�����ֱ�Ϊע��
        }
        else 					 //��Ϊ��
        {
        	Toast.makeText(Login.this, "��ӭ�����������������¼��", Toast.LENGTH_LONG).show();
        }
        
        /*Ϊע����ߵ�½�󶨼����¼�*/
        bt_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = et.getText().toString();  //ȡ�����������
				if(pwd.equals(""))     //Ϊ��
				{
					changePwd();       //���ø÷���
						
				}
				else if(password.equals("")){                //�������Ϊ��
					//����Toast��Ϣ
					Toast.makeText(Login.this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
				}
				else if(password.equals(pwd)){               //�������ƥ��
					Toast.makeText(Login.this, "��¼�ɹ���", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(Login.this, NoteList.class);
					startActivity(intent);                   //��ת�������б�
				}
				else{
					//��ƥ�䵯����Ϣ��ʾ�������
					Toast.makeText(Login.this, "�������", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        /*Ϊ�޸����밴ť�󶨼����¼�*/
        bt_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = et.getText().toString();  //ȡ������������
				if(password.equals(pwd)){                   //ƥ��
					changePwd();                            //���ø÷���
				}
				else{
					//��ƥ�䵯����Ϣ
					Toast.makeText(Login.this, "��ʼ�������", Toast.LENGTH_LONG).show();
				}
			}
		});
        
    }
    
    /**�޸�����
     * */
    public void changePwd(){
    	/*װ�ضԻ��򲼾�*/
    	LinearLayout newPwd = (LinearLayout) getLayoutInflater()
		.inflate(R.layout.login_dialog, null);
		
    	//ȡ�öԻ����е���������
		final EditText et_dia_new = (EditText) newPwd.findViewById(R.id.etnew);
		final EditText et_dia_con = (EditText) newPwd.findViewById(R.id.etconfirm);
		
		/*�½�һ���Ի���*/
		new AlertDialog.Builder(Login.this)
			.setTitle("��������")              //���ñ���
			.setView(newPwd)				  //���öԻ���󶨵���ͼ
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//ȡ������������
					String dia_new = et_dia_new.getText().toString(); 
					String dia_con = et_dia_con.getText().toString();
					
					if(dia_new.equals("")||dia_con.equals("")){   //�ж��Ƿ��п�ֵ
						Toast.makeText(Login.this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
					}
					else if(dia_new.equals(dia_con)){             //�����������һ��
						if(db.getPwd()!=null){
							db.updatePwd(dia_new);                //����Ѿ����ù����룬����
						}
						else{
							db.insertPwd(dia_new);                //��������
						}
						pwd = dia_new;                            //�µ�����
						bt_login.setText("��¼");                 //��Button��������ʾΪ��¼
						//����Toast��Ϣ
						Toast.makeText(Login.this, "�����ʹ�ø������¼�ˣ�", Toast.LENGTH_LONG)
							.show();

					}
					
				}
			})
			//����һ��ȡ����ť
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			})
			.show();
    }
}