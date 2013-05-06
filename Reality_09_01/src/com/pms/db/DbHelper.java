package com.pms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "note_db";  //���ݿ���
	private final static int DATABASE_VERSION = 1;			//�汾��			
	
	private final static String TABLE_NAME = "notepad";
	public final static String NOTE_ID = "_id";
	public final static String NOTE_TITLE = "title";
	public final static String NOTE_CONTENT = "content";
	
	public final static String LOGIN_TABLE_NAME = "login";
	public final static String LOGIN_USER = "admin";
	public final static String LOGIN_PWD = "password";
	
	/*���캯��*/
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/*�������ݿ�*/
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table "+TABLE_NAME+" ("
		+NOTE_ID+" integer primary key autoincrement, "
		+NOTE_TITLE+" text, "
		+NOTE_CONTENT+" text )";
		db.execSQL(sql);
		
		sql = "create table "+LOGIN_TABLE_NAME+" ("
		+LOGIN_USER+" text, "
		+LOGIN_PWD+" text )";
		db.execSQL(sql);
	}

	/*�������ݿ�*/
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table if exists "+TABLE_NAME;
		db.execSQL(sql);	
		
		sql = "drop table if exists "+LOGIN_TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
	/**��������
	 * @param password
	 * Ҫ���������
	 * */
	public long insertPwd(String password){
		SQLiteDatabase db = this.getWritableDatabase();    //ȡ�ÿ�д�����ݿ����
		ContentValues cv = new ContentValues();
		cv.put(LOGIN_USER, LOGIN_USER);
		cv.put(LOGIN_PWD, password);
		return db.insert(LOGIN_TABLE_NAME, null, cv);
	}
	
	/**��������
	 * @param password
	 * ������
	 * */
	public int updatePwd(String password){
		SQLiteDatabase db = this.getWritableDatabase();
		String where = LOGIN_USER+"=?";
		String[] whereValues = {LOGIN_USER};
		ContentValues cv = new ContentValues();
		cv.put(LOGIN_PWD, password);
		return db.update(LOGIN_TABLE_NAME, cv, where, whereValues);
	}
	
	/**ȡ������
	 * @return �������룬û���򷵻�""
	 * */
	public String getPwd(){
		SQLiteDatabase db = this.getReadableDatabase();
		String where = LOGIN_USER+"=?";
		String[] whereValues = {LOGIN_USER}; 
		Cursor cursor = db.query(LOGIN_TABLE_NAME, null, where, whereValues, null, null, null);
		if(cursor.moveToFirst()){
			return cursor.getString(cursor.getColumnIndex(LOGIN_PWD));
		}else{
			return "";
		}
	}
	/**��ѯ���±����е�����	
	  */
	public Cursor selectNotes(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}
	
	/**�������
	 * */
	public long insertNote(String title, String content){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(NOTE_TITLE, title);
		cv.put(NOTE_CONTENT, content);
		return db.insert(TABLE_NAME, null, cv);
	}
	
	/**ɾ������
	 * @param id
	 * _id�ֶ�
	 * */
	public void deleteNote(String id){
		SQLiteDatabase db = this.getWritableDatabase();
		String where = NOTE_ID+"=?";
		String[] whereValues = {id};
		db.delete(TABLE_NAME, where, whereValues);
	}
	
	/**���¼���
	 * */
	public int updateNote(String id,String title, String content){
		SQLiteDatabase db = this.getWritableDatabase();
		String where = NOTE_ID+"=?";
		String[] whereValues = {id};
		ContentValues cv = new ContentValues();
		cv.put(NOTE_TITLE, title);
		cv.put(NOTE_CONTENT, content);
		return db.update(TABLE_NAME, cv, where, whereValues);
	}
	
}

