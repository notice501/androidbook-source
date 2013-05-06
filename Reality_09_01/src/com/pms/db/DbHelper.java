package com.pms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "note_db";  //数据库名
	private final static int DATABASE_VERSION = 1;			//版本号			
	
	private final static String TABLE_NAME = "notepad";
	public final static String NOTE_ID = "_id";
	public final static String NOTE_TITLE = "title";
	public final static String NOTE_CONTENT = "content";
	
	public final static String LOGIN_TABLE_NAME = "login";
	public final static String LOGIN_USER = "admin";
	public final static String LOGIN_PWD = "password";
	
	/*构造函数*/
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/*创建数据库*/
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

	/*更新数据库*/
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table if exists "+TABLE_NAME;
		db.execSQL(sql);	
		
		sql = "drop table if exists "+LOGIN_TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
	/**插入密码
	 * @param password
	 * 要插入的密码
	 * */
	public long insertPwd(String password){
		SQLiteDatabase db = this.getWritableDatabase();    //取得可写的数据库对象
		ContentValues cv = new ContentValues();
		cv.put(LOGIN_USER, LOGIN_USER);
		cv.put(LOGIN_PWD, password);
		return db.insert(LOGIN_TABLE_NAME, null, cv);
	}
	
	/**更新密码
	 * @param password
	 * 新密码
	 * */
	public int updatePwd(String password){
		SQLiteDatabase db = this.getWritableDatabase();
		String where = LOGIN_USER+"=?";
		String[] whereValues = {LOGIN_USER};
		ContentValues cv = new ContentValues();
		cv.put(LOGIN_PWD, password);
		return db.update(LOGIN_TABLE_NAME, cv, where, whereValues);
	}
	
	/**取得密码
	 * @return 返回密码，没有则返回""
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
	/**查询记事本表中的内容	
	  */
	public Cursor selectNotes(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}
	
	/**插入记事
	 * */
	public long insertNote(String title, String content){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(NOTE_TITLE, title);
		cv.put(NOTE_CONTENT, content);
		return db.insert(TABLE_NAME, null, cv);
	}
	
	/**删除记事
	 * @param id
	 * _id字段
	 * */
	public void deleteNote(String id){
		SQLiteDatabase db = this.getWritableDatabase();
		String where = NOTE_ID+"=?";
		String[] whereValues = {id};
		db.delete(TABLE_NAME, where, whereValues);
	}
	
	/**更新记事
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

