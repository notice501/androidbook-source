package com.pms.myshared;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MySharedPrefereces extends Activity {
	
	private EditText et_name, et_pwd;
	private TextView count;
	private SharedPreferences sp;//声明SharedPreferences对象
	private int ct;//用来计数
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*得到各个控件对象*/
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        count = (TextView) findViewById(R.id.tv_count);
        

        //取得SharedPreferences对象
        sp = getSharedPreferences("my_data", Context.MODE_PRIVATE);
        //从my_data文件中取得相应的键对应的值，为空则初始化为第二个参数的值
        String name = sp.getString("name", "");
        String pwd = sp.getString("pwd", "");
        ct = sp.getInt("count", 0);
        
        //设置各个控件显示的内容
        count.setText("这是第" + ct +"次打开该应用");
        et_name.setText(name);
        et_pwd.setText(pwd);
        
    }
    /*在onPause中保存数据*/
	@Override
	protected void onPause() {
		super.onPause();
		/*取得输入框的内容*/
        String etname = et_name.getText().toString();
        String etpwd = et_pwd.getText().toString();
        //取得SharedPreferences.Editor对象
		SharedPreferences.Editor editor = sp.edit();
		//存储内容
		editor.putString("name", etname);
		editor.putString("pwd", etpwd);
		editor.putInt("count", ++ct);
		//提交
		editor.commit();
		
	}
}