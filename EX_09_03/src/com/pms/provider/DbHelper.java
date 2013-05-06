package com.pms.provider;

import com.pms.provider.Students.Student;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	
	//���ݿ�����
	private static final String DATABASE_NAME = "student_db";
	//���ݿ�汾
	private static final int    DATABASE_VERSION = 1;
	//����
	public static final String STUDENTS_TABLE_NAME = "student";

	//���캯��
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//����ѧ����Ϣ�������ĸ��ֶΣ��ֱ���id���������Ա�����
        db.execSQL("CREATE TABLE " + STUDENTS_TABLE_NAME + " ("
                + Student._ID + " INTEGER PRIMARY KEY,"
                + Student.NAME + " TEXT,"
                + Student.GENDER + " TEXT,"
                + Student.AGE + " INTEGER"
                + ");");
	}

	// ���±�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXITS student";
		db.execSQL(sql);
	}

}
