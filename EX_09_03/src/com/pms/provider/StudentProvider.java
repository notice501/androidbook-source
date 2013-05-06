package com.pms.provider;

import java.util.HashMap;

import com.pms.provider.Students.Student;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


public class StudentProvider extends ContentProvider{
	// 声明数据库帮助类对象
	private DbHelper dbHelper;
    // 定义Uri工具类
    private static final UriMatcher sUriMatcher;
    // 匹配码
    private static final int STUDENT = 1;
    private static final int STUDENT_ID = 2;
    // 需要查询的列集合
    private static HashMap<String, String> stuProjiction;
    static {
    	// 创建UriMatcher对象，匹配Uri
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //添加需要匹配的Uri，匹配则返回相应的匹配码
        sUriMatcher.addURI(Students.AUTHORITY, "student", STUDENT);
        sUriMatcher.addURI(Students.AUTHORITY, "student/#", STUDENT_ID);
        // 实例化需要查询的列集合
        stuProjiction = new HashMap<String, String>();
        // 添加查询列map
        stuProjiction.put(Student._ID, Student._ID);
        stuProjiction.put(Student.NAME, Student.NAME);
        stuProjiction.put(Student.GENDER, Student.GENDER);
        stuProjiction.put(Student.AGE, Student.AGE);
    }

	// 创建时调用，初始化
	public boolean onCreate() {
		// 创建DbHelper对象
		dbHelper = new DbHelper(getContext());
		return true;
	}
	// 插入数据
	public Uri insert(Uri uri, ContentValues values) {
		// 获得可写的数据库
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// 插入数据，返回行ID
		long rowId = db.insert(DbHelper.STUDENTS_TABLE_NAME, Student.NAME, values);
		// 如果插入则成功返回uri
        if (rowId > 0) {
        	//将rowId添加到uri的id部分
            Uri stuUri = ContentUris.withAppendedId(Student.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(stuUri, null);
            return stuUri;
        }
		return null;
	}
	// 删除
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        // 根据匹配的Uri返回码来执行相应的删除操作
        case STUDENT: 
            count = db.delete(DbHelper.STUDENTS_TABLE_NAME, selection, selectionArgs);
            break;
        case STUDENT_ID: //只删除对应id的值
            String stuId = uri.getPathSegments().get(1);
            count = db.delete(DbHelper.STUDENTS_TABLE_NAME, Student._ID + "=" + stuId
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            break;

        default:
            throw new IllegalArgumentException("错误的 URI！ " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

	// 获得类型
	public String getType(Uri uri) {
		return null;
	}

	// 查询
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	        switch (sUriMatcher.match(uri)) {
	        // 查询所有
	        case STUDENT:
	            qb.setTables(DbHelper.STUDENTS_TABLE_NAME);
	            qb.setProjectionMap(stuProjiction);
	            break;
	        // 根据ID查询
	        case STUDENT_ID:
	            qb.setTables(DbHelper.STUDENTS_TABLE_NAME);
	            qb.setProjectionMap(stuProjiction);
	            qb.appendWhere(Student._ID + "=" + uri.getPathSegments().get(1));
	            break;
	        default:
	            throw new IllegalArgumentException("Uri错误！ " + uri);
	        }

	        // 获得可写的数据库对象
	        SQLiteDatabase db = dbHelper.getReadableDatabase();
	        // 返回游标集合
	        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
	        c.setNotificationUri(getContext().getContentResolver(), uri);
	        return c;
	}

	// 更新
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
    	// 获得一个可写的数据库对戏那个
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        //根据匹配的Uri返回码来执行相应的删除操作
        switch (sUriMatcher.match(uri)) {
        case STUDENT:
            count = db.update(DbHelper.STUDENTS_TABLE_NAME, values, selection, selectionArgs);
            break;
        case STUDENT_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(DbHelper.STUDENTS_TABLE_NAME, values, Student._ID + "=" + noteId
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("错误的 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

}

