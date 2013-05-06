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
	// �������ݿ���������
	private DbHelper dbHelper;
    // ����Uri������
    private static final UriMatcher sUriMatcher;
    // ƥ����
    private static final int STUDENT = 1;
    private static final int STUDENT_ID = 2;
    // ��Ҫ��ѯ���м���
    private static HashMap<String, String> stuProjiction;
    static {
    	// ����UriMatcher����ƥ��Uri
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //�����Ҫƥ���Uri��ƥ���򷵻���Ӧ��ƥ����
        sUriMatcher.addURI(Students.AUTHORITY, "student", STUDENT);
        sUriMatcher.addURI(Students.AUTHORITY, "student/#", STUDENT_ID);
        // ʵ������Ҫ��ѯ���м���
        stuProjiction = new HashMap<String, String>();
        // ��Ӳ�ѯ��map
        stuProjiction.put(Student._ID, Student._ID);
        stuProjiction.put(Student.NAME, Student.NAME);
        stuProjiction.put(Student.GENDER, Student.GENDER);
        stuProjiction.put(Student.AGE, Student.AGE);
    }

	// ����ʱ���ã���ʼ��
	public boolean onCreate() {
		// ����DbHelper����
		dbHelper = new DbHelper(getContext());
		return true;
	}
	// ��������
	public Uri insert(Uri uri, ContentValues values) {
		// ��ÿ�д�����ݿ�
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// �������ݣ�������ID
		long rowId = db.insert(DbHelper.STUDENTS_TABLE_NAME, Student.NAME, values);
		// ���������ɹ�����uri
        if (rowId > 0) {
        	//��rowId��ӵ�uri��id����
            Uri stuUri = ContentUris.withAppendedId(Student.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(stuUri, null);
            return stuUri;
        }
		return null;
	}
	// ɾ��
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        // ����ƥ���Uri��������ִ����Ӧ��ɾ������
        case STUDENT: 
            count = db.delete(DbHelper.STUDENTS_TABLE_NAME, selection, selectionArgs);
            break;
        case STUDENT_ID: //ֻɾ����Ӧid��ֵ
            String stuId = uri.getPathSegments().get(1);
            count = db.delete(DbHelper.STUDENTS_TABLE_NAME, Student._ID + "=" + stuId
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            break;

        default:
            throw new IllegalArgumentException("����� URI�� " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

	// �������
	public String getType(Uri uri) {
		return null;
	}

	// ��ѯ
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	        switch (sUriMatcher.match(uri)) {
	        // ��ѯ����
	        case STUDENT:
	            qb.setTables(DbHelper.STUDENTS_TABLE_NAME);
	            qb.setProjectionMap(stuProjiction);
	            break;
	        // ����ID��ѯ
	        case STUDENT_ID:
	            qb.setTables(DbHelper.STUDENTS_TABLE_NAME);
	            qb.setProjectionMap(stuProjiction);
	            qb.appendWhere(Student._ID + "=" + uri.getPathSegments().get(1));
	            break;
	        default:
	            throw new IllegalArgumentException("Uri���� " + uri);
	        }

	        // ��ÿ�д�����ݿ����
	        SQLiteDatabase db = dbHelper.getReadableDatabase();
	        // �����α꼯��
	        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
	        c.setNotificationUri(getContext().getContentResolver(), uri);
	        return c;
	}

	// ����
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
    	// ���һ����д�����ݿ��Ϸ�Ǹ�
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        //����ƥ���Uri��������ִ����Ӧ��ɾ������
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
            throw new IllegalArgumentException("����� URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

}

