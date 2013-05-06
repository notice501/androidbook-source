package com.pms.provider;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.pms.provider.Students.Student;

public class MyProvider extends Activity {
	
	private static final String TAG = "MyProvider";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //先执行插入操作
        insert();
        //查询
        query();
        //更新
        update();
        //重新查询
        query();
        //删除
        delete();
        //查询
        query();
    }
    
    public void insert(){
    	// 声明Uri
    	Uri uri = Student.CONTENT_URI;
    	// 取得ContentValues对象
    	ContentValues cv = new ContentValues();
    	// 添加学生信息
    	cv.put(Student.NAME, "notice");
    	cv.put(Student.GENDER, "男");
    	cv.put(Student.AGE,30);
    	// 将信息插入
    	getContentResolver().insert(uri, cv);
    	// 输出日志
    	Log.i(TAG, uri.toString());
    }
  
    // 查询
    private void query(){
    	// 要查询的列
    	   String[] PROJECTION = new String[] { 
    			   Student._ID, 		
    			   Student.NAME, 		
    			   Student.GENDER, 	
    			   Student.AGE 		
    	};
    	// 查询学生信息，返回游标对象
		Cursor c = managedQuery(Student.CONTENT_URI, PROJECTION, null,
				null, null);
		// 判断游标是否为空
		if (c.moveToFirst()) {
			// 遍历游标
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);
				String name = c.getString(1);  //取得姓名
				String gender = c.getString(2);//取得性别
				int age = c.getInt(3);		   //取得年龄
				// 输出日志
				Log.i(TAG, name+","+gender+","+age);
			}
		}
    }
    
    // 更新
    private void update(){
    	// 指定ID为1的记录
    	Uri uri = ContentUris.withAppendedId(Student.CONTENT_URI, 1);
    	ContentValues cv = new ContentValues();
    	// 添加学生信息
    	cv.put(Student.NAME, "pjd");
    	cv.put(Student.GENDER, "男");
    	cv.put(Student.AGE,25);
    	// 更新该记录
		getContentResolver().update(uri, cv, null, null);
    }
    
    // 删除
    private void delete(){
    	// 指定ID为1的记录
    	Uri uri = ContentUris.withAppendedId(Student.CONTENT_URI, 1);
    	// 删除该记录
    	getContentResolver().delete(uri, null, null);
    	Log.i(TAG, "信息已删除");
    }
    
}