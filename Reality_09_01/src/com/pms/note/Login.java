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
	
	/*得到各个控件对象*/
	private EditText et;
	private Button   bt_login, bt_update;
	
	private DbHelper db;     //数据库对象
	private String   pwd;    //密码
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*取得各个控件*/
        et = (EditText) findViewById(R.id.password_edit);
        bt_login = (Button) findViewById(R.id.login_button);
        bt_update = (Button) findViewById(R.id.update_button);
        
        db = new DbHelper(this); //得到DbHelper对象
        
        pwd = db.getPwd();       //从数据库中取得密码
        
        if(pwd.equals(""))       //为空
        {
        	Toast.makeText(Login.this, "这是你第一次登录，请初始化密码！", Toast.LENGTH_LONG)
        	.show();//弹出Toast消息
        	bt_login.setText("注册");    //将Button显示的文字变为注册
        }
        else 					 //不为空
        {
        	Toast.makeText(Login.this, "欢迎回来，请输入密码登录！", Toast.LENGTH_LONG).show();
        }
        
        /*为注册或者登陆绑定监听事件*/
        bt_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = et.getText().toString();  //取得输入的密码
				if(pwd.equals(""))     //为空
				{
					changePwd();       //调用该方法
						
				}
				else if(password.equals("")){                //如果输入为空
					//弹出Toast消息
					Toast.makeText(Login.this, "不能为空！", Toast.LENGTH_LONG).show();
				}
				else if(password.equals(pwd)){               //如果输入匹配
					Toast.makeText(Login.this, "登录成功！", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(Login.this, NoteList.class);
					startActivity(intent);                   //跳转到记事列表
				}
				else{
					//不匹配弹出消息提示密码错误
					Toast.makeText(Login.this, "密码错误！", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        /*为修改密码按钮绑定监听事件*/
        bt_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = et.getText().toString();  //取得输入框的内容
				if(password.equals(pwd)){                   //匹配
					changePwd();                            //调用该方法
				}
				else{
					//不匹配弹出消息
					Toast.makeText(Login.this, "初始密码错误", Toast.LENGTH_LONG).show();
				}
			}
		});
        
    }
    
    /**修改密码
     * */
    public void changePwd(){
    	/*装载对话框布局*/
    	LinearLayout newPwd = (LinearLayout) getLayoutInflater()
		.inflate(R.layout.login_dialog, null);
		
    	//取得对话框中的输入框对象
		final EditText et_dia_new = (EditText) newPwd.findViewById(R.id.etnew);
		final EditText et_dia_con = (EditText) newPwd.findViewById(R.id.etconfirm);
		
		/*新建一个对话框*/
		new AlertDialog.Builder(Login.this)
			.setTitle("输入密码")              //设置标题
			.setView(newPwd)				  //设置对话框绑定的视图
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//取得输入框的内容
					String dia_new = et_dia_new.getText().toString(); 
					String dia_con = et_dia_con.getText().toString();
					
					if(dia_new.equals("")||dia_con.equals("")){   //判断是否有空值
						Toast.makeText(Login.this, "不能为空！", Toast.LENGTH_LONG).show();
					}
					else if(dia_new.equals(dia_con)){             //如果两个输入一致
						if(db.getPwd()!=null){
							db.updatePwd(dia_new);                //如果已经设置过密码，更新
						}
						else{
							db.insertPwd(dia_new);                //插入密码
						}
						pwd = dia_new;                            //新的密码
						bt_login.setText("登录");                 //把Button的文字显示为登录
						//弹出Toast消息
						Toast.makeText(Login.this, "你可以使用该密码登录了！", Toast.LENGTH_LONG)
							.show();

					}
					
				}
			})
			//设置一个取消按钮
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			})
			.show();
    }
}