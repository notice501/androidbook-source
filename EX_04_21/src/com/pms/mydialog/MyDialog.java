package com.pms.mydialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyDialog extends Activity implements OnClickListener{
	
	/*���������ؼ�����*/
	private Button bt_remind;
	private Button bt_sichoice;
	private Button bt_muchoice;
	private Button bt_login;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*�õ�Button�ؼ�����*/
        bt_remind = (Button) findViewById(R.id.button_remind);
        bt_sichoice = (Button) findViewById(R.id.button_sichoice);
        bt_muchoice = (Button) findViewById(R.id.button_muchoice);
        bt_login = (Button) findViewById(R.id.button_login);
        
        /*Ϊ��ť�󶨵��������*/
        bt_remind.setOnClickListener(this);
        bt_sichoice.setOnClickListener(this);
        bt_muchoice.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        
        
    }
    /*�������¼�*/
	@Override
	public void onClick(View v) {
		//������Ӧ�� Button Id������
		switch(v.getId())
		{
		/*���ѶԻ���*/
		case R.id.button_remind:
			new AlertDialog.Builder(this)
					.setTitle("����")//���ñ���
					.setMessage("��ȷ��Ҫ�˳���")//������ʾ����
					/*����һ��ȷ����ť��ʵ�ֵ���¼�*/
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							MyDialog.this.finish();//�رո�Acitivity
							
						}
					})
					/*����һ��ȡ����ť��ʵ�ֵ���¼�*/
					.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//����һ��Toast��Ϣ��ʾδ�˳�����һ�ڽ������
							Toast.makeText(MyDialog.this, "��ȡ�����˳�", Toast.LENGTH_SHORT).show();
						}
					})
					.show();
					
			break;
			/*��ѡ�Ի���*/
		case R.id.button_sichoice:
			new AlertDialog.Builder(this)
				.setTitle("��ѡ�Ի���")
				/*����һ����ѡ�б��÷����ж������ط�ʽ������ʵ�ֵķ�ʽ��������������Ϊ
				 * ������Դid����ǰѡ�е���Ŀ��������
				 */
				.setSingleChoiceItems(R.array.mobile_arry, 2, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//����һ��Toast��Ϣ����һ�ڽ�����ܣ���ʾ�������һ����Ŀ
						Toast.makeText(MyDialog.this, "�����˵�" + which + "��", Toast.LENGTH_SHORT).show();
						
					}
				})
				.show();
			break;
			/*��ѡ�Ի���*/
		case R.id.button_muchoice:
			new AlertDialog.Builder(this)
				.setTitle("��ѡ�Ի���")
				/*����һ����ѡ�б�ʵ�ָ÷������е�һ�����ط�ʽ��������������Ϊ������Դid����ʼ��ѡ�񣬼�����*/
				.setMultiChoiceItems(R.array.phone_arry, new boolean[]{false,true,false,false,true,true}, 
						new DialogInterface.OnMultiChoiceClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which, boolean isChecked) {
								//����һ��Toast��Ϣ����һ�ڽ�����ܣ�����ѡ������һ����Ŀ
								Toast.makeText(MyDialog.this, "�����˵�" + which + "��", Toast.LENGTH_SHORT).show();
								
							}
						})
				.show();
			break;
			/*�Զ����ѡ�Ի���*/
		case R.id.button_login:
			
			//���Զ����login_dialog��װ��LinearLayout����
			final LinearLayout loginLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.login_dialog, null);
			
			new AlertDialog.Builder(this)
				.setTitle("�Զ����¼�Ի���")
				.setIcon(R.drawable.icon)//���öԻ���ͼ��
				.setView(loginLayout)//�󶨲���
				.setPositiveButton("��¼", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						/*�õ���������ע��Ҫ�ڵ���Ĳ��ֶ������ҵ��ÿؼ�*/
						EditText ed_username = (EditText) loginLayout.findViewById(R.id.eduser_name);
						EditText ed_userpassword = (EditText) loginLayout.findViewById(R.id.eduser_password);
						//ȡ��������е�ֵ
						String user_info = "�û���: " + ed_username.getText().toString() + "\n" +
											"����: " + ed_userpassword.getText().toString();
						//����һ��Toast��Ϣ����ʾ��¼��Ϣ
						Toast.makeText(MyDialog.this, "��¼�ɹ��������ϢΪ��" + "\n" + user_info	, Toast.LENGTH_LONG)
						.show();
						
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//��ʾδ��¼
						Toast.makeText(MyDialog.this, "δ��¼", Toast.LENGTH_LONG).show();
					}
				})
				.show();
			break;
			
		}
		
	}
}