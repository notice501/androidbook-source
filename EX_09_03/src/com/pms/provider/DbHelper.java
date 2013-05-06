package com.pms.provider;

import com.pms.provider.Students.Student;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	
	//数据库名称
	private static final String DATABASE_NAME = "student_db";
	//数据库版本
	private static final int    DATABASE_VERSION = 1;
	//表名
	public static final String STUDENTS_TABLE_NAME = "student";

	//构造函数
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建学生信息表，包含四个字段，分别是id，姓名，性别，年龄
        db.execSQL("CREATE TABLE " + STUDENTS_TABLE_NAME + " ("
                + Student._ID + " INTEGER PRIMARY KEY,"
                + Student.NAME + " TEXT,"
                + Student.GENDER + " TEXT,"
                + Student.AGE + " INTEGER"
                + ");");
	}

	// 更新表单
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXITS student";
		db.execSQL(sql);
	}

}
