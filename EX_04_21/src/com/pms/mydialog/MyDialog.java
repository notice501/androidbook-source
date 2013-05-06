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
	
	/*声明各个控件对象*/
	private Button bt_remind;
	private Button bt_sichoice;
	private Button bt_muchoice;
	private Button bt_login;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*得到Button控件对象*/
        bt_remind = (Button) findViewById(R.id.button_remind);
        bt_sichoice = (Button) findViewById(R.id.button_sichoice);
        bt_muchoice = (Button) findViewById(R.id.button_muchoice);
        bt_login = (Button) findViewById(R.id.button_login);
        
        /*为按钮绑定点击监听器*/
        bt_remind.setOnClickListener(this);
        bt_sichoice.setOnClickListener(this);
        bt_muchoice.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        
        
    }
    /*处理点击事件*/
	@Override
	public void onClick(View v) {
		//根据相应的 Button Id来处理
		switch(v.getId())
		{
		/*提醒对话框*/
		case R.id.button_remind:
			new AlertDialog.Builder(this)
					.setTitle("警告")//设置标题
					.setMessage("你确定要退出吗")//设置显示内容
					/*设置一个确定按钮，实现点击事件*/
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							MyDialog.this.finish();//关闭该Acitivity
							
						}
					})
					/*设置一个取消按钮，实现点击事件*/
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//弹出一个Toast消息提示未退出，下一节讲会介绍
							Toast.makeText(MyDialog.this, "你取消了退出", Toast.LENGTH_SHORT).show();
						}
					})
					.show();
					
			break;
			/*单选对话框*/
		case R.id.button_sichoice:
			new AlertDialog.Builder(this)
				.setTitle("单选对话框")
				/*设置一个单选列表，该方法有多种重载方式，这里实现的方式的三个参数依次为
				 * 数组资源id，当前选中的条目，监听器
				 */
				.setSingleChoiceItems(R.array.mobile_arry, 2, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//弹出一个Toast消息，下一节讲会介绍，提示点击了哪一个条目
						Toast.makeText(MyDialog.this, "你点击了第" + which + "个", Toast.LENGTH_SHORT).show();
						
					}
				})
				.show();
			break;
			/*多选对话框*/
		case R.id.button_muchoice:
			new AlertDialog.Builder(this)
				.setTitle("多选对话框")
				/*设置一个对选列表，实现该方法其中的一个重载方式，三个参数依次为数组资源id，初始化选择，监听器*/
				.setMultiChoiceItems(R.array.phone_arry, new boolean[]{false,true,false,false,true,true}, 
						new DialogInterface.OnMultiChoiceClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which, boolean isChecked) {
								//弹出一个Toast消息，下一节讲会介绍，表明选择了哪一个条目
								Toast.makeText(MyDialog.this, "你点击了第" + which + "个", Toast.LENGTH_SHORT).show();
								
							}
						})
				.show();
			break;
			/*自定义多选对话框*/
		case R.id.button_login:
			
			//从自定义的login_dialog中装载LinearLayout对象
			final LinearLayout loginLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.login_dialog, null);
			
			new AlertDialog.Builder(this)
				.setTitle("自定义登录对话框")
				.setIcon(R.drawable.icon)//设置对话框图标
				.setView(loginLayout)//绑定布局
				.setPositiveButton("登录", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						/*得到输入框对象，注意要在导入的布局对象中找到该控件*/
						EditText ed_username = (EditText) loginLayout.findViewById(R.id.eduser_name);
						EditText ed_userpassword = (EditText) loginLayout.findViewById(R.id.eduser_password);
						//取出输入框中的值
						String user_info = "用户名: " + ed_username.getText().toString() + "\n" +
											"密码: " + ed_userpassword.getText().toString();
						//弹出一个Toast消息，显示登录信息
						Toast.makeText(MyDialog.this, "登录成功，你的信息为：" + "\n" + user_info	, Toast.LENGTH_LONG)
						.show();
						
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//提示未登录
						Toast.makeText(MyDialog.this, "未登录", Toast.LENGTH_LONG).show();
					}
				})
				.show();
			break;
			
		}
		
	}
}